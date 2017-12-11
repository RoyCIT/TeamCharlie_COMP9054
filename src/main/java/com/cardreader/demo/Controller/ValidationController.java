package com.cardreader.demo.Controller;

import com.cardreader.demo.Data.EventRepository;
import com.cardreader.demo.Model.Event;
import com.cardreader.demo.Mqtt.MqttUtility;
import com.cardreader.demo.Store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (value = "/api")
public class ValidationController implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * Internal store
     * Spring knows which polymorphic type to use if there's a single implementation of the
     * interface and it's annotated with @Component and Spring's component scan enabled.
     */
    @Autowired
    private Store store;

    @Autowired
    private EventRepository eventData;

    /**
     * This is the instance of eventCacher class that handles
     * the caching of the most recent swipe for every person
     */
    private EventCacher eventCacher;

    /**
     * Default constructor
     */
    public ValidationController() {
        this.eventCacher = new EventCacher();
    }

    @RequestMapping(value = "/panels/request", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Store getAccessByEvents(@RequestParam(value="panelid") String panelid,
                                   @RequestParam(value="cardid") String cardid,
                                   @RequestParam(value="allowed") boolean accessAllowed) {

        getEvents(panelid, cardid, accessAllowed);

        return store;
    }

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        // Populate cache from database
        List<Event> allEvents = eventData.findAll();
        for (Event e : allEvents) {
            cacheEvent(e);
        }
    }

    /**
     * Creates an Event instance for the current swipe
     * Creates an Event instance for the previous swipe of this card
     * Populates inMemoryStore instance from Events
     * Sends a post subscribe message if required
     *
     * @param panelId       the panelId of the current event
     * @param cardId        the cardId of the current event
     * @param accessAllowed is local access allowed for the current event
     */
    private void getEvents(String panelId, String cardId, boolean accessAllowed) {

        // TODO: create inmemorystore for locations and ability for client to update / refresh data from uuid
        // TODO: Swagger

        String reason;
        boolean isValidEvent = false;
        boolean doAlert = false;
        Event currentEvent = populateCurrentEvent(panelId, cardId, accessAllowed);
        Event previousEvent = getPreviousEventFromKey(currentEvent.getKey());

        if (currentEvent.getAccessAllowed()) {

            // Has UUID locator been successful
            if (currentEvent.getlocation() != null) {
                // Determine if locations are feasible/valid
                isValidEvent = validateLocations(previousEvent, currentEvent);

                if (isValidEvent) {
                    reason = "Valid event.";
                }
                else {
                    reason = "Impossible time-distance event.";
                    doAlert = true;
                }
                // Only cache events where the location has been resolved
                cacheEvent(currentEvent);

                // Update database
                eventData.save(currentEvent);
            }
            else {
                reason = "Unknown panel id, unable to resolve location";
            }
        }
        else {
            // Local access has been declined
            reason = "Local access denied for this event.";
        }

        // populate the store
        store.setCurrentEvent(currentEvent);
        store.setPreviousEvent(previousEvent);
        store.setValidEvent(isValidEvent);
        store.setReason(reason);

        // Publish message
        if (doAlert) {
            MqttUtility.getInstance().publishAlert(store.getCurrentEvent(), store.getPreviousEvent());
        }
    }

    /**
     * Calls validationController to determine if cloned card is detected
     *
     * @param previousEvent the previous event retrieved from cache or db
     * @param currentEvent  the populated current event object
     * @return              boolean reflecting if event locations are feasible
     */
    private boolean validateLocations(Event previousEvent, Event currentEvent) {

        if (previousEvent != null && currentEvent != null) {
            return GoogleMapsValidationController.performValidation(previousEvent, currentEvent);
        }

        return true;
    }

    /**
     * Caches the current event, will overwrite previous swipe for this card
     *
     * @param theEvent the current event
     */
    public void cacheEvent(Event theEvent) {
        if (theEvent != null) eventCacher.addEventToCache(theEvent);
    }

//    private Event getCurrentEvent(String panelId, String cardId, boolean accessAllowed) {
//        return populateCurrentEvent(panelId, cardId, accessAllowed);
//    }

    /**
     * Retrieve Event from cache using the key
     *
     * @param key cardId + panelId
     * @return    previous event
     */
    private Event getPreviousEventFromKey(String key) {
        return eventCacher.getEventFromCache(key);
    }

    /**
     * Create a new Event instance from the current transaction
     * Resolve the location for this event
     *
     * @param panelId from current transaction/card swipe
     * @param cardId from current transaction/card swipe
     * @param accessAllowed local access granted/denied for this transaction
     * @return the populated currentEvent
     */
    private Event populateCurrentEvent(String panelId, String cardId, boolean accessAllowed) {
        Event currentEvent = new Event(panelId, cardId, accessAllowed);
        currentEvent.resolveLocation();

        return currentEvent;
    }

}

package com.cardreader.demo.Controller;

import com.cardreader.demo.Model.Event;
import com.cardreader.demo.Mqtt.MqttUtility;
import com.cardreader.demo.Store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "/api")
public class ValidationController {

    // Internal store
    // Spring knows which polymorphic type to use if there's a single implementation of the
    // interface and it's annotated with @Component and Spring's component scan enabled.
    @Autowired
    private Store store;

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

    private void getEvents(String panelId, String cardId, boolean accessAllowed) {

        // TODO: create inmemorystore for locations and ability for client to update / refresh data from uuid
        // TODO: Comments
        // TODO: Swagger

        String reason;
        boolean isValidEvent = false;
        boolean doAlert = false;
        Event currentEvent = getCurrentEvent(panelId, cardId, accessAllowed);
        Event previousEvent = getPreviousEventFromKey(currentEvent.getKey());

        if (currentEvent.getAccessAllowed()) {

            // Has UUID locator been successful
            if (currentEvent.getlocation() != null) {
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
            }
            else {
                reason = "Unknown panel id, unable to resolve location";
            }
        }
        else {
            reason = "Local access denied for this event.";
        }

        // populate the store
        store.setCurrentEvent(currentEvent);
        store.setPreviousEvent(previousEvent);
        store.setValidEvent(isValidEvent);
        store.setReason(reason);

        if (doAlert) {
            MqttUtility.getInstance().publishAlert(store.getCurrentEvent(), store.getPreviousEvent());
        }
    }

    private boolean validateLocations(Event previousEvent, Event currentEvent) {

        if (previousEvent != null && currentEvent != null) {
            return GoogleMapsValidationController.performValidation(previousEvent, currentEvent);
        }

        return true;
    }

    private void cacheEvent(Event theEvent) {
        if (theEvent != null) eventCacher.addEventToCache(theEvent);
    }

    private Event getCurrentEvent(String panelId, String cardId, boolean accessAllowed) {
        return populateCurrentEvent(panelId, cardId, accessAllowed);
    }

    private Event getPreviousEventFromKey(String key) {
        return (Event)eventCacher.getEventFromCache(key);
    }

    private Event populateCurrentEvent(String panelId, String cardId, boolean accessAllowed) {
        Event currentEvent = new Event(panelId, cardId, accessAllowed);
        currentEvent.resolveLocation();

        return currentEvent;
    }
}

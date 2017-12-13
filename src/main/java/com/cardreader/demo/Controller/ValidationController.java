package com.cardreader.demo.Controller;

import com.cardreader.demo.Data.EventRepository;
import com.cardreader.demo.Model.Event;
import com.cardreader.demo.Model.ILocation;
import com.cardreader.demo.Model.Location;
import com.cardreader.demo.Mqtt.MqttUtility;
import com.cardreader.demo.Store.IJsonStore;
import com.cardreader.demo.Store.JsonStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping (value = "/api")
public class ValidationController implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private EventRepository eventData;

    /**
     * This is the instance of eventCacher class that handles
     * the caching of the most recent swipe for every person
     */
    private EventCacher eventCacher;

    // store of panel locations
    private Map<UUID, ILocation> panelLocations;

    /**
     * Default constructor
     */
    public ValidationController() {
        this.eventCacher = new EventCacher();
        this.panelLocations = new HashMap<UUID, ILocation>();
    }

    @RequestMapping(value = "/panels/request", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public IJsonStore getAccessByEvents(@RequestParam(value="panelid") String panelid,
                                        @RequestParam(value="cardid") String cardid,
                                        @RequestParam(value="allowed") boolean accessAllowed) {

        return getEvents(panelid, cardid, accessAllowed);
    }

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        // Populate event cache from database
        updateEventCacheFromDB();

        // Populate location cache from rest interface
        updateLocationCacheFromRest();
    }

    /**
     * Populate the in-memory event cache from database
     */
    private void updateEventCacheFromDB() {

        List<Event> allEvents = eventData.findAll();
        for (Event e : allEvents) {
            cacheEvent(e);
        }
    }

    /**
     * update the in-memory location cache from rest interface
     */
    private void updateLocationCacheFromRest() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://uuidlocator.cfapps.io/api/panels/";
        Location location;
        UUID uuid;

        try {
            // Get all ids
            ResponseEntity<String[]> allIds = restTemplate.getForEntity(url, String[].class);

            for (String id : allIds.getBody()) {
                uuid = UUID.fromString(id);
                location = restTemplate.getForObject(url + id, Location.class);
                if (location != null) {
                    panelLocations.put(uuid, location);
                }
            }
        }
        catch (HttpMessageNotReadableException notReadable) {
            // Do nothing??
        }
        catch (HttpServerErrorException serverError) {
            // Do nothing??
        }
    }
    /**
     * INstantiate and populate jsonStore object which is bound to JSON
     * Sends a post subscribe message if required
     *
     * @param panelId       the panelId of the current event
     * @param cardId        the cardId of the current event
     * @param accessAllowed is local access allowed for the current event
     */
    private JsonStore getEvents(String panelId, String cardId, boolean accessAllowed) {

        boolean isValidEvent = false;
        boolean doAlert = false;
        JsonStore jsonStore = new JsonStore();

        // Instantiate currentEvent and populate location
        Event currentEvent = populateCurrentEvent(panelId, cardId, accessAllowed);
        // Get previous event from cache
        Event previousEvent = getPreviousEventFromKey(currentEvent.getKey());

        // Jackson is used to bind jsonStore to JSON. Populate jsonStore
        jsonStore.setCurrentEvent(currentEvent);
        jsonStore.setPreviousEvent(previousEvent);

        // Local access has been created
        if (currentEvent.getAccessAllowed()) {

            if (currentEvent.getlocation() != null) {
                // Determine if locations are feasible/valid
                isValidEvent = validateLocations(previousEvent, currentEvent);

                if (isValidEvent) {
                    jsonStore.setReason("Valid event.");
                }
                else {
                    jsonStore.setReason("Impossible time-distance event.");
                    doAlert = true;
                }
                // Only cache events where the location has been resolved
                cacheEvent(currentEvent);

                // Update database
                eventData.save(currentEvent);
            }
            else {
                jsonStore.setReason("Unknown panel id, unable to resolve location");
            }
        }
        else {
            // Local access has been declined
            jsonStore.setReason("Local access denied for this event.");
        }

        jsonStore.setValidEvent(isValidEvent);

        // Publish message
        if (doAlert) {
            sendMqtt(currentEvent, previousEvent);
        }
        return jsonStore;
    }

    /**
     * Publish-Subscribe message
     * @param currentEvent the current event
     * @param previousEvent the previous event
     */
    private void sendMqtt(Event currentEvent, Event previousEvent) {
        MqttUtility.getInstance().publishAlert(currentEvent, previousEvent);
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
    private void cacheEvent(Event theEvent) {
        if (theEvent != null) eventCacher.addEventToCache(theEvent);
    }

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

        // lookup panel location cache by uuid
        UUID uuid = UUID.fromString(currentEvent.getPanelId());
        Location l = (Location)panelLocations.get(uuid);

        if (l != null) {
            // use the cache
            currentEvent.setLocation(l);
        }
        else {
            // call rest interface
            currentEvent.resolveLocation();
            // update the cache
            panelLocations.put(uuid, currentEvent.getlocation());
        }
        return currentEvent;
    }
}

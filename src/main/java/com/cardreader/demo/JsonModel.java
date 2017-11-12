package com.cardreader.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "reason", "currentEvent", "previousEvent", "accessAlllowed" })
public class JsonModel {

    private static JsonModel instance = null;
    private static String panelId;
    private static String cardId;
    private Event currentEvent;
    private Event previousEvent;
    private EventCacher eventCacher;
    private Boolean validEvent;
    private String reason;

    protected JsonModel() {
        this.validEvent = false;
        eventCacher = new EventCacher();
        reason = "Impossible time-distance event.";
    }

    public static JsonModel getInstance() {
        if(instance == null) {
            instance = new JsonModel();
        }
        return instance;
    }

    public void populate(String panelId, String cardId) {
        boolean accessAllowed;

        currentEvent = getCurrentEvent(panelId, cardId);
        previousEvent = getPreviousEventFromKey(currentEvent.getKey());

        // Once a card is identified as a clone then maintain that state
        // This prevents a cloned card from being reused.
        if (previousEvent != null && previousEvent.getValidEvent()) {
            currentEvent.setValidEvent(true);
            accessAllowed = false;
            // reason: this card has previously been detected as a clone
        }
        else if (currentEvent.getAccessAllowed()) {
            accessAllowed = validateLocations(currentEvent, previousEvent);
            // Set flag to identify as a cloned card
            currentEvent.setValidEvent(accessAllowed);
            // if !accessAllowed reason: this card is currently detected as a clone
        }
        else {
            accessAllowed = false;
        }

        currentEvent.setAccessAllowed(accessAllowed);
        cacheEvent(currentEvent);
    }

    private boolean validateLocations(Event previousEvent, Event currentEvent) {
        return true;
    }

    private void cacheEvent(Event theEvent) {
        if (theEvent != null) eventCacher.addEventToCache(theEvent);
    }

    private Event getCurrentEvent(String panelId, String cardId) {
        return populateCurrentEvent(panelId, cardId);
    }

    private Event getPreviousEventFromKey(String key) {
        return (Event)eventCacher.getEventFromCache(key);
    }

    private Event populateCurrentEvent(String panelId, String cardId) {
        currentEvent = new Event(panelId, cardId);
        currentEvent.resolveLocation();
        return currentEvent;
    }

    public String getReason() {
        return this.reason;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public Event getPreviousEvent() {
        return previousEvent;
    }

    @JsonProperty("validEvent")
    public Boolean getValidEvent() {
        return this.currentEvent.getValidEvent();
    }

}

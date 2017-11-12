package com.cardreader.demo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "reason", "currentEvent", "previousEvent", "validEvent" })
public class JsonModel {

    private static JsonModel instance = null;
    private Event currentEvent;
    private Event previousEvent;
    private EventCacher eventCacher;
    private Boolean validEvent;
    private String reason;

    protected JsonModel() {
        this.validEvent = false;
        this.eventCacher = new EventCacher();
        this.reason = "Valid event.";
    }

    public static JsonModel getInstance() {
        if(instance == null) {
            instance = new JsonModel();
        }
        return instance;
    }

    public void populate(String panelId, String cardId, String accessAllowed) {

        currentEvent = getCurrentEvent(panelId, cardId, accessAllowed);
        previousEvent = getPreviousEventFromKey(currentEvent.getKey());

        if (currentEvent.getAccessAllowed().equals("true")) {
            validEvent = validateLocations(currentEvent, previousEvent);
            if (!validEvent) {
                reason = "Impossible time-distance event.";
            }
        }
        else {
            reason = "Local access denied for this event.";
        }

        cacheEvent(currentEvent);
    }

    private boolean validateLocations(Event previousEvent, Event currentEvent) {
        return true;
    }

    private void cacheEvent(Event theEvent) {
        if (theEvent != null) eventCacher.addEventToCache(theEvent);
    }

    private Event getCurrentEvent(String panelId, String cardId, String accessAllowed) {
        return populateCurrentEvent(panelId, cardId, accessAllowed);
    }

    private Event getPreviousEventFromKey(String key) {
        return (Event)eventCacher.getEventFromCache(key);
    }

    private Event populateCurrentEvent(String panelId, String cardId, String accessAllowed) {
        currentEvent = new Event(panelId, cardId, accessAllowed);
        currentEvent.resolveLocation();
        return currentEvent;
    }

    public String getReason() {
        return this.reason;
    }

    public Event getCurrentEvent() {
        return this.currentEvent;
    }

    public Event getPreviousEvent() {
        return this.previousEvent;
    }

    public Boolean getValidEvent() {
        return this.validEvent;
    }

}

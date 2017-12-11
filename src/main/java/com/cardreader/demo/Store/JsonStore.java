package com.cardreader.demo.Store;

import com.cardreader.demo.Model.Event;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.stereotype.Component;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "reason", "currentEvent", "previousEvent", "validEvent" })
public class JsonStore implements Store{

    private Event currentEvent;
    private Event previousEvent;
    private Boolean validEvent;
    private String reason;

    public JsonStore() {
    }

    @Override
    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    @Override
    public void setPreviousEvent(Event previousEvent) {
        this.previousEvent = previousEvent;
    }

    @Override
    public void setValidEvent(Boolean validEvent) {
        this.validEvent = validEvent;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public Event getCurrentEvent() {
        return this.currentEvent;
    }

    @Override
    public Event getPreviousEvent() {
        return this.previousEvent;
    }

    @Override
    public Boolean getValidEvent() {
        return this.validEvent;
    }

}

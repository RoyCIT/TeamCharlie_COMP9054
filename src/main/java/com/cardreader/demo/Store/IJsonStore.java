package com.cardreader.demo.Store;

import com.cardreader.demo.Model.Event;

public interface IJsonStore {

    void setCurrentEvent(Event currentEvent);

    void setPreviousEvent(Event previousEvent);

    void setValidEvent(Boolean validEvent);

    void setReason(String reason);

    String getReason();

    Event getCurrentEvent();

    Event getPreviousEvent();

    Boolean getValidEvent();
}

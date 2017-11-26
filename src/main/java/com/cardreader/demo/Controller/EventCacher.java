package com.cardreader.demo.Controller;

import com.cardreader.demo.Model.Event;

import java.util.HashMap;
import java.util.Map;

public class EventCacher {
    private Map cache;

    public EventCacher() {
        this.cache = new HashMap<String, Object>();
    }

    public Object getEventFromCache(String key) {
        return this.cache.get(key);
    }

    public void addEventToCache(Event event) {
        String key = event.getCardId();
        // If the key is in cache then replace with the latest details
        if (this.cache.containsKey(key)) {
            this.cache.remove(key);
        }
        this.cache.put(key, event);
    }
}
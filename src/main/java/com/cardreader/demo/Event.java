package com.cardreader.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.client.RestTemplate;

public class Event {

    private final String panelId;
    private final String cardId;
    private final String url = "http://uuidlocator.cfapps.io/api/panels/";
    private Location location;
    @JsonIgnore
    private Boolean validEvent;
    private Boolean accessAllowed;
    @JsonIgnore
    private String key;

    public Event(String panelId, String cardId) {
        this.panelId = panelId;
        this.cardId = cardId;
        this.validEvent = false;
        this.accessAllowed = false;
        this.key = getPanelId()+getCardId();
    }

    public void resolveLocation() {
        RestTemplate restTemplate = new RestTemplate();
        location = restTemplate.getForObject(url + panelId, Location.class);
    }

    public String getPanelId() {
        return panelId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getKey() {
        return this.key;
    }

    public Location getlocation() {
        return location;
    }

    public Boolean getValidEvent() {
        return this.validEvent;
    }

    public void setValidEvent(Boolean isValid) {
        this.validEvent = isValid;
    }

    public Boolean getAccessAllowed() {
        return this.accessAllowed;
    }

    public void setAccessAllowed (Boolean accessAllowed) {
        this.accessAllowed = accessAllowed;
    }
}

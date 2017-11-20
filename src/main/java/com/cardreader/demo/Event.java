package com.cardreader.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;

@JsonPropertyOrder({ "panelId", "cardId", "timestamp", "location", "accessAllowed" })
public class Event {

    private final String panelId;
    private final String cardId;
    private Location location;
    private final String accessAllowed;
    @JsonIgnore
    private final String key;
    private Timestamp timestamp;

    public Event(String panelId, String cardId, String accessAllowed) {
        this.panelId = panelId;
        this.cardId = cardId;
        this.accessAllowed = accessAllowed;
        this.key = cardId;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    //TODO: Remove me
    public Event(Location location) {
        this.location = location;
        this.panelId = "1234";
        this.cardId = "1232132";
        this.accessAllowed = "true";
        this.key = this.cardId;
        this.timestamp = new Timestamp(System.currentTimeMillis() - 10000000);
    }

    public void resolveLocation() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://uuidlocator.cfapps.io/api/panels/";
        location = restTemplate.getForObject(url + panelId, Location.class);
    }

    public String getPanelId() {
        return this.panelId;
    }

    public String getCardId() {
        return this.cardId;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public String getKey() {
        return this.key;
    }

    public Location getlocation() {
        return this.location;
    }

    public String getAccessAllowed() {
        return this.accessAllowed;
    }
}

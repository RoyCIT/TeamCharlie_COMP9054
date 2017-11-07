package com.cardreader.demo;

import org.springframework.web.client.RestTemplate;

public class CurrentEvent {

    private final String panelId;
    private final String cardId;
    private final String url = "http://uuidlocator.cfapps.io/api/panels/";
    private Location location;


    public CurrentEvent(String panelId, String cardId) {
        this.panelId = panelId;
        this.cardId = cardId;
    }

    public void resolveLocation() {
        RestTemplate restTemplate = new RestTemplate();
        location = restTemplate.getForObject(url + panelId, Location.class);;
    }

    public String getPanelId() {
        return panelId;
    }

    public String getCardId() {
        return cardId;
    }

    public Location getlocation() {
        return location; }
}

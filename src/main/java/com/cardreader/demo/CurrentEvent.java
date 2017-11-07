package com.cardreader.demo;

public class CurrentEvent {

    private final String panelId;
    private final String cardId;
    private Location currentLocation;


    public CurrentEvent(String panelId, String cardId) {
        this.panelId = panelId;
        this.cardId = cardId;
        this.currentLocation = new Location(panelId);
    }

    public void resolveLocation() {
        currentLocation.resolve();
    }

    public String getPanelId() {
        return panelId;
    }

    public String getCardId() {
        return cardId;
    }

    public Location getCurrentLocation() { return currentLocation; }
}

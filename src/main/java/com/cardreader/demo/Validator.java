package com.cardreader.demo;

public class Validator {

    private static String panelId;
    private static String cardId;
    private final long id;
    private CurrentEvent currentEvent;


    public Validator(long id, String panelId, String cardId) {
        this.id = id;
        this.panelId = panelId;
        this.cardId = cardId;
        this.currentEvent = null;
    }

    public void populateCurrentEvent() {
        currentEvent = new CurrentEvent(panelId, cardId);
        currentEvent.resolveLocation();
    }
    
    public long getId() {
        return id;
    }

    //public String getCardId() {
    //    return cardId;
    //}

    //public String getPanelId() {
    //    return panelId;
    //}

    public String getReason() {
        return "Impossible time-distance event.";
    }

    public CurrentEvent getCurrentEvent() {
        return currentEvent;
    }

//    public String getPreviousEvent() {
//        return "Previous event class";
//    }

}

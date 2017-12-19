package com.cardreader.demo.Model;

import java.sql.Timestamp;

/*
 * Utility class to create sample events for tests
 *
 * Event 1 and event 2 are different locations and cards
 */
public class EventUtil {

    // Declare these constants as public as we may want to use them in comparison tests

    public static final String panelId1 = "e95cffe9-f7cf-4a58-92ac-733a7350092f";
    public static final String cardId1 = "246609c6-ce9c-41a2-b64d-ba8066a376af";
    public static final double altitude1 = 110.0;
    public static final String relativeLocation1 = "Main Building, First Floor East Exit, Cork, Ireland";
    public static final double longitude1 = -8.532648;
    public static final double latitude1 = 51.883165;
    public static final boolean accessAllowed1 = true;
    public static final Timestamp timestamp1 = java.sql.Timestamp.valueOf("2017-12-14 19:01:59.446");

    public Coordinates coordinates1;
    public Location location1;
    public Event event1;

    public static final String panelId2 = "efa66c50-1327-4aec-9661-ef7531235420";
    public static final String cardId2 = "846609c6-ce9c-41a2-b64d-ba8066a376af";
    public static final double altitude2 = 50.0;
    public static final String relativeLocation2 = "Widener Library, Harvard University, Main Entrance, MA, USA";
    public static final double longitude2 = -71.116374;
    public static final double latitude2 = 42.373921;
    public static final boolean accessAllowed2 = true;
    public static final Timestamp timestamp2 = java.sql.Timestamp.valueOf("2017-12-18 12:08:05.708");

    public Coordinates coordinates2;
    public Location location2;
    public Event event2;

    public Event getEvent1() {
        coordinates1 = new Coordinates();
        coordinates1.setLongitude(longitude1);
        coordinates1.setLatitude(latitude1);

        location1 = new Location();
        location1.setAltitude(altitude1);
        location1.setRelativeLocation(relativeLocation1);
        location1.setCoordinates(coordinates1);

        event1 = new Event(panelId1, cardId1, accessAllowed1);
        event1.setLocation(location1);
        event1.setTimestamp(timestamp1);

        return event1;
    }

    public Event getEvent2() {
        coordinates2 = new Coordinates();
        coordinates2.setLongitude(longitude2);
        coordinates2.setLatitude(latitude2);

        location2 = new Location();
        location2.setAltitude(altitude2);
        location2.setRelativeLocation(relativeLocation2);
        location2.setCoordinates(coordinates2);

        event2 = new Event(panelId2, cardId2, accessAllowed2);
        event2.setLocation(location2);
        event2.setTimestamp(timestamp2);

        return event2;
    }

    public boolean sameContents(Event ev1, Event ev2) {

        if(!ev1.getKey().equals(ev2.getKey())) return false;
        if(!ev1.getPanelId().equals(ev2.getPanelId())) return false;
        if(!ev1.getCardId().equals(ev2.getCardId())) return false;
        if(ev1.getAccessAllowed() != ev2.getAccessAllowed()) return false;
        if(!ev1.getTimestamp().equals(ev2.getTimestamp())) return false;
        if(!ev1.getlocation().getAltitude().equals(ev2.getlocation().getAltitude())) return false;
        if(!ev1.getlocation().getRelativeLocation().equals(ev2.getlocation().getRelativeLocation())) return false;
        if(!ev1.getlocation().getCoordinates().getLongitude().equals(ev2.getlocation().getCoordinates().getLongitude())) return false;
        if(!ev1.getlocation().getCoordinates().getLatitude().equals(ev2.getlocation().getCoordinates().getLatitude())) return false;

        return true;
    }
}

package com.cardreader.demo.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {

    /*
     * Get the location of a panel from the external website and
     * check that the correct location was returned.
     * This depends on the external site returning the correct location
     *
     * This is the only method in the model that performs a function
     * The simple getters and setters are not tested
     */
    @Test
    public void resolveLocation1ExternalTest() throws Exception {
        Event event = new Event(EventUtil.panelId1, EventUtil.cardId1, EventUtil.accessAllowed1);
        event.setTimestamp(EventUtil.timestamp1);
        event.resolveLocation();

        assertTrue("An incorrect object was returned", event.getlocation() instanceof Location);

        Location location = event.getlocation();
        assertEquals("The wrong altitude was returned from site",
                (Double)EventUtil.altitude1, location.getAltitude());
        assertEquals("The wrong location name was returned from site",
                EventUtil.relativeLocation1, location.getRelativeLocation());

        Coordinates coordinates = location.getCoordinates();
        assertEquals("The wrong longitude was returned from site",
                (Double) EventUtil.longitude1, coordinates.getLongitude());
        assertEquals("The wrong latitude was returned from site",
                (Double) EventUtil.latitude1, coordinates.getLatitude());
    }

    /*
     * Repeat the test but this time with a different panel and
     * check that the correct location was returned.
     */
    @Test
    public void resolveLocation2ExternalTest() throws Exception {
        Event event = new Event(EventUtil.panelId2, EventUtil.cardId2, EventUtil.accessAllowed2);
        event.setTimestamp(EventUtil.timestamp2);
        event.resolveLocation();

        assertTrue("An incorrect object was returned", event.getlocation() instanceof Location);

        Location location = event.getlocation();
        assertEquals("The wrong altitude was returned from site",
                (Double)EventUtil.altitude2, location.getAltitude());
        assertEquals("The wrong location name was returned from site",
                EventUtil.relativeLocation2, location.getRelativeLocation());

        Coordinates coordinates = location.getCoordinates();
        assertEquals("The wrong longitude was returned from site",
                (Double) EventUtil.longitude2, coordinates.getLongitude());
        assertEquals("The wrong latitude was returned from site",
                (Double) EventUtil.latitude2, coordinates.getLatitude());
    }
}
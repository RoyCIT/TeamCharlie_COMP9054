package com.cardreader.demo.Controller;

import com.cardreader.demo.Model.Event;
import com.cardreader.demo.Model.EventUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class EventCacherUnitTest {

    private EventUtil eventUtil;
    private EventCacher eventCacher;

    @Before
    public void setUp() throws Exception {
        eventUtil = new EventUtil();
        eventCacher = new EventCacher();
    }

    @After
    public void tearDown() throws Exception {
    }

    /*
     * Test that an missing key does not return an event on an empty cache
     */
    @Test
    public void getEventFromCacheMissingKeyExceptionTest() {
        Event event1 = eventUtil.getEvent1();

        Event ev = eventCacher.getEventFromCache(event1.getKey());
        assertNull("The eventCacher should not contain this key", ev);
    }

    /*
     * Test that an event can be added to cache and retrieved by its key
     */
    @Test
    public void addEventToCacheAndRetrieveTest() throws Exception {
        Event event1 = eventUtil.getEvent1();

        eventCacher.addEventToCache(event1);
        Event ev = eventCacher.getEventFromCache(event1.getKey());
        assertNotNull("An event object should have been returned", ev);
        assertTrue("The two event objects do not have the same content", eventUtil.sameContents(event1, ev));
    }

    /*
     * Test that several events can be added to cache and the correct one retrieved
     */
    @Test
    public void addEventToCacheAndRetrieveCorrectEventTest() throws Exception {
        Event event1 = eventUtil.getEvent1();
        Event event2 = eventUtil.getEvent2();

        eventCacher.addEventToCache(event1);
        eventCacher.addEventToCache(event2);
        Event ev1 = eventCacher.getEventFromCache(event1.getKey());
        assertNotNull("An event object should have been returned", ev1);
        assertTrue("The two event objects do not have the same content", eventUtil.sameContents(event1, ev1));
        Event ev2 = eventCacher.getEventFromCache(event2.getKey());
        assertNotNull("An event object should have been returned", ev2);
        assertTrue("The two event objects do not have the same content", eventUtil.sameContents(event2, ev2));
    }

    /*
     * Test that several events can be added to cache,
     * a second event for one of the cards re-added
     * and that the second event has replaced the first
     */
    @Test
    public void addEventToCacheAndReaddEventTest() throws Exception {
        Event event1 = eventUtil.getEvent1();
        Event event2 = eventUtil.getEvent2();

        eventCacher.addEventToCache(event1);
        eventCacher.addEventToCache(event2);
        Event ev1 = eventCacher.getEventFromCache(event1.getKey());
        Event ev2 = eventCacher.getEventFromCache(event2.getKey());

        // Now that two events have been added to the cache
        // create a new event form event 1 and change the time
        // indicating that the user has swiped the card again

        Event event3 = eventUtil.getEvent1();
        Timestamp newTimestamp = java.sql.Timestamp.valueOf("2017-12-14 20:31:34.047");
        event3.setTimestamp(newTimestamp);

        eventCacher.addEventToCache(event3);
        Event ev3 = eventCacher.getEventFromCache(event3.getKey());
        assertNotNull("An event object should have been returned", ev3);
        assertFalse("The two event objects do not have the same content", eventUtil.sameContents(event3, ev1));
        assertTrue("The two event objects do not have the same content", eventUtil.sameContents(event3, ev3));
        assertEquals("This is not the new timestamp", newTimestamp, ev3.getTimestamp());
        assertNotNull("An event object should have been returned", ev2);
        assertTrue("The two event objects do not have the same content", eventUtil.sameContents(event2, ev2));

    }
}
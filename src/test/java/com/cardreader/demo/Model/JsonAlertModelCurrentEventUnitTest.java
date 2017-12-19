package com.cardreader.demo.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonAlertModelCurrentEventUnitTest {

    JSONObject currentEventJsonObject;

    /*
     * Create the json message once at the start of the test,
     * as all the test just read the message
     */
    @Before
    public void setUp() throws Exception {
        EventUtil eventUtil = new EventUtil();
        Event event1 = eventUtil.getEvent1();
        Event event2 = eventUtil.getEvent2();

        JsonAlertModel jsonAlertModel = new JsonAlertModel(event1, event2);
        JSONObject alertJsonObject = jsonAlertModel.getJsonObject();
        currentEventJsonObject = alertJsonObject.getJSONObject("currentEvent");
    }

    /*
     * Test the panelId value is in json and correct value
     */
    @Test
    public void getAlertPanelIdTest() {
        assertTrue("panelId value missing", currentEventJsonObject.has("panelId"));

        try {
            assertEquals("Unexpected panelId", currentEventJsonObject.getString("panelId"),
                    EventUtil.panelId1);
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the cardId value is in json and correct value
     */
    @Test
    public void getAlertCardIdTest() {
        assertTrue("cardId value missing", currentEventJsonObject.has("cardId"));

        try {
            assertEquals("Unexpected cardId", currentEventJsonObject.getString("cardId"),
                    EventUtil.cardId1);
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the accessAllowed value is in json and correct value
     */
    @Test
    public void getAlertAccessAllowedTest() {
        assertTrue("accessAllowed value missing", currentEventJsonObject.has("accessAllowed"));

        try {
            assertEquals("Unexpected accessAllowed", currentEventJsonObject.getBoolean("accessAllowed"),
                    EventUtil.accessAllowed1);
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the timestamp value is in json and correct value
     */
    @Test
    public void getAlertTimestampTest() {
        assertTrue("timestamp value missing", currentEventJsonObject.has("timestamp"));

        try {
            assertEquals("Unexpected timestamp", currentEventJsonObject.getString("timestamp"),
                    EventUtil.timestamp1.toString());
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the altitude value is in json and correct value
     */
    @Test
    public void getAlertAltitudeTest() {
        assertTrue("location missing", currentEventJsonObject.has("location"));

        try {
            JSONObject locationJsonObject = currentEventJsonObject.getJSONObject("location");
            assertTrue("altitude value missing", locationJsonObject.has("altitude"));
            assertEquals("Unexpected altitude", locationJsonObject.getDouble("altitude"),
                    EventUtil.altitude1, 0.001);
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the relativeLocation value is in json and correct value
     */
    @Test
    public void getAlertRelativeLocationTest() {
        assertTrue("location missing", currentEventJsonObject.has("location"));

        try {
            JSONObject locationJsonObject = currentEventJsonObject.getJSONObject("location");
            assertTrue("relativeLocation value missing", locationJsonObject.has("relativeLocation"));
            assertEquals("Unexpected relativeLocation", locationJsonObject.getString("relativeLocation"),
                    EventUtil.relativeLocation1);
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the longitude value is in json and correct value
     */

    @Test
    public void getAlertLongitudeTest() {
        assertTrue("location missing", currentEventJsonObject.has("location"));

        try {
            JSONObject locationJsonObject = currentEventJsonObject.getJSONObject("location");
            assertTrue("coordinates missing", locationJsonObject.has("coordinates"));
            JSONObject coordinatesJsonObject = locationJsonObject.getJSONObject("coordinates");
            assertTrue("longitude value missing", coordinatesJsonObject.has("longitude"));
            assertEquals("Unexpected longitude", coordinatesJsonObject.getDouble("longitude"),
                    EventUtil.longitude1, 0.001);
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the latitude value is in json and correct value
     */

    @Test
    public void getAlertLatitudeTest() {
        assertTrue("location missing", currentEventJsonObject.has("location"));

        try {
            JSONObject locationJsonObject = currentEventJsonObject.getJSONObject("location");
            assertTrue("coordinates missing", locationJsonObject.has("coordinates"));
            JSONObject coordinatesJsonObject = locationJsonObject.getJSONObject("coordinates");
            assertTrue("latitude value missing", coordinatesJsonObject.has("latitude"));
            assertEquals("Unexpected latitude", coordinatesJsonObject.getDouble("latitude"),
                    EventUtil.latitude1, 0.001);
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }
}
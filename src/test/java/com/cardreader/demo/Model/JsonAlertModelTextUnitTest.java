package com.cardreader.demo.Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonAlertModelTextUnitTest {

    JSONObject alertJsonObject;

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
        alertJsonObject = jsonAlertModel.getJsonObject();
    }

    /*
     * Test the severity value is in json and correct value
     */
    @Test
    public void getAlertSeverityTest() {
        assertTrue("severity value missing", alertJsonObject.has("severity"));

        try {
            assertEquals("Unexpected severity", alertJsonObject.getString("severity"), "High");
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the title value is in json and correct value
     */
    @Test
    public void getAlertTitleTest() {
        assertTrue("title value missing", alertJsonObject.has("title"));

        try {
            assertEquals("Unexpected title", alertJsonObject.getString("title"),
                    "Possible Cloned Access Card");
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }

    /*
     * Test the description value is in json and correct value
     */
    @Test
    public void getAlertDescriptionTest() {
        assertTrue("description value missing", alertJsonObject.has("description"));

        try {
            assertEquals("Unexpected description", alertJsonObject.getString("description"),
                    "An access-card has been used that was very recently used in another location, " +
                            "indicating that it is unlikely to be the same card-holder");
        } catch (JSONException e) {
            fail("Failed to parse json object" + e.getMessage());
        }
    }
}
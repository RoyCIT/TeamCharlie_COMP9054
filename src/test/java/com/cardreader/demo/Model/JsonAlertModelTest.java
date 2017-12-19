package com.cardreader.demo.Model;

import com.cardreader.demo.Controller.GoogleMapsValidationTest;
import com.cardreader.demo.Mqtt.MqttUtility;
import org.json.JSONObject;
import org.junit.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class JsonAlertModelTest {

    private static String card_1 = "846609c6-ce9c-41a2-b64d-ba8066a376af";



    private static String panelid1 = "7907775e-15ac-415f-a99c-e978856cffb";
    private static String panelId2 = "580ddc98-0db9-473d-a721-348f353f1ppd";

    @Test
    public void test1NoPreviousEvent() throws Exception {
        Event event1 = buildEvent(panelid1,card_1,51.524774, -0.131913, 100.0, new Timestamp(System.currentTimeMillis()));
        JsonAlertModel jsonMessage = new JsonAlertModel(event1,null );
        JSONObject sentJASONObject= jsonMessage.getJsonObject();
        assertEquals(sentJASONObject.getString("severity"),"High");
        assertEquals(sentJASONObject.getJSONObject("currentEvent").getString("panelId"),panelid1);
        assertEquals(sentJASONObject.getJSONObject("currentEvent").getString("cardId"),card_1);
        assertEquals(sentJASONObject.getJSONObject("currentEvent").getBoolean("accessAllowed"),true);

    }

    @Test
    public void test2AllEventClone() throws Exception {
        Event event1 = buildEvent(panelid1,card_1,51.524774, -0.131913, 100.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(panelId2,card_1,51.524778, -0.131913, 50.0, new Timestamp(System.currentTimeMillis()));
        JsonAlertModel jsonMessage = new JsonAlertModel(event1,event2 );
        JSONObject sentJASONObject= jsonMessage.getJsonObject();
        assertEquals(sentJASONObject.getString("severity"),"High");
        assertEquals(sentJASONObject.getJSONObject("currentEvent").getString("panelId"),panelid1);
        assertEquals(sentJASONObject.getJSONObject("currentEvent").getString("cardId"),card_1);
        assertEquals(sentJASONObject.getJSONObject("currentEvent").getBoolean("accessAllowed"),true);
        assertEquals(sentJASONObject.getString("title"),"Possible Cloned Access Card");
        assertEquals(sentJASONObject.getString("description"),"An access-card has been used that was very recently used in" +
        "another location, indicating that it is unlikely to be the same card-holder");
    }
    private static Event buildEvent(String panel, String card, Double latitude, Double longitude, Double altitude, Timestamp timestamp) {
        Event event = new Event(panel, card, true);
        Location location = new Location();
        location.setAltitude(altitude);
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(latitude);
        coordinates.setLongitude(longitude);
        location.setCoordinates(coordinates);
        event.setLocation(location);
        event.setTimestamp(timestamp);
        return event;
    }

}
package com.cardreader.demo.Store;

import com.cardreader.demo.Model.Event;
import com.cardreader.demo.Model.EventUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonStoreUnitTest {

    private static final String reasonText = "Valid event.";
    
    String expectedJsonText = "{\"reason\":\"" + reasonText + "\"," +
            "\"currentEvent\":{" +
            "\"panelId\":\"e95cffe9-f7cf-4a58-92ac-733a7350092f\"," +
            "\"cardId\":\"246609c6-ce9c-41a2-b64d-ba8066a376af\"," +
            "\"timestamp\":1513278119446," +
            "\"location\":{" +
            "\"coordinates\":{" +
            "\"longitude\":-8.532648," +
            "\"latitude\":51.883165}," +
            "\"altitude\":110.0," +
            "\"relativeLocation\":\"Main Building, First Floor East Exit, Cork, Ireland\"}," +
            "\"accessAllowed\":true}," +
            "\"previousEvent\":{" +
            "\"panelId\":\"efa66c50-1327-4aec-9661-ef7531235420\"," +
            "\"cardId\":\"846609c6-ce9c-41a2-b64d-ba8066a376af\"," +
            "\"timestamp\":1513598885708," +
            "\"location\":{" +
            "\"coordinates\":{" +
            "\"longitude\":-71.116374," +
            "\"latitude\":42.373921}," +
            "\"altitude\":50.0," +
            "\"relativeLocation\":\"Widener Library, Harvard University, Main Entrance, MA, USA\"}," +
            "\"accessAllowed\":true}," +
            "\"validEvent\":true}";
    
    /*
     * Populate the JsonStore and convert it into json string.
     * Then compare to the expected string. Making the expected
     * string is not ideal, there should be a better way to do this.
     */
    @Test
    public void createJsonMessageTest() throws Exception {
        EventUtil eventUtil = new EventUtil();
        Event event1 = eventUtil.getEvent1();
        Event event2 = eventUtil.getEvent2();

        JsonStore jsonStore = new JsonStore();
        jsonStore.setCurrentEvent(event1);
        jsonStore.setPreviousEvent(event2);
        jsonStore.setReason(reasonText);
        jsonStore.setValidEvent(true);

        ObjectMapper mapper = new ObjectMapper();
        String jsonText = mapper.writeValueAsString(jsonStore);
        assertEquals("Json message was not as expected", expectedJsonText, jsonText);
    }

}
package com.cardreader.demo.Mqtt;

import com.cardreader.demo.Controller.GoogleMapsValidationController;
import com.cardreader.demo.Controller.GoogleMapsValidationTest;
import com.cardreader.demo.Model.Event;
import org.junit.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class MqttUtilityTest {

    @Test
    public void test1simplemessage() throws Exception {
        Event event1 = GoogleMapsValidationTest.buildEvent(51.524774, -0.131913, 100.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = GoogleMapsValidationTest.buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis()));
        Boolean success = MqttUtility.getInstance().publishAlert(event1, event2);
        assertThat(success).isEqualTo(true);
    }



}
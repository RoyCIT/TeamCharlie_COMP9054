package com.cardreader.demo.Controller;


import com.cardreader.demo.Model.Coordinates;
import com.cardreader.demo.Model.Event;
import com.cardreader.demo.Model.Location;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.AssertTrue;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GoogleMapsValidationController.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class GoogleMapsValidationTest {

    private static Integer MS_PER_SEC = 1000;

    private static Event buildEvent(Double latitude, Double longitude, Double altitude, Timestamp timestamp) {
        Event event = new Event();
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

    @Test
    public void testSameCoordDiffAltitudeSameTimetamp() throws Exception {
        Event event1 = buildEvent(51.883165, -8.532648, 110.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis()));
        Boolean success = GoogleMapsValidationController.performValidation(event1, event2);
        assertThat(success).isEqualTo(false);
    }

    //more than 10 seconds should pass
    @Test
    public void testSameCoordDiffAltitudeDiffTimetamp() throws Exception {
        Event event1 = buildEvent(51.883165, -8.532648, 110.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis() + 10 * MS_PER_SEC));
        Boolean success = GoogleMapsValidationController.performValidation(event1, event2);
        assertThat(success).isEqualTo(true);
    }

    @Test
    public void testShortDistanceShortTimestamp() throws Exception {
        Event event1 = buildEvent(51.8864, -8.533581, 100.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis() + 10 * MS_PER_SEC));
        Boolean success = GoogleMapsValidationController.performValidation(event1, event2);
        assertThat(success).isEqualTo(false);
    }

    @Test
    public void testShortDistanceLongTimestamp() throws Exception {
        Event event1 = buildEvent(51.8864, -8.533581, 100.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis() + 60 * 5 * MS_PER_SEC));
        Boolean success = GoogleMapsValidationController.performValidation(event1, event2);
        assertThat(success).isEqualTo(true);
    }

    @Test
    public void testMediumDistanceShortTimestamp() throws Exception {
        Event event1 = buildEvent(51.524774, -0.131913, 100.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis() + 60 * 60 * 2 * MS_PER_SEC));
        Boolean success = GoogleMapsValidationController.performValidation(event1, event2);
        assertThat(success).isEqualTo(false);
    }

    @Test
    public void testMediumDistanceLongTimestamp() throws Exception {
        Event event1 = buildEvent(51.524774, -0.131913, 100.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis() + 60 * 60 * 4 * MS_PER_SEC));
        Boolean success = GoogleMapsValidationController.performValidation(event1, event2);
        assertThat(success).isEqualTo(true);
    }

    @Test
    public void testLongDistanceShortTimestamp() throws Exception {
        Event event1 = buildEvent(42.373916, -71.115494, 100.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis() + 60 * 60 * 7 * MS_PER_SEC));
        Boolean success = GoogleMapsValidationController.performValidation(event1, event2);
        assertThat(success).isEqualTo(false);
    }

    @Test
    public void testLongDistanceLongTimestamp() throws Exception {
        Event event1 = buildEvent(42.373916, -71.115494, 100.0, new Timestamp(System.currentTimeMillis()));
        Event event2 = buildEvent(51.883165, -8.532648, 100.0, new Timestamp(System.currentTimeMillis() + 60 * 60 * 11 * MS_PER_SEC));
        Boolean success = GoogleMapsValidationController.performValidation(event1, event2);
        assertThat(success).isEqualTo(true);
    }
}

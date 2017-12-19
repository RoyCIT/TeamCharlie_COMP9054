package com.cardreader.demo.Controller;

import com.cardreader.demo.Model.EventUtil;
import com.cardreader.demo.Model.Location;
import org.junit.Test;

import static org.junit.Assert.*;

public class HaversineValidationUnitTest {

    private static final double expectedDistance = 4697.359;

    /*
     * The test used the haversine method to calculate the
     * distance between two know locations, the value was then
     * compared to the distance obtained from the website
     * https://www.fcc.gov/media/radio/distance-and-azimuths
     */
    @Test
    public void getHaversineDistanceTest() throws Exception {
        EventUtil eventUtil = new EventUtil();
        Location location1 = eventUtil.getEvent1().getlocation();
        Location location2 = eventUtil.getEvent2().getlocation();

        double calculatedDistanceMetre = HaversineValidation.getHaversineDistance(location1, location2);
        double calculatedDistanceKm = calculatedDistanceMetre/1000;

        assertEquals("The difference in the distances was over 1km",
                expectedDistance, calculatedDistanceKm, 1.00);
    }

}
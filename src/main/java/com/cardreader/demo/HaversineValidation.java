package com.cardreader.demo;

import static java.lang.Math.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class HaversineValidation {

    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public static Integer getHaversineDistance(Location first, Location second) {

        double latDistance = toRadians(second.getCoordinates().getLatitude() - first.getCoordinates().getLatitude());
        double lonDistance = toRadians(second.getCoordinates().getLongitude() - first.getCoordinates().getLongitude());
        double a = sin(latDistance / 2) * sin(latDistance / 2)
                + cos(toRadians(first.getCoordinates().getLatitude())) * cos(toRadians(second.getCoordinates().getLatitude()))
                * sin(lonDistance / 2) * sin(lonDistance / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        double height = first.getAltitude() - second.getAltitude();

        distance = sqrt(pow(distance, 2) + pow(height, 2));

        return Integer.valueOf((int) distance * 1000); //metres
    }
}
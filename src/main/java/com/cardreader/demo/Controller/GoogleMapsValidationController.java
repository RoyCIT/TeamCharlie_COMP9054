package com.cardreader.demo.Controller;

import com.cardreader.demo.Model.GoogleMapsResponse.GoogleMapsResponse;
import com.cardreader.demo.Model.Coordinates;
import com.cardreader.demo.Model.Event;
import org.springframework.web.client.RestTemplate;

public class GoogleMapsValidationController {

    private static String GOOGLE_API_KEY = "AIzaSyBTWLLeJ7HU6ojUs39VtTzXNtyHrzjMyQg";

    private static Integer FLIGHT_THRESHOLD = 500000;
    private static Double FLIGHT_SPEED = 600.0; //600km/h

    private static Integer DRIVING_THRESHOLD = 500;
    private static Double DRIVING_SPEED = 100.0; //100km/h

    private static Double WALKING_SPEED = 5.0; //5km/h

    private static Double KMPH_TO_MPH = 0.277778;
    private static Double ALTITUDE_MPS = 0.75;

    /**
     * Method to compare two events and determine the possibility of
     * travelling between the two locations in a given timeframe
     *
     * @param previousEvent the previous event
     * @param currentEvent  the current event
     * @return              boolean reflecting if the events passed validation
     */
    public static Boolean performValidation(Event previousEvent, Event currentEvent) {
        final Integer haversineDistance = HaversineValidation.getHaversineDistance(previousEvent.getlocation(), currentEvent.getlocation());

        final Double timeElapsed = Double.valueOf(currentEvent.getTimestamp().getTime() - previousEvent.getTimestamp().getTime()) / 1000.0; // seconds
        Double timeRequired = 0.0;

        if (haversineDistance > FLIGHT_THRESHOLD) { //500km
            //Flying, do manually
            timeRequired = haversineDistance / (FLIGHT_SPEED * KMPH_TO_MPH); // distance / metres per second
        } else if (haversineDistance > DRIVING_THRESHOLD) {
            //Driving
            timeRequired = getGoogleMapDuration(previousEvent.getlocation().getCoordinates(), currentEvent.getlocation().getCoordinates(), "driving");
            if (timeRequired == null) { // If the google request fails
                timeRequired = haversineDistance / (FLIGHT_SPEED * KMPH_TO_MPH); // distance / metres per second
            }
        } else {
            //Walking
            timeRequired = getGoogleMapDuration(previousEvent.getlocation().getCoordinates(), currentEvent.getlocation().getCoordinates(), "walking");
            if (timeRequired == null) { // If the google request fails
                timeRequired = haversineDistance / (WALKING_SPEED * KMPH_TO_MPH); // distance / metres per second
            }
            double altitudeChange = Math.abs(previousEvent.getlocation().getAltitude() - currentEvent.getlocation().getAltitude());
            timeRequired = timeRequired + (ALTITUDE_MPS * altitudeChange);
        }

        return timeRequired < timeElapsed;

    }

    /**
     * Method to call Google Maps Distance Matrix API to determine the distance between two coordinates
     *
     * @param first the first coordinate
     * @param second  the second coordinte
     * @param mode  the mode. one of; "bicycling", "walking", "driving", "transit"
     * @return              Double the distance between two coordinates based on result of api call
     */
    private static Double getGoogleMapDuration(Coordinates first, Coordinates second, String mode) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?";

        //API Key
        url = url + "key=" + GOOGLE_API_KEY;

        //Mode
        url = url + "&mode=" + mode;

        //Coordinates
        url = url + "&origins=" + first.getLatitude() + "," + first.getLongitude();
        url = url + "&destinations=" + second.getLatitude() + "," + second.getLongitude();

        RestTemplate restTemplate = new RestTemplate();
        GoogleMapsResponse result = restTemplate.getForObject(url, GoogleMapsResponse.class);

        try {
            return Double.valueOf(result.getRows().get(0).getElements().get(0).getDuration().getValue());
        } catch (NullPointerException e) {
            return null;
        }
    }
}

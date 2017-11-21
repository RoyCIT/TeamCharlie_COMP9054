package com.cardreader.demo;

import com.cardreader.demo.GoogleMapsResponse.GoogleMapsResponse;
import org.springframework.web.client.RestTemplate;

import static java.lang.Math.*;

public class GoogleMapsValidation {
    private static String GOOGLE_API_KEY = "AIzaSyBTWLLeJ7HU6ojUs39VtTzXNtyHrzjMyQg";

    public static Boolean performValidation(Event previousEvent, Event currentEvent) {
        Integer distance = getGoogleMapDistance(previousEvent.getlocation().getCoordinates(), currentEvent.getlocation().getCoordinates());

        if (distance == null) {
            // If any issues getting the google map distance fall back on the Haversine method
            distance = HaversineValidation.getHaversineDistance(previousEvent.getlocation(), currentEvent.getlocation());
        }

        Long timeDifference = (currentEvent.getTimestamp().getTime() - previousEvent.getTimestamp().getTime()) / 1000; // seconds

        Integer speed = 0;
        if (distance > 500000) { //500km
            //Flying (600 km/h)
            speed = 600;
        } else if (distance > 1000) {// 1km
            //Driving (100 km/h)
            speed = 100;
        } else {
            //Walking (5 km/h)
            speed = 5;
        }

        Double time = distance / (speed * 0.277778); // distance / metres per second

        return time < timeDifference;
    }

    private static Integer getGoogleMapDistance(Coordinates first, Coordinates second) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?";

        //API Key
        url = url + "key=" + GOOGLE_API_KEY;

        //Mode
        url = url + "&mode=transit";

        //Coordinates
        url = url + "&origins=" + first.getLatitude() + "," + first.getLongitude();
        url = url + "&destinations=" + second.getLatitude() + "," + second.getLongitude();

        RestTemplate restTemplate = new RestTemplate();
        GoogleMapsResponse result = restTemplate.getForObject(url, GoogleMapsResponse.class);
        Integer distance = result.getRows().get(0).getElements().get(0).getDistance().getValue();
        return distance;
    }
}

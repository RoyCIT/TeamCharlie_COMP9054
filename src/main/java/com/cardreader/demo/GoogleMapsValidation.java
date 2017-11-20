package com.cardreader.demo;

import com.cardreader.demo.GoogleMapsResponse.GoogleMapsResponse;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

public class GoogleMapsValidation {
    private static String GOOGLE_API_KEY = "AIzaSyBTWLLeJ7HU6ojUs39VtTzXNtyHrzjMyQg";

    public static Boolean performValidation(Event previousEvent, Event currentEvent) {
        Integer distance = getGoogleMapDistance(previousEvent.getlocation().getCoordinates(), currentEvent.getlocation().getCoordinates());

        if (distance == null) {
            distance = getHaversineDistance(previousEvent.getlocation().getCoordinates(), currentEvent.getlocation().getCoordinates());
        }
        System.out.print(currentEvent.getTimestamp().toString() + " : " + previousEvent.getTimestamp().toString());
        Long timeDifference = (currentEvent.getTimestamp().getTime() - previousEvent.getTimestamp().getTime())/1000;
        Double speed = 0.0;
        if (distance > 500000) { //500km
            //Flying (800 km/h)
            speed = 222.222;
        } else if (distance > 1000) {// 1km
            //Driving (100 km/h)
            speed = 27.7778;
        } else {
            //Walking (5 km/h)
            speed = 1.38889;
        }

        Double time = distance / speed;

        return time < timeDifference;
    }

    private static Integer getHaversineDistance(Coordinates first, Coordinates second) {
        return 100;
    }

    private static Integer getGoogleMapDistance(Coordinates first, Coordinates second) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?";

        //API Key
        url = url + "key=" + GOOGLE_API_KEY;

        //Mode
        url = url + "&mode=transit";

        //Coordinates
        url = url + "&origins=" + first.getLatitude() + "," + first.getLongitude();

        //TODO: Investigate bug with switched lat/lng
        url = url + "&destinations=" + second.getLongitude() + "," + second.getLatitude();

        RestTemplate restTemplate = new RestTemplate();
        GoogleMapsResponse result = restTemplate.getForObject(url, GoogleMapsResponse.class);
        Integer distance = result.getRows().get(0).getElements().get(0).getDistance().getValue();
        return distance;
    }
}

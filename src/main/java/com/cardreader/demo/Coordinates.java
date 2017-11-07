package com.cardreader.demo;

public class Coordinates {

    private final double longitude;
    private final double latitude;

    public Coordinates() {
       // this.latitude = json.latitude;
       // this.longitude = json.longitude;
        this.latitude = 42.37392;
        this.longitude = -71.116374;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}

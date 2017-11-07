package com.cardreader.demo;

public class Location {

    private final String panelId;
    private Coordinates coordinates;
    private double altitude = 0;


    public Location(String panelId) {
        this.panelId = panelId;
    }

    public void resolve() {
        //call api...then
        coordinates = new Coordinates();
        altitude = 50;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Double getAltitude() {
        return altitude;
    }

}

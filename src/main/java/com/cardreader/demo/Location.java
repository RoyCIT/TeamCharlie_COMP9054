package com.cardreader.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    private Double altitude;
    private String relativeLocation;
    private Coordinates coordinates;

    public Location() {
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getRelativeLocation() {
        return relativeLocation;
    }

    public void setRelativeLocation(String relativeLocation) {
        this.relativeLocation = relativeLocation;
    }

    public Coordinates getValue() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Location{" +
                "altitude='" + altitude + '\'' +
                "relativeLocation='" + relativeLocation + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}

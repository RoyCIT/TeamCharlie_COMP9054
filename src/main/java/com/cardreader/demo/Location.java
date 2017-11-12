package com.cardreader.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "coordinates", "altitude", "relativeLocation" })
public class Location {

    private Double altitude;
    private String relativeLocation;
    private Coordinates coordinates;

    public Location() {
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public Double getAltitude() {
        return this.altitude;
    }

    public String getRelativeLocation() {
        return this.relativeLocation;
    }

    public void setRelativeLocation(String relativeLocation) {
        this.relativeLocation = relativeLocation;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
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

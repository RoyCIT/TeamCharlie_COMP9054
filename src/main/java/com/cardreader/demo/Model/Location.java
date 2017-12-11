package com.cardreader.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Embedded;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "coordinates", "altitude", "relativeLocation" })
public class Location implements ILocation{

    private Double altitude;
    private String relativeLocation;
    @Embedded
    private Coordinates coordinates;

    public Location() {
    }

    @Override
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    @Override
    public Double getAltitude() {
        return this.altitude;
    }

    @Override
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
}

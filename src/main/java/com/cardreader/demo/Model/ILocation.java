package com.cardreader.demo.Model;

public interface ILocation {

    Coordinates getCoordinates();

    Double getAltitude();

    String getRelativeLocation();

    public void setRelativeLocation(String relativeLocation);

    public void setAltitude(Double altitude);

    public void setCoordinates(Coordinates coordinates);

}

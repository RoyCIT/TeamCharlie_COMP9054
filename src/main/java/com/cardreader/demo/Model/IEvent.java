package com.cardreader.demo.Model;

import java.sql.Timestamp;

public interface IEvent {

    void resolveLocation();

    String getPanelId();

    String getCardId();

    Timestamp getTimestamp();

    String getKey();

    Location getlocation();

    String getAccessAllowed();
}

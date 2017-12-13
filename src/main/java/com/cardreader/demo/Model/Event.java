package com.cardreader.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@JsonPropertyOrder({ "panelId", "cardId", "timestamp", "location", "accessAllowed" })
@Entity
public class Event implements IEvent {
    @Column
    private final String panelId;
    @Column
    private final String cardId;
    @Column
    @Embedded
    private Location location;
    @Column
    private final boolean accessAllowed;
    @JsonIgnore
    @Id
    private final String key;
    private Timestamp timestamp;

    public Event(String panelId, String cardId, boolean accessAllowed) {
        this.panelId = panelId;
        this.cardId = cardId;
        this.accessAllowed = accessAllowed;
        this.key = cardId;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // default constructor for serialization
    public Event()
    {
        this("", "", false);
    }

    public void resolveLocation() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://uuidlocator.cfapps.io/api/panels/";

        try {
            location = restTemplate.getForObject(url + panelId, Location.class);
        }
        catch (HttpMessageNotReadableException notReadable) {
            // Do nothing??
        }
        catch (HttpServerErrorException serverError) {
            // Do nothing??
        }
    }

    public String getPanelId() {
        return this.panelId;
    }

    public String getCardId() {
        return this.cardId;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public String getKey() {
        return this.key;
    }

    public Location getlocation() {
        return this.location;
    }

    public void setLocation(Location l) {
        this.location = l;
    }

    public boolean getAccessAllowed() {
        return this.accessAllowed;
    }

}


package com.cardreader.demo.GoogleMapsResponse;

import java.util.HashMap;
import java.util.Map;

import com.cardreader.demo.GoogleMapsResponseDistance;
import com.cardreader.demo.GoogleMapsResponseDuration;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "distance",
    "duration",
    "status"
})
public class GoogleMapsResponseElement {

    @JsonProperty("distance")
    private GoogleMapsResponseDistance distance;
    @JsonProperty("duration")
    private GoogleMapsResponseDuration duration;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("distance")
    public GoogleMapsResponseDistance getDistance() {
        return distance;
    }

    @JsonProperty("distance")
    public void setDistance(GoogleMapsResponseDistance distance) {
        this.distance = distance;
    }

    @JsonProperty("duration")
    public GoogleMapsResponseDuration getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(GoogleMapsResponseDuration duration) {
        this.duration = duration;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

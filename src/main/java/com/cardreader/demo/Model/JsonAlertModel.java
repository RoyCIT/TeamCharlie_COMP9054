package com.cardreader.demo.Model;

import com.cardreader.demo.Model.Event;
import org.json.JSONObject;

public class JsonAlertModel {


        private Event currentEvent;
        private Event previousEvent;
        private JSONObject jsonMessage = new JSONObject();
        private static final String  severity = "High";
        private static final String title = "Possible Cloned Access Card";
        private static final String description = "An access-card has been used that was very recently used in" +
                "another location, indicating that it is unlikely to be the same card-holder";

        public JsonAlertModel(Event curEvent, Event prevEvent) {
            this.currentEvent = curEvent;
            this.previousEvent = prevEvent;
        }


        public JSONObject getJsonObject() {
            jsonMessage.put("severity",severity);
            jsonMessage.put("title",title);
            jsonMessage.put("description",description);
            //Current Event
            JSONObject currItem = new JSONObject();
            currItem.put("panelId",currentEvent.getPanelId());
            currItem.put("cardId",currentEvent.getCardId());
            currItem.put("timestamp",currentEvent.getTimestamp());
            currItem.put("accessAllowed",currentEvent.getAccessAllowed());
            JSONObject currLocation = new JSONObject();
            JSONObject currCorr = new JSONObject();
            currCorr.put("longitude",currentEvent.getlocation().getCoordinates().getLongitude());
            currCorr.put("latitude",currentEvent.getlocation().getCoordinates().getLatitude());

            currLocation.put("altitude", currentEvent.getlocation().getAltitude());
            currLocation.put("relativeLocation", currentEvent.getlocation().getRelativeLocation());

            currLocation.put("coordinates",currCorr);
            currItem.put("location",currLocation);
            jsonMessage.put("currentEvent",currItem);

            //Previous Event
            if (null != previousEvent)
            {
                JSONObject prvItem = new JSONObject();
                prvItem.put("panelId", previousEvent.getPanelId());
                prvItem.put("cardId", previousEvent.getCardId());
                prvItem.put("timestamp", previousEvent.getTimestamp());
                prvItem.put("accessAllowed", previousEvent.getAccessAllowed());
                JSONObject prvLocation = new JSONObject();
                JSONObject prvCorr = new JSONObject();
                prvCorr.put("longitude", previousEvent.getlocation().getCoordinates().getLongitude());
                prvCorr.put("latitude", previousEvent.getlocation().getCoordinates().getLatitude());

                prvLocation.put("altitude", previousEvent.getlocation().getAltitude());
                prvLocation.put("relativeLocation", previousEvent.getlocation().getRelativeLocation());

                prvLocation.put("coordinates", prvCorr);
                prvItem.put("location", prvLocation);
                jsonMessage.put("previousEvent", prvItem);
            }



            return this.jsonMessage;

        }





    }



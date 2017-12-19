package com.cardreader.demo.Mqtt;

import com.cardreader.demo.Model.Event;
import com.cardreader.demo.Model.JsonAlertModel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * This is the MQTT Utility class . It is singleton. It handles
 * Connecting, and Publishing of messages to the MQ Broker
 * on the default port
 */
public class MqttUtility {

    private static MqttUtility instance = null;

    private MqttClient client;


    private String host ="tcp://localhost";


    private String port = "1883";


    private String topic = "validation.alerts";

    private MqttUtility(){

    }

    public static MqttUtility getInstance(){
        if(MqttUtility.instance == null){
            MqttUtility.instance = new MqttUtility();
        }
        return MqttUtility.instance;
    }
    /**
     * This method is used to Connect to a MQTT Broker
     * and Publish an message to it. The Message is created from
     * two Events and generated into a JSON message.
     * @param currentEvent This the current Event/Card Swipe
     * @param prevEvent  This the previous Event/Card Swipe
     */
    public boolean publishAlert(Event currentEvent, Event prevEvent) {
        try {
            client = new MqttClient(host+":"+port, "CloneAlert");
            client.connect();
            MqttMessage message = new MqttMessage();
            JsonAlertModel jsonMessage = new JsonAlertModel(currentEvent,prevEvent );
            message.setPayload(jsonMessage.getJsonObject().toString().getBytes());
            client.publish(topic, message);
            client.disconnect();
            return true;
        } catch (MqttException e) {
            System.out.println("Unable to Connect to Message Broker, unable to send Alert");
            return false;
        }
    }

}

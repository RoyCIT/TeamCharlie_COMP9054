package com.cardreader.demo.Mqtt;

import com.cardreader.demo.Event;
import com.cardreader.demo.JsonAlertModel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


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

    public void publishAlert(Event currentEvent, Event prevEvent) {
        try {
            client = new MqttClient(host+":"+port, "CloneAlert");
            client.connect();
            MqttMessage message = new MqttMessage();
            JsonAlertModel jsonMessage = new JsonAlertModel(currentEvent,prevEvent );
            message.setPayload(jsonMessage.getJsonObject().toString().getBytes());
            client.publish(topic, message);
            client.disconnect();
        } catch (MqttException e) {
            System.out.println("Unable to Connect to Message Broker, unable to send Alert");
        }
    }

}

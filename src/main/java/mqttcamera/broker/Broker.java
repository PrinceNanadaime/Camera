package main.java.mqttcamera.broker;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface Broker {
    @Gateway(requestChannel = "mqttOutboundChannel")
    void publish(String message, @Header(MqttHeaders.TOPIC) String topic) throws MqttException;
    void subscribe(@Header(MqttHeaders.TOPIC) String topic) throws InterruptedException, MqttException;
}

package main.java.mqttcamera.broker;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import mqttcamera.model.AlarmEntity;
import mqttcamera.model.CameraEntity;
import mqttcamera.repository.MqttRepository;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.ConnectionLostException;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BrokerImpl implements Broker, MqttCallback {

    private final IMqttClient client = new MqttClient("things.io.clipboard","tcp://localhost:8080");

    public BrokerImpl() throws MqttException {
    }

    @Autowired
    private MqttRepository<AlarmEntity,Long> alarmEntityLongMqttRepository;

    @Autowired
    private MqttRepository<CameraEntity,Long> cameraEntityLongMqttRepository;

    @Override
    public void publish(String message, String topic) throws MqttException {
        if ( !client.isConnected()) {
            throw new NullPointerException();
        }
        MqttMessage msg = new MqttMessage(message.getBytes());
        msg.setQos(0);
        msg.setRetained(true);
        client.publish("devices/camera",msg);
    }

    @Override
    public void subscribe(String topic) throws InterruptedException, MqttException {
        CountDownLatch receivedSignal = new CountDownLatch(10);
        client.subscribe("devices/camera");
        receivedSignal.await(receivedSignal.getCount(), TimeUnit.SECONDS);
    }

    @Override
    public void connectionLost(Throwable throwable) {
        throw new ConnectionLostException("");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (!Objects.equals(topic, "devices/camera")) throw new IllegalArgumentException();
        if (Arrays.toString(message.getPayload()).equals("TRUE")){
            alarmEntityLongMqttRepository.save(new AlarmEntity(
                    cameraEntityLongMqttRepository.findById(1L).get(),new Date(1L),"Motion detected!"));
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("Complete!");
    }
}

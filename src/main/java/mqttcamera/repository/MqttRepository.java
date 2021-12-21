package main.java.mqttcamera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MqttRepository<E, L> extends JpaRepository<E, L> {
}


package main.java.mqttcamera.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import mqttcamera.broker.Broker;
import mqttcamera.exception.AlarmNotFoundException;
import mqttcamera.model.AlarmEntity;
import mqttcamera.model.CameraEntity;
import mqttcamera.repository.MqttRepositoryImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestApiController {

    private final MqttRepositoryImpl<AlarmEntity, Long> repository;
    private Broker broker;

    public RestApiController(MqttRepositoryImpl<AlarmEntity, Long> alarmDevice) {
        this.repository = alarmDevice;
    }

    @PostMapping("/publish")
    public ResponseEntity<?> publish(@RequestBody MessageForm mqttMessage) {
        try {
            broker.publish(mqttMessage.getMessage(),mqttMessage.getTopic());
            return ResponseEntity.ok("Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok("fail");
        }
    }

    @GetMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody String topic) {
        try {
            broker.subscribe(topic);
            return ResponseEntity.ok("Success");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.ok("fail");
        }
    }

    @GetMapping()
    public List<AlarmEntity> getAlarmHistory() {
        try {
            return repository.getAll();
        } catch (AlarmNotFoundException e) {
            log.info("Alarm history is empty!");
            return new ArrayList<>();
        }
    }

    @GetMapping("/{id}")
    public AlarmEntity alarmById(@PathVariable long id) {
        return repository.getAlarmByID(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.delete(alarmById(id));
    }

    @PostMapping("/new")
    public void save(@RequestBody AlarmForm alarmForm) {
        repository.save(new AlarmEntity(
                alarmForm.getCamera(),
                alarmForm.getDate(), alarmForm.getDescription()
        ));
    }

    @Data
    static class AlarmForm {
        private Date date;
        private String description;
        private CameraEntity camera;
    }

    @Data
    static class MessageForm {
        private String message;
        private String topic;
    }
}
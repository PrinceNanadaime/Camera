package main.java.mqttcamera.repository;

import mqttcamera.exception.AlarmNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MqttRepositoryImpl<E, L> {

    private MqttRepository<E, L> repository;

    @Autowired
    public void setRepository(MqttRepository<E, L> repository) {
        this.repository = repository;
    }

    public E getAlarmByID(L id) {
        Optional<E> optional = repository.findById(id);
        return optional.orElse(null);
    }

    public void delete(E e) {
        repository.delete(e);
    }

    public void save(E e) {
        repository.save(e);
    }

    public List<E> getAll() throws AlarmNotFoundException {
        List<E> alarmList = repository.findAll();
        if (!alarmList.isEmpty()) {
            return alarmList;
        } else {
            throw new AlarmNotFoundException();
        }
    }
}

package main.java.mqttcamera.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlarmNotFoundException extends RuntimeException {

    public AlarmNotFoundException() {
        log.error("Current alarm has not been found in the alarm history!");
    }
}


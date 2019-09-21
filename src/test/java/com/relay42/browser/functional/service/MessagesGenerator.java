package com.relay42.browser.functional.service;

import com.relay42.generated.OutsideTemperature;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class MessagesGenerator {

    private MessagesGenerator() {
    }

    public static OutsideTemperature generateOutsideTemperatureMessage() {
        OutsideTemperature outsideTemperature = new OutsideTemperature();
        outsideTemperature.setDeviceId("someDeviceId" + Math.random());
        outsideTemperature.setGroupId("someGroupId" + Math.random());
        outsideTemperature.setCreated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        double temperature = Math.random() * 100;
        outsideTemperature.setValue(temperature);

        return outsideTemperature;
    }

}

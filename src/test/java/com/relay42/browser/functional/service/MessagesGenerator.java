package com.relay42.browser.functional.service;

import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public static OutsideHumidity generateOutsideHumidityMessage() {
        OutsideHumidity outsideHumidity = new OutsideHumidity();
        outsideHumidity.setDeviceId("someDeviceId" + Math.random());
        outsideHumidity.setGroupId("someGroupId" + Math.random());
        outsideHumidity.setCreated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        double temperature = Math.random() * 100;
        outsideHumidity.setValue(temperature);

        return outsideHumidity;
    }

    public static WindSpeed generateWindSpeedMessage() {
        WindSpeed windSpeed = new WindSpeed();
        windSpeed.setDeviceId("someDeviceId" + Math.random());
        windSpeed.setGroupId("someGroupId" + Math.random());
        windSpeed.setCreated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        double temperature = Math.random() * 100;
        windSpeed.setValue(temperature);

        return windSpeed;
    }

}

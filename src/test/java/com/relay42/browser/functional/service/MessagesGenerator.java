package com.relay42.browser.functional.service;

import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class MessagesGenerator {

    private static final int NUMBER_OF_MESSAGES = 100;

    private MessagesGenerator() {
    }

    public static List<OutsideTemperature> generateOutsideTemperatureList() {
        List<OutsideTemperature> outsideTemperatures = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            outsideTemperatures.add(MessagesGenerator.generateOutsideTemperatureMessage());
        }

        for (int i = 0; i < NUMBER_OF_MESSAGES / 2; i++) {
            outsideTemperatures.get(i).setDeviceId(outsideTemperatures.get(0).getDeviceId());
        }

        for (int i = NUMBER_OF_MESSAGES / 2; i < NUMBER_OF_MESSAGES; i++) {
            outsideTemperatures.get(i).setGroupId(outsideTemperatures.get(0).getGroupId());
        }

        return outsideTemperatures;
    }

    public static List<OutsideHumidity> generateOutsideHumidityList() {
        List<OutsideHumidity> outsideHumidities = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            outsideHumidities.add(MessagesGenerator.generateOutsideHumidityMessage());
        }

        for (int i = 0; i < NUMBER_OF_MESSAGES / 2; i++) {
            outsideHumidities.get(i).setDeviceId(outsideHumidities.get(0).getDeviceId());
        }

        for (int i = NUMBER_OF_MESSAGES / 2; i < NUMBER_OF_MESSAGES; i++) {
            outsideHumidities.get(i).setGroupId(outsideHumidities.get(0).getGroupId());
        }

        return outsideHumidities;
    }

    public static List<WindSpeed> generateWindSpeedList() {
        List<WindSpeed> windSpeeds = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            windSpeeds.add(MessagesGenerator.generateWindSpeedMessage());
        }

        for (int i = 0; i < NUMBER_OF_MESSAGES / 2; i++) {
            windSpeeds.get(i).setDeviceId(windSpeeds.get(0).getDeviceId());
        }

        for (int i = NUMBER_OF_MESSAGES / 2; i < NUMBER_OF_MESSAGES; i++) {
            windSpeeds.get(i).setGroupId(windSpeeds.get(0).getGroupId());
        }

        return windSpeeds;
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
}

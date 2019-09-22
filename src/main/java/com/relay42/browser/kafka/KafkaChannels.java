package com.relay42.browser.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannels {

    String OUTSIDE_TEMPERATURE_INPUT_CHANNEL = "outsideTemperatureInputChannel";
    String OUTSIDE_HUMIDITY_INPUT_CHANNEL = "outsideHumidityInputChannel";
    String WIND_SPEED_INPUT_CHANNEL = "windSpeedInputChannel";

    @Input(OUTSIDE_TEMPERATURE_INPUT_CHANNEL)
    MessageChannel outsideTemperatureInputChannel();

    @Input(OUTSIDE_HUMIDITY_INPUT_CHANNEL)
    MessageChannel outsideHumidityInputChannel();

    @Input(WIND_SPEED_INPUT_CHANNEL)
    MessageChannel windSpeedInputChannel();
}

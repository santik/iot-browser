package com.relay42.browser.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannels {

    String OUTSIDE_TEMPERATURE_INPUT_CHANNEL = "outsideTemperatureInputChannel";

    @Input(OUTSIDE_TEMPERATURE_INPUT_CHANNEL)
    MessageChannel outsideTemperatureInputChannel();
}

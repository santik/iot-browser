package com.relay42.browser.functional.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannels {

    String OUTSIDE_TEMPERATURE_OUTPUT_CHANNEL = "outsideTemperatureOutputChannel";

    @Output(OUTSIDE_TEMPERATURE_OUTPUT_CHANNEL)
    MessageChannel outsideTemperatureOutputChannel();
}

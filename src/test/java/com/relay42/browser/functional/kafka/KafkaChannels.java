package com.relay42.browser.functional.kafka;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface KafkaChannels {

    String OUTSIDE_TEMPERATURE_OUTPUT_CHANNEL = "outsideTemperatureOutputChannel";
    String OUTSIDE_HUMIDITY_OUTPUT_CHANNEL = "outsideHumidityOutputChannel";
    String WIND_SPEED_OUTPUT_CHANNEL = "windSpeedOutputChannel";

    @Output(OUTSIDE_TEMPERATURE_OUTPUT_CHANNEL)
    MessageChannel outsideTemperatureOutputChannel();

    @Output(OUTSIDE_HUMIDITY_OUTPUT_CHANNEL)
    MessageChannel outsideHumidityOutputChannel();

    @Output(WIND_SPEED_OUTPUT_CHANNEL)
    MessageChannel windSpeedOutputChannel();
}

package com.relay42.browser.functional.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding({KafkaChannels.class})
public class OutsideTemperatureTestPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutsideTemperatureTestPublisher.class);
    private final MessageChannel outsideTemperatureOutputChannel;

    @Autowired
    public OutsideTemperatureTestPublisher(MessageChannel outsideTemperatureOutputChannel) {
        this.outsideTemperatureOutputChannel = outsideTemperatureOutputChannel;
    }

    public void publish(Object message) {
        LOGGER.info("Publishing to outsideTemperatureOutputChannel channel. Message: {}", message.toString());
        outsideTemperatureOutputChannel.send(MessageBuilder.withPayload(message).build());
    }
}

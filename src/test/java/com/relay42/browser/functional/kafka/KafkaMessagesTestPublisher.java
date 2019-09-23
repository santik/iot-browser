package com.relay42.browser.functional.kafka;

import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding({KafkaChannels.class})
public class KafkaMessagesTestPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessagesTestPublisher.class);
    private final MessageChannel outsideTemperatureOutputChannel;
    private final MessageChannel outsideHumidityOutputChannel;
    private final MessageChannel windSpeedOutputChannel;

    @Autowired
    public KafkaMessagesTestPublisher(MessageChannel outsideTemperatureOutputChannel,
                                      MessageChannel outsideHumidityOutputChannel,
                                      MessageChannel windSpeedOutputChannel) {
        this.outsideTemperatureOutputChannel = outsideTemperatureOutputChannel;
        this.outsideHumidityOutputChannel = outsideHumidityOutputChannel;
        this.windSpeedOutputChannel = windSpeedOutputChannel;
    }

    public void publishOutsideTemperature(OutsideTemperature message) {
        LOGGER.info("Publishing to outsideTemperatureOutputChannel channel. Message: {}", message.toString());
        outsideTemperatureOutputChannel.send(MessageBuilder.withPayload(message).build());
    }

    public void publishOutsideHumidity(OutsideHumidity message) {
        LOGGER.info("Publishing to outsideHumidityOutputChannel channel. Message: {}", message.toString());
        outsideHumidityOutputChannel.send(MessageBuilder.withPayload(message).build());
    }

    public void publishWindSpeed(WindSpeed message) {
        LOGGER.info("Publishing to windSpeedOutputChannel channel. Message: {}", message.toString());
        windSpeedOutputChannel.send(MessageBuilder.withPayload(message).build());
    }
}

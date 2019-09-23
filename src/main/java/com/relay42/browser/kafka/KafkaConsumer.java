package com.relay42.browser.kafka;

import com.relay42.browser.processor.KafkaMessageProcessor;
import com.relay42.generated.KafkaMessage;
import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(KafkaChannels.class)
public class KafkaConsumer {

    private KafkaMessageProcessor messageProcessor;

    public KafkaConsumer(KafkaMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @StreamListener(KafkaChannels.OUTSIDE_TEMPERATURE_INPUT_CHANNEL)
    public void consumeOutsideTemperature(Message<OutsideTemperature> message) {
        messageProcessor.process(message.getPayload());
    }

    @StreamListener(KafkaChannels.OUTSIDE_HUMIDITY_INPUT_CHANNEL)
    public void consumeOutsideHumidity(Message<OutsideHumidity> message) {
        messageProcessor.process(message.getPayload());
    }

    @StreamListener(KafkaChannels.WIND_SPEED_INPUT_CHANNEL)
    public void consumeWindSpeed(Message<WindSpeed> message) {
        messageProcessor.process(message.getPayload());
    }
}

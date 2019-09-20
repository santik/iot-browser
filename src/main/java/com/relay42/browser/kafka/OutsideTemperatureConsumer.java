package com.relay42.browser.kafka;

import com.relay42.browser.processor.OutsideTemperatureProcessor;
import com.relay42.generated.OutsideTemperature;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(KafkaChannels.class)
public class OutsideTemperatureConsumer {

    private OutsideTemperatureProcessor processor;

    public OutsideTemperatureConsumer(OutsideTemperatureProcessor processor) {
        this.processor = processor;
    }

    @StreamListener(KafkaChannels.OUTSIDE_TEMPERATURE_INPUT_CHANNEL)
    public void consume(Message<OutsideTemperature> message) {
        processor.process(message.getPayload());
    }
}

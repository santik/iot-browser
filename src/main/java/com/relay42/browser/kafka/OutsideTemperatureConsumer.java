package com.relay42.browser.kafka;

import com.relay42.browser.processor.OutsideHumidityProcessor;
import com.relay42.browser.processor.OutsideTemperatureProcessor;
import com.relay42.browser.processor.WindSpeedProcessor;
import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(KafkaChannels.class)
public class OutsideTemperatureConsumer {

    private OutsideTemperatureProcessor outsideTemperatureProcessor;
    private OutsideHumidityProcessor outsideHumidityProcessor;
    private WindSpeedProcessor windSpeedProcessor;

    public OutsideTemperatureConsumer(OutsideTemperatureProcessor outsideTemperatureProcessor,
                                      OutsideHumidityProcessor outsideHumidityProcessor,
                                      WindSpeedProcessor windSpeedProcessor) {
        this.outsideTemperatureProcessor = outsideTemperatureProcessor;
        this.outsideHumidityProcessor = outsideHumidityProcessor;
        this.windSpeedProcessor = windSpeedProcessor;
    }

    @StreamListener(KafkaChannels.OUTSIDE_TEMPERATURE_INPUT_CHANNEL)
    public void consumeOutsideTemperature(Message<OutsideTemperature> message) {
        outsideTemperatureProcessor.process(message.getPayload());
    }

    @StreamListener(KafkaChannels.OUTSIDE_HUMIDITY_INPUT_CHANNEL)
    public void consumeOutsideHumidity(Message<OutsideHumidity> message) {
        outsideHumidityProcessor.process(message.getPayload());
    }

    @StreamListener(KafkaChannels.WIND_SPEED_INPUT_CHANNEL)
    public void consumeWindSpeed(Message<WindSpeed> message) {
        windSpeedProcessor.process(message.getPayload());
    }
}

package com.relay42.browser.kafka;

import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    private KafkaConsumer kafkaConsumer;

    @Mock
    private KafkaMessageProcessor kafkaMessageProcessor;


    @BeforeEach
    void setUp() {
        kafkaConsumer = new KafkaConsumer(kafkaMessageProcessor);
    }

    @Test
    void consumeOutsideTemperature_withMessage_shouldPassPayloadToProcessor() {
        //arrange
        Message message = mock(Message.class);
        OutsideTemperature outsideTemperature = new OutsideTemperature();
        when(message.getPayload()).thenReturn(outsideTemperature);

        //act
        kafkaConsumer.consumeOutsideTemperature(message);

        //assert
        verify(kafkaMessageProcessor).process(outsideTemperature);
    }

    @Test
    void consumeOutsideHumidity_withMessage_shouldPassPayloadToProcessor() {
        //arrange
        Message message = mock(Message.class);
        OutsideHumidity outsideHumidity = new OutsideHumidity();
        when(message.getPayload()).thenReturn(outsideHumidity);

        //act
        kafkaConsumer.consumeOutsideHumidity(message);

        //assert
        verify(kafkaMessageProcessor).process(outsideHumidity);
    }

    @Test
    void consumeWindSpeed_withMessage_shouldPassPayloadToProcessor() {
        //arrange
        Message message = mock(Message.class);
        WindSpeed windSpeed = new WindSpeed();
        when(message.getPayload()).thenReturn(windSpeed);

        //act
        kafkaConsumer.consumeWindSpeed(message);

        //assert
        verify(kafkaMessageProcessor).process(windSpeed);
    }
}

package com.relay42.browser.processor;

import com.relay42.browser.database.model.IOTReadingsModel;
import com.relay42.browser.database.repository.IoTReadingsRepository;
import com.relay42.generated.WindSpeed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaMessageProcessorTest {

    @Mock
    private IoTReadingsRepository repository;

    @Captor
    private ArgumentCaptor<IOTReadingsModel> iotReadingsModelArgumentCaptor;

    @Test
    void process_withKafkaMessage_shouldPassItToRepository() {
        //arrange
        KafkaMessageProcessor processor = new KafkaMessageProcessor(repository);
        WindSpeed message = new WindSpeed("someDeviceId", "someGroupId", 1d, new Date());

        //act
        processor.process(message);

        //assert
        verify(repository).save(iotReadingsModelArgumentCaptor.capture());
        IOTReadingsModel model = iotReadingsModelArgumentCaptor.getValue();
        assertEquals(message.getDeviceId(), model.getDeviceId());
        assertEquals(message.getGroupId(), model.getGroupId());
        assertEquals(Double.valueOf(message.getValue()), Double.valueOf(model.getValue()));
        assertEquals(message.getCreated(), model.getCreated());
    }
}
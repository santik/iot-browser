package com.relay42.browser.service;

import com.relay42.browser.database.IOTReadingsModel;
import com.relay42.browser.database.IoTReadingsRepository;
import com.relay42.generated.ReadingRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IOTReadingsModelServiceTest {

    @Mock
    private IoTReadingsRepository ioTReadingsRepository;

    @Test
    void getValue_withByDeviceIdReadingRequest_shouldReturnCorrectValue() {
        //arrange
        IOTReadingsModelService service = new IOTReadingsModelService(ioTReadingsRepository);
        String deviceId = "deviceId";
        Date startDateTime = new Date();
        Date finishDateTime = new Date();
        ReadingRequest readingRequest = new ReadingRequest(deviceId, null, startDateTime, finishDateTime, ReadingRequest.Type.AVERAGE);
        IOTReadingsModel model = mock(IOTReadingsModel.class);
        Double value = 123d;
        when(model.getValue()).thenReturn(value);
        List<IOTReadingsModel> modelsList = Collections.singletonList(model);
        when(ioTReadingsRepository.findByDeviceIdAndCreatedBetween(deviceId, startDateTime, finishDateTime))
                .thenReturn(modelsList);


        //act && assert
        assertEquals(value, service.getValue(readingRequest));
    }

    @Test
    void getValue_withByGroupIdReadingRequest_shouldReturnCorrectValue() {
        //arrange
        IOTReadingsModelService service = new IOTReadingsModelService(ioTReadingsRepository);
        String groupId = "groupId";
        Date startDateTime = new Date();
        Date finishDateTime = new Date();
        ReadingRequest readingRequest = new ReadingRequest(null, groupId, startDateTime, finishDateTime, ReadingRequest.Type.AVERAGE);
        IOTReadingsModel model = mock(IOTReadingsModel.class);
        Double value = 123d;
        when(model.getValue()).thenReturn(value);
        List<IOTReadingsModel> modelsList = Collections.singletonList(model);
        when(ioTReadingsRepository.findByGroupIdAndCreatedBetween(groupId, startDateTime, finishDateTime))
                .thenReturn(modelsList);


        //act && assert
        assertEquals(value, service.getValue(readingRequest));
    }
}
package com.relay42.browser.service;

import com.relay42.browser.web.ReadingRequestValidator;
import com.relay42.generated.ReadingRequest;
import com.relay42.generated.ReadingResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadingsServiceTest {

    @Mock
    private ReadingRequestValidator validator;
    @Mock
    private IOTReadingsModelService iOTReadingsModelService;

    private ReadingsService readingsService;

    @BeforeEach
    void setUp() {
        readingsService = new ReadingsService(validator, iOTReadingsModelService);
    }

    @Test
    void getReadings_withValidRequest_shouldReturnResponse() throws ReadingRequestException {
        //arrange
        ReadingRequest readingRequest = new ReadingRequest();
        readingRequest.setType(ReadingRequest.Type.AVERAGE);
        when(validator.isValid(readingRequest)).thenReturn(true);
        Double value = 1d;
        when(iOTReadingsModelService.getValue(readingRequest)).thenReturn(value);

        //act
        ReadingResponse readings = readingsService.getReadings(readingRequest);

        //assert
        assertEquals(value, readings.getValue());
    }

    @Test
    void getReadings_withInvalidRequest_shouldThrowException() {
        //arrange
        ReadingRequest readingRequest = new ReadingRequest();
        when(validator.isValid(readingRequest)).thenReturn(false);

        //act && assert
        Assertions.assertThrows(ReadingRequestException.class, () -> {
            readingsService.getReadings(readingRequest);
        });
    }
}
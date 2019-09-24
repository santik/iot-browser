package com.relay42.browser.web;

import com.relay42.generated.ReadingRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReadingRequestValidatorTest {

    private ReadingRequestValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ReadingRequestValidator();
    }

    @Test
    void isValid_withBothEmptyIds_shouldReturnFalse() {
        //arrange
        ReadingRequest readingRequest = new ReadingRequest();

        //act && assert
        assertFalse(validator.isValid(readingRequest));
    }

    @Test
    void isValid_withStartMoreThanFinish_shouldReturnFalse() {
        //arrange
        ReadingRequest readingRequest = new ReadingRequest();
        readingRequest.setStartDateTime(new Date());
        readingRequest.setFinishDateTime(new Date());

        //act && assert
        assertFalse(validator.isValid(readingRequest));
    }

    @Test
    void isValid_withValidRequest_shouldReturnTrue() {
        //arrange
        ReadingRequest readingRequest = new ReadingRequest();
        readingRequest.setGroupId("groupId");
        readingRequest.setDeviceId("deviceId");
        readingRequest.setStartDateTime(Date.from(ZonedDateTime.now().minusMinutes(1).toInstant()));
        readingRequest.setFinishDateTime(new Date());

        //act && assert
        assertTrue(validator.isValid(readingRequest));
    }
}

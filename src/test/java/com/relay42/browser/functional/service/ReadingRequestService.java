package com.relay42.browser.functional.service;

import com.relay42.generated.ReadingRequest;

import java.time.ZonedDateTime;
import java.util.Date;

public class ReadingRequestService {

    public static ReadingRequest getReadingRequestForDeviceId(String deviceId) {
        ReadingRequest readingsRequest = new ReadingRequest();
        readingsRequest.setDeviceId(deviceId);
        readingsRequest.setStartDateTime(Date.from(ZonedDateTime.now().minusMinutes(10).toInstant()));
        readingsRequest.setFinishDateTime(Date.from(ZonedDateTime.now().toInstant()));
        return readingsRequest;
    }

    public static ReadingRequest getReadingRequestForGroupId(String groupId) {
        ReadingRequest readingsRequest = new ReadingRequest();
        readingsRequest.setGroupId(groupId);
        readingsRequest.setStartDateTime(Date.from(ZonedDateTime.now().minusMinutes(10).toInstant()));
        readingsRequest.setFinishDateTime(Date.from(ZonedDateTime.now().toInstant()));
        return readingsRequest;
    }
}

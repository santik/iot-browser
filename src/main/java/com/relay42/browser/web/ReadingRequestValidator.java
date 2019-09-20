package com.relay42.browser.web;

import com.relay42.generated.ReadingRequest;
import org.springframework.stereotype.Component;

@Component
public class ReadingRequestValidator {

    public boolean isValid(ReadingRequest readingRequest) {
        if (readingRequest.getDeviceId() == null && readingRequest.getGroupId() == null) {
            return false;
        }

        if (readingRequest.getStartDateTime().isAfter(readingRequest.getFinishDateTime())) {
            return false;
        }

        return true;
    }
}

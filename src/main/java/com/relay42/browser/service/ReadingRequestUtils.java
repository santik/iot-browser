package com.relay42.browser.service;

import com.relay42.generated.ReadingRequest;
import org.springframework.stereotype.Service;

@Service
public class ReadingRequestUtils {

    public boolean isMinRequest(ReadingRequest readingRequest) {
        return readingRequest.getType().equals(ReadingRequest.Type.MIN);
    }

    public boolean isMaxRequest(ReadingRequest readingRequest) {
        return readingRequest.getType().equals(ReadingRequest.Type.MAX);
    }

    public boolean isAverageRequest(ReadingRequest readingRequest) {
        return readingRequest.getType().equals(ReadingRequest.Type.AVERAGE);
    }

    public boolean isRequestByGroup(ReadingRequest readingRequest) {
        return readingRequest.getGroupId() != null;
    }

    public boolean isRequestByDevice(ReadingRequest readingRequest) {
        return readingRequest.getDeviceId() != null;
    }
}

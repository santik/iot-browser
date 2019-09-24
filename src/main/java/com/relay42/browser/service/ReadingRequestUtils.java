package com.relay42.browser.service;

import com.relay42.generated.ReadingRequest;

public class ReadingRequestUtils {

    private ReadingRequestUtils() {
    }

    public static boolean isMinRequest(ReadingRequest readingRequest) {
        return readingRequest.getType().equals(ReadingRequest.Type.MIN);
    }

    public static boolean isMaxRequest(ReadingRequest readingRequest) {
        return readingRequest.getType().equals(ReadingRequest.Type.MAX);
    }

    public static boolean isAverageRequest(ReadingRequest readingRequest) {
        return readingRequest.getType().equals(ReadingRequest.Type.AVERAGE);
    }

    public static boolean isRequestByGroup(ReadingRequest readingRequest) {
        return readingRequest.getGroupId() != null;
    }

    public static boolean isRequestByDevice(ReadingRequest readingRequest) {
        return readingRequest.getDeviceId() != null;
    }
}

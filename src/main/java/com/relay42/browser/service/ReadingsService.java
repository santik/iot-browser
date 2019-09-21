package com.relay42.browser.service;

import com.relay42.browser.web.ReadingRequestValidator;
import com.relay42.generated.ReadingRequest;
import com.relay42.generated.ReadingResponse;
import org.springframework.stereotype.Service;

@Service
public class ReadingsService {

    private ReadingRequestValidator validator;
    private IOTReadingsModelService iOTReadingsModelService;

    public ReadingsService(ReadingRequestValidator validator, IOTReadingsModelService iotReadingsModelService) {
        this.validator = validator;
        this.iOTReadingsModelService = iotReadingsModelService;
    }

    public ReadingResponse getReadings(ReadingRequest readingRequest) throws ReadingRequestException {

        if (!validator.isValid(readingRequest)) {
            throw new ReadingRequestException();
        }

        Double value = iOTReadingsModelService.getValue(readingRequest);

        return createReadingResponse(readingRequest, value);

    }

    private ReadingResponse createReadingResponse(ReadingRequest readingRequest, Double value) {
        ReadingResponse readingResponse = new ReadingResponse();
        readingResponse.setDeviceId(readingRequest.getDeviceId());
        readingResponse.setGroupId(readingRequest.getGroupId());
        readingResponse.setFinishDateTime(readingRequest.getFinishDateTime());
        readingResponse.setStartDateTime(readingRequest.getStartDateTime());
        readingResponse.setType(ReadingResponse.Type.fromValue(readingRequest.getType().value()));
        readingResponse.setValue(value);
        return readingResponse;
    }
}

package com.relay42.browser.service;

import com.relay42.browser.database.model.OutsideTemperatureModel;
import com.relay42.browser.web.ReadingRequestValidator;
import com.relay42.generated.ReadingRequest;
import com.relay42.generated.ReadingResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.DoubleStream;

@Service
public class ReadingsService {

    private ReadingRequestValidator validator;
    private OutsideTemperatureModelService outsideTemperatureModelService;

    public ReadingsService(ReadingRequestValidator validator, OutsideTemperatureModelService outsideTemperatureModelService) {
        this.validator = validator;
        this.outsideTemperatureModelService = outsideTemperatureModelService;
    }

    public ReadingResponse getReadings(ReadingRequest readingRequest) throws ReadingRequestException {

        if (!validator.isValid(readingRequest)) {
            throw new ReadingRequestException();
        }

        List<OutsideTemperatureModel> outsideTemperatureModels = outsideTemperatureModelService.getOutsideTemperatureModels(readingRequest);


        if (outsideTemperatureModels.size() == 0) {
            return createReadingResponse(readingRequest, -1);
        }

        Double value = null;

        if (readingRequest.getType().equals(ReadingRequest.Type.AVERAGE)) {
            value = getAverageValue(outsideTemperatureModels);
        }

        if (readingRequest.getType().equals(ReadingRequest.Type.MAX)) {
            value = getMaxValue(outsideTemperatureModels);
        }

        if (readingRequest.getType().equals(ReadingRequest.Type.MIN)) {
            value = getMinValue(outsideTemperatureModels);
        }

        if (value != null) {
            return createReadingResponse(readingRequest, value);
        }

        throw new ReadingRequestException();
    }

    private double getAverageValue(List<OutsideTemperatureModel> outsideTemperatureModels) {
        return getValues(outsideTemperatureModels).average().getAsDouble();
    }

    private double getMinValue(List<OutsideTemperatureModel> outsideTemperatureModels) {
        return getValues(outsideTemperatureModels).min().getAsDouble();
    }

    private double getMaxValue(List<OutsideTemperatureModel> outsideTemperatureModels) {
        return getValues(outsideTemperatureModels).max().getAsDouble();
    }

    private DoubleStream getValues(List<OutsideTemperatureModel> outsideTemperatureModels) {
        return outsideTemperatureModels.stream().mapToDouble(model -> model.getValue());
    }

    private ReadingResponse createReadingResponse(ReadingRequest readingRequest, double value) {
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

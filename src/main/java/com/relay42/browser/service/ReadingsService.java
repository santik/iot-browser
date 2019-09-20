package com.relay42.browser.service;

import com.relay42.browser.database.model.OutsideTemperatureModel;
import com.relay42.browser.database.repository.OutsideTemperatureRepository;
import com.relay42.browser.web.ReadingRequestValidator;
import com.relay42.generated.ReadingRequest;
import com.relay42.generated.ReadingResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class ReadingsService {

    private OutsideTemperatureRepository outsideTemperatureRepository;
    private ReadingRequestValidator validator;

    public ReadingsService(OutsideTemperatureRepository outsideTemperatureRepository, ReadingRequestValidator validator) {
        this.outsideTemperatureRepository = outsideTemperatureRepository;
        this.validator = validator;
    }

    public ReadingResponse getReadings(ReadingRequest readingRequest) {

        if (!validator.isValid(readingRequest)) {
            throw new RuntimeException("request is not valid");
        }

        List<OutsideTemperatureModel> outsideTemperatureModels = new ArrayList<>();

        if (readingRequest.getDeviceId() != null) {
            outsideTemperatureModels = outsideTemperatureRepository.findByDeviceIdAndCreatedBetween(
                    readingRequest.getDeviceId(),
                    readingRequest.getStartDateTime(), readingRequest.getFinishDateTime()
            );
        }

        if (readingRequest.getGroupId() != null) {
            outsideTemperatureModels = outsideTemperatureRepository.findByGroupIdAndCreatedBetween(
                    readingRequest.getGroupId(),
                    readingRequest.getStartDateTime(), readingRequest.getFinishDateTime()
            );
        }

        if (outsideTemperatureModels.size() == 0) {
            return createReadingResponce(readingRequest, -1);
        }

        if (readingRequest.getType().equals(ReadingRequest.Type.AVERAGE)) {
            return createReadingResponce(readingRequest, outsideTemperatureModels.stream().mapToDouble(model -> model.getValue())
                    .average().getAsDouble());
        }

        if (readingRequest.getType().equals(ReadingRequest.Type.MAX)) {
            return createReadingResponce(readingRequest, outsideTemperatureModels.stream().mapToDouble(model -> model.getValue())
                    .max().getAsDouble());
        }

        if (readingRequest.getType().equals(ReadingRequest.Type.MIN)) {
            return createReadingResponce(readingRequest, outsideTemperatureModels.stream().mapToDouble(model -> model.getValue())
                    .min().getAsDouble());
        }

        throw new RuntimeException();
    }

    private ReadingResponse createReadingResponce(ReadingRequest readingRequest, double value) {
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

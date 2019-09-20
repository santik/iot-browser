package com.relay42.browser.service;

import com.relay42.browser.database.model.OutsideTemperatureModel;
import com.relay42.browser.database.repository.OutsideTemperatureRepository;
import com.relay42.generated.ReadingRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

@Service
public class OutsideTemperatureModelService {

    private OutsideTemperatureRepository outsideTemperatureRepository;

    public OutsideTemperatureModelService(OutsideTemperatureRepository outsideTemperatureRepository) {
        this.outsideTemperatureRepository = outsideTemperatureRepository;
    }

    public List<OutsideTemperatureModel> getOutsideTemperatureModels(ReadingRequest readingRequest) {
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
        return outsideTemperatureModels;
    }




}

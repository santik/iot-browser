package com.relay42.browser.service;

import com.relay42.browser.database.model.IOTReadingsModel;
import com.relay42.browser.database.repository.OutsideTemperatureRepository;
import com.relay42.generated.ReadingRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

@Service
public class IOTReadingsModelService {

    private OutsideTemperatureRepository outsideTemperatureRepository;
    private ReadingRequestUtils readingRequestUtils;

    public IOTReadingsModelService(OutsideTemperatureRepository outsideTemperatureRepository, ReadingRequestUtils readingRequestUtils) {
        this.outsideTemperatureRepository = outsideTemperatureRepository;
        this.readingRequestUtils = readingRequestUtils;
    }

    public Double getValue(ReadingRequest readingRequest) {
        List<IOTReadingsModel> outsideTemperatureModels = getOutsideTemperatureModels(readingRequest);

        if (outsideTemperatureModels.size() == 0) {
            return null;
        }

        Double value = null;

        if (readingRequestUtils.isAverageRequest(readingRequest)) {
            value = getAverageValue(outsideTemperatureModels);
        }

        if (readingRequestUtils.isMaxRequest(readingRequest)) {
            value = getMaxValue(outsideTemperatureModels);
        }

        if (readingRequestUtils.isMinRequest(readingRequest)) {
            value = getMinValue(outsideTemperatureModels);
        }

        return value;
    }

    private List<IOTReadingsModel> getOutsideTemperatureModels(ReadingRequest readingRequest) {
        List<IOTReadingsModel> outsideTemperatureModels = new ArrayList<>();

        if (readingRequestUtils.isRequestByDevice(readingRequest)) {
            outsideTemperatureModels = outsideTemperatureRepository.findByDeviceIdAndCreatedBetween(
                    readingRequest.getDeviceId(),
                    readingRequest.getStartDateTime(), readingRequest.getFinishDateTime()
            );
        }

        if (readingRequestUtils.isRequestByGroup(readingRequest)) {
            outsideTemperatureModels = outsideTemperatureRepository.findByGroupIdAndCreatedBetween(
                    readingRequest.getGroupId(),
                    readingRequest.getStartDateTime(), readingRequest.getFinishDateTime()
            );
        }
        return outsideTemperatureModels;
    }

    private double getAverageValue(List<IOTReadingsModel> outsideTemperatureModels) {
        return getValues(outsideTemperatureModels).average().getAsDouble();
    }

    private double getMinValue(List<IOTReadingsModel> outsideTemperatureModels) {
        return getValues(outsideTemperatureModels).min().getAsDouble();
    }

    private double getMaxValue(List<IOTReadingsModel> outsideTemperatureModels) {
        return getValues(outsideTemperatureModels).max().getAsDouble();
    }

    private DoubleStream getValues(List<IOTReadingsModel> outsideTemperatureModels) {
        return outsideTemperatureModels.stream().mapToDouble(model -> model.getValue());
    }
}

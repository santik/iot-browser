package com.relay42.browser.service;

import com.relay42.browser.database.model.IOTReadingsModel;
import com.relay42.browser.database.repository.IoTReadingsRepository;
import com.relay42.generated.ReadingRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

@Service
public class IOTReadingsModelService {

    private IoTReadingsRepository ioTReadingsRepository;

    public IOTReadingsModelService(IoTReadingsRepository ioTReadingsRepository) {
        this.ioTReadingsRepository = ioTReadingsRepository;
    }

    public Double getValue(ReadingRequest readingRequest) {
        List<IOTReadingsModel> outsideTemperatureModels = getIoTReadingModels(readingRequest);

        if (outsideTemperatureModels.size() == 0) {
            return null;
        }

        Double value = null;

        if (ReadingRequestUtils.isAverageRequest(readingRequest)) {
            value = getAverageValue(outsideTemperatureModels);
        }

        if (ReadingRequestUtils.isMaxRequest(readingRequest)) {
            value = getMaxValue(outsideTemperatureModels);
        }

        if (ReadingRequestUtils.isMinRequest(readingRequest)) {
            value = getMinValue(outsideTemperatureModels);
        }

        return value;
    }

    private List<IOTReadingsModel> getIoTReadingModels(ReadingRequest readingRequest) {
        List<IOTReadingsModel> outsideTemperatureModels = new ArrayList<>();

        if (ReadingRequestUtils.isRequestByDevice(readingRequest)) {
            outsideTemperatureModels = ioTReadingsRepository.findByDeviceIdAndCreatedBetween(
                    readingRequest.getDeviceId(),
                    readingRequest.getStartDateTime(), readingRequest.getFinishDateTime()
            );
        }

        if (ReadingRequestUtils.isRequestByGroup(readingRequest)) {
            outsideTemperatureModels = ioTReadingsRepository.findByGroupIdAndCreatedBetween(
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

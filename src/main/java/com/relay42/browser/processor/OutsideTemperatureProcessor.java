package com.relay42.browser.processor;

import com.relay42.browser.database.model.OutsideTemperatureModel;
import com.relay42.browser.database.repository.OutsideTemperatureRepository;
import com.relay42.generated.OutsideTemperature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class OutsideTemperatureProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutsideTemperatureProcessor.class);

    private OutsideTemperatureRepository outsideTemperatureRepository;

    public OutsideTemperatureProcessor(OutsideTemperatureRepository outsideTemperatureRepository) {
        this.outsideTemperatureRepository = outsideTemperatureRepository;
    }

    @Retryable
    public void process(OutsideTemperature outsideTemperature) {
        LOGGER.info("Received {}", outsideTemperature);
        outsideTemperatureRepository.save(OutsideTemperatureModel.createFromKafkaMessage(outsideTemperature));
    }
}

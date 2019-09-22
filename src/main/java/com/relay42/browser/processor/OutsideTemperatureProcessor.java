package com.relay42.browser.processor;

import com.relay42.browser.database.model.IOTReadingsModel;
import com.relay42.browser.database.repository.IoTReadingsRepository;
import com.relay42.generated.OutsideTemperature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class OutsideTemperatureProcessor implements KafkaMessageProcessor<OutsideTemperature> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutsideTemperatureProcessor.class);

    private IoTReadingsRepository ioTReadingsRepository;

    public OutsideTemperatureProcessor(IoTReadingsRepository ioTReadingsRepository) {
        this.ioTReadingsRepository = ioTReadingsRepository;
    }

    @Override
    @Retryable
    public void process(OutsideTemperature outsideTemperature) {
        LOGGER.info("Received {}", outsideTemperature);
        ioTReadingsRepository.save(IOTReadingsModel.createFromKafkaMessage(outsideTemperature));
    }
}

package com.relay42.browser.processor;

import com.relay42.browser.database.model.IOTReadingsModel;
import com.relay42.browser.database.repository.IoTReadingsRepository;
import com.relay42.generated.OutsideHumidity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class OutsideHumidityProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutsideHumidityProcessor.class);

    private IoTReadingsRepository ioTReadingsRepository;

    public OutsideHumidityProcessor(IoTReadingsRepository ioTReadingsRepository) {
        this.ioTReadingsRepository = ioTReadingsRepository;
    }

    @Retryable
    public void process(OutsideHumidity outsideHumidity) {
        LOGGER.info("Received {}", outsideHumidity);
        ioTReadingsRepository.save(IOTReadingsModel.createFromKafkaMessage(outsideHumidity));
    }
}

package com.relay42.browser.processor;

import com.relay42.browser.database.model.IOTReadingsModel;
import com.relay42.browser.database.repository.IoTReadingsRepository;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class WindSpeedProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(WindSpeedProcessor.class);

    private IoTReadingsRepository ioTReadingsRepository;

    public WindSpeedProcessor(IoTReadingsRepository ioTReadingsRepository) {
        this.ioTReadingsRepository = ioTReadingsRepository;
    }

    @Retryable
    public void process(WindSpeed windSpeed) {
        LOGGER.info("Received {}", windSpeed);
        ioTReadingsRepository.save(IOTReadingsModel.createFromKafkaMessage(windSpeed));
    }
}

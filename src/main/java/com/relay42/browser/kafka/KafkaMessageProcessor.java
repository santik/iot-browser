package com.relay42.browser.kafka;

import com.relay42.browser.database.IOTReadingsModel;
import com.relay42.browser.database.IoTReadingsRepository;
import com.relay42.generated.KafkaMessage;
import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProcessor<T extends KafkaMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageProcessor.class);

    private IoTReadingsRepository ioTReadingsRepository;

    public KafkaMessageProcessor(IoTReadingsRepository ioTReadingsRepository) {
        this.ioTReadingsRepository = ioTReadingsRepository;
    }

    public void process(T t) {
        LOGGER.info("Received {}", t);
        ioTReadingsRepository.save(getIotReadingsModel(t));
    }

    private IOTReadingsModel getIotReadingsModel(T t) {
        if (t instanceof WindSpeed) {
            return IOTReadingsModel.createFromKafkaMessage((WindSpeed) t);
        }

        if (t instanceof OutsideTemperature) {
            return IOTReadingsModel.createFromKafkaMessage((OutsideTemperature) t);
        }

        if (t instanceof OutsideHumidity) {
            return IOTReadingsModel.createFromKafkaMessage((OutsideHumidity) t);
        }

        throw new RuntimeException("Unknown message type");
    }
}

package com.relay42.browser.functional.steps;

import com.relay42.browser.functional.kafka.KafkaMessagesTestPublisher;
import com.relay42.browser.functional.service.MessagesGenerator;
import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.jbehave.core.annotations.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class SendsRandomKafkaMessagesSteps extends BaseSteps {
    @Autowired
    private KafkaMessagesTestPublisher kafkaMessagesTestPublisher;

    private String tempId = "temId";
    private String humId = "humId";
    private String windId = "windId";

    @Given("send messages every second")
    public void sendMessages() throws InterruptedException {
        while (true) {
            OutsideTemperature outsideTemperature = MessagesGenerator.generateOutsideTemperatureMessage();
            outsideTemperature.setDeviceId(tempId);
            OutsideHumidity outsideHumidity = MessagesGenerator.generateOutsideHumidityMessage();
            outsideHumidity.setDeviceId(humId);
            WindSpeed windSpeed = MessagesGenerator.generateWindSpeedMessage();
            windSpeed.setDeviceId(windId);
            kafkaMessagesTestPublisher.publishOutsideTemperature(outsideTemperature);
            kafkaMessagesTestPublisher.publishOutsideHumidity(outsideHumidity);
            kafkaMessagesTestPublisher.publishWindSpeed(windSpeed);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

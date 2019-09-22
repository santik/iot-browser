package com.relay42.browser.functional.steps;

import com.relay42.browser.functional.AsyncEventHelper;
import com.relay42.browser.functional.service.MessagesGenerator;
import com.relay42.browser.functional.service.ReadingsRequestClient;
import com.relay42.browser.functional.kafka.KafkaMessagesTestPublisher;
import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.ReadingRequest;
import com.relay42.generated.ReadingResponse;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

public class SendsHttpRequestAndGetInformationSteps extends BaseSteps {

    private static final int NUMBER_OF_MESSAGES = 100;

    @Autowired
    private KafkaMessagesTestPublisher kafkaMessagesTestPublisher;

    @Autowired
    private ReadingsRequestClient readingsRequestClient;

    private List<OutsideTemperature> outsideTemperatures = new ArrayList<>();
    private List<OutsideHumidity> outsideHumidities = new ArrayList<>();

    @Given("multiple OutsideTemperature messages")
    public void generateOutsideTemperatureMessages() {
        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            outsideTemperatures.add(MessagesGenerator.generateOutsideTemperatureMessage());
        }

        for (int i = 0; i < NUMBER_OF_MESSAGES/2; i++) {
            outsideTemperatures.get(i).setDeviceId(outsideTemperatures.get(0).getDeviceId());
        }

        for (int i = NUMBER_OF_MESSAGES/2; i < NUMBER_OF_MESSAGES; i++) {
            outsideTemperatures.get(i).setGroupId(outsideTemperatures.get(0).getGroupId());
        }
    }

    @When("OutsideTemperature messages are published")
    public void publishOutsideTemperatureMessages() {
        outsideTemperatures.forEach(outsideTemperature -> kafkaMessagesTestPublisher.publishOutsideTemperature(outsideTemperature));
    }

    @Then("request for reading from OutsideTemperature returns correct value")
    public void verifyReadingRequestForOutsideTemperatureSucceded() {
        AsyncEventHelper.await(this::requestReturnCorrectOutsideTemperatureData, "Data saved");
    }

    private boolean requestReturnCorrectOutsideTemperatureData() throws IOException, InterruptedException {
        String deviceId = outsideTemperatures.get(0).getDeviceId();
        double expectedAverageByDeviceId = getValuesFromOutsideTemperatureByDeviceId(deviceId, outsideTemperatures).average().getAsDouble();
        double expectedMaxByDeviceId = getValuesFromOutsideTemperatureByDeviceId(deviceId, outsideTemperatures).max().getAsDouble();
        double expectedMinByDeviceId = getValuesFromOutsideTemperatureByDeviceId(deviceId, outsideTemperatures).min().getAsDouble();

        String groupId = outsideTemperatures.get(outsideTemperatures.size() - 1).getGroupId();
        double expectedAverageByGroupId = getValuesFromOutsideTemperatureByGroupId(groupId, outsideTemperatures).average().getAsDouble();
        double expectedMaxByGroupId = getValuesFromOutsideTemperatureByGroupId(groupId, outsideTemperatures).max().getAsDouble();
        double expectedMinByGroupId = getValuesFromOutsideTemperatureByGroupId(groupId, outsideTemperatures).min().getAsDouble();

        return averageByDeviceIdIsCorrect(deviceId, expectedAverageByDeviceId)
                && maxByDeviceIdIsCorrect(deviceId, expectedMaxByDeviceId)
                && minByDeviceIdIsCorrect(deviceId, expectedMinByDeviceId)
                && averageByGroupIdIsCorrect(groupId, expectedAverageByGroupId)
                && maxByGroupIdIsCorrect(groupId, expectedMaxByGroupId)
                && minByGroupIdIsCorrect(groupId, expectedMinByGroupId);
    }


    @Given("multiple OutsideHumidity messages")
    public void generateOutsideHumidityMessages() {
        for (int i = 0; i < NUMBER_OF_MESSAGES; i++) {
            outsideHumidities.add(MessagesGenerator.generateOutsideHumidityMessage());
        }

        for (int i = 0; i < NUMBER_OF_MESSAGES/2; i++) {
            outsideHumidities.get(i).setDeviceId(outsideHumidities.get(0).getDeviceId());
        }

        for (int i = NUMBER_OF_MESSAGES/2; i < NUMBER_OF_MESSAGES; i++) {
            outsideHumidities.get(i).setGroupId(outsideHumidities.get(0).getGroupId());
        }
    }

    @When("OutsideHumidity messages are published")
    public void publishOutsideHumidityMessages() {
        outsideHumidities.forEach(outsideHumidity -> kafkaMessagesTestPublisher.publishOutsideHumidity(outsideHumidity));
    }

    @Then("request for reading from OutsideHumidity returns correct value")
    public void verifyReadingRequestSucceded() {
        AsyncEventHelper.await(this::requestReturnCorrectOutsideHumidityData, "Data saved");
    }

    private boolean requestReturnCorrectOutsideHumidityData() throws IOException, InterruptedException {
        String deviceId = outsideHumidities.get(0).getDeviceId();
        double expectedAverageByDeviceId = getValuesFromOutsideHumiditiesByDeviceId(deviceId, outsideHumidities).average().getAsDouble();
        double expectedMaxByDeviceId = getValuesFromOutsideHumiditiesByDeviceId(deviceId, outsideHumidities).max().getAsDouble();
        double expectedMinByDeviceId = getValuesFromOutsideHumiditiesByDeviceId(deviceId, outsideHumidities).min().getAsDouble();

        String groupId = outsideHumidities.get(outsideHumidities.size() - 1).getGroupId();
        double expectedAverageByGroupId = getValuesFromOutsideHumiditiesByGroupId(groupId, outsideHumidities).average().getAsDouble();
        double expectedMaxByGroupId = getValuesFromOutsideHumiditiesByGroupId(groupId, outsideHumidities).max().getAsDouble();
        double expectedMinByGroupId = getValuesFromOutsideHumiditiesByGroupId(groupId, outsideHumidities).min().getAsDouble();

        return averageByDeviceIdIsCorrect(deviceId, expectedAverageByDeviceId)
                && maxByDeviceIdIsCorrect(deviceId, expectedMaxByDeviceId)
                && minByDeviceIdIsCorrect(deviceId, expectedMinByDeviceId)
                && averageByGroupIdIsCorrect(groupId, expectedAverageByGroupId)
                && maxByGroupIdIsCorrect(groupId, expectedMaxByGroupId)
                && minByGroupIdIsCorrect(groupId, expectedMinByGroupId);
    }
    

    private boolean averageByDeviceIdIsCorrect(String deviceId, double expectedAverage) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.AVERAGE);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedAverage == readingResponse.getValue();
    }


    private boolean maxByDeviceIdIsCorrect(String deviceId, double expectedMax) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.MAX);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedMax == readingResponse.getValue();
    }

    private boolean minByDeviceIdIsCorrect(String deviceId, double expectedMin) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.MIN);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedMin == readingResponse.getValue();
    }

    private boolean averageByGroupIdIsCorrect(String groupId, double expectedAverage ) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.AVERAGE);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedAverage == readingResponse.getValue();
    }

    private boolean maxByGroupIdIsCorrect(String groupId, double expectedMax) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.MAX);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedMax == readingResponse.getValue();
    }

    private boolean minByGroupIdIsCorrect(String groupId, double expectedMin) throws IOException, InterruptedException {
        ReadingRequest readingsRequest = getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.MIN);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedMin == readingResponse.getValue();
    }

    private DoubleStream getValuesFromOutsideTemperatureByDeviceId(String deviceId, List<OutsideTemperature> outsideHumidityMessagess) {
        return outsideHumidityMessagess.stream()
                .filter(outsideHumidity -> outsideHumidity.getDeviceId().equals(deviceId))
                .mapToDouble(model -> model.getValue());
    }
    
    private DoubleStream getValuesFromOutsideHumiditiesByGroupId(String groupId, List<OutsideHumidity> outsideHumidityMessages) {
        return outsideHumidityMessages.stream()
                .filter(outsideHumidity -> outsideHumidity.getGroupId().equals(groupId))
                .mapToDouble(model -> model.getValue());
    }

    private DoubleStream getValuesFromOutsideHumiditiesByDeviceId(String deviceId, List<OutsideHumidity> outsideHumidityMessagess) {
        return outsideHumidityMessagess.stream()
                .filter(outsideHumidity -> outsideHumidity.getDeviceId().equals(deviceId))
                .mapToDouble(model -> model.getValue());
    }

    private DoubleStream getValuesFromOutsideTemperatureByGroupId(String groupId, List<OutsideTemperature> outsideHumidityMessages) {
        return outsideHumidityMessages.stream()
                .filter(outsideHumidity -> outsideHumidity.getGroupId().equals(groupId))
                .mapToDouble(model -> model.getValue());
    }
    
    private ReadingRequest getReadingRequestForDeviceId(String deviceId) {
        ReadingRequest readingsRequest = new ReadingRequest();
        readingsRequest.setDeviceId(deviceId);
        readingsRequest.setStartDateTime(Date.from(ZonedDateTime.now().minusMinutes(10).toInstant()));
        readingsRequest.setFinishDateTime(Date.from(ZonedDateTime.now().toInstant()));
        return readingsRequest;
    }

    private ReadingRequest getReadingRequestForGroupId(String groupId) {
        ReadingRequest readingsRequest = new ReadingRequest();
        readingsRequest.setGroupId(groupId);
        readingsRequest.setStartDateTime(Date.from(ZonedDateTime.now().minusMinutes(10).toInstant()));
        readingsRequest.setFinishDateTime(Date.from(ZonedDateTime.now().toInstant()));
        return readingsRequest;
    }
}

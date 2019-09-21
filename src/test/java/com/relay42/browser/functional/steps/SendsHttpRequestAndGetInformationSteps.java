package com.relay42.browser.functional.steps;

import com.relay42.browser.functional.AsyncEventHelper;
import com.relay42.browser.functional.service.MessagesGenerator;
import com.relay42.browser.functional.service.ReadingsRequestClient;
import com.relay42.browser.functional.kafka.OutsideTemperatureTestPublisher;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.ReadingRequest;
import com.relay42.generated.ReadingResponse;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

public class SendsHttpRequestAndGetInformationSteps extends BaseSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendsHttpRequestAndGetInformationSteps.class);
    private static final int NUMBER_OF_MESSAGES = 100;

    @Autowired
    private OutsideTemperatureTestPublisher outsideTemperatureTestPublisher;

    @Autowired
    private ReadingsRequestClient readingsRequestClient;

    private List<OutsideTemperature> outsideTemperatures = new ArrayList<>();

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
        outsideTemperatures.forEach(outsideTemperature -> outsideTemperatureTestPublisher.publish(outsideTemperature));
    }

    @Then("request for reading returns correct value")
    public void verifyReadingRequestSucceded() {
        AsyncEventHelper.await(this::requestReturnCorrectData, "Data saved");
    }

    private boolean requestReturnCorrectData() throws IOException, InterruptedException {
        return averageByDeviceIdIsCorrect() 
                && maxByDeviceIdIsCorrect()
                && minByDeviceIdIsCorrect()
                && averageByGroupIdIsCorrect() 
                && maxByGroupIdIsCorrect() 
                && minByGroupIdIsCorrect();
    }

    private boolean averageByDeviceIdIsCorrect() throws IOException, InterruptedException {
        String deviceId = outsideTemperatures.get(0).getDeviceId();
        double expectedAverage = getValuesByDeviceId(deviceId, outsideTemperatures).average().getAsDouble();
        LOGGER.info("Expected average by device Id  {}", expectedAverage);

        ReadingRequest readingsRequest = getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.AVERAGE);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedAverage == readingResponse.getValue();
    }


    private boolean maxByDeviceIdIsCorrect() throws IOException, InterruptedException {
        String deviceId = outsideTemperatures.get(0).getDeviceId();
        double expectedMax = getValuesByDeviceId(deviceId, outsideTemperatures).max().getAsDouble();
        LOGGER.info("Expected max by device Id  {}", expectedMax);

        ReadingRequest readingsRequest = getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.MAX);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedMax == readingResponse.getValue();
    }

    private boolean minByDeviceIdIsCorrect() throws IOException, InterruptedException {
        String deviceId = outsideTemperatures.get(0).getDeviceId();
        List<OutsideTemperature> outsideTemperatures = this.outsideTemperatures;
        double expectedMin = getValuesByDeviceId(deviceId, outsideTemperatures)
                .min().getAsDouble();
        LOGGER.info("Expected min by device Id {}", expectedMin);

        ReadingRequest readingsRequest = getReadingRequestForDeviceId(deviceId);
        readingsRequest.setType(ReadingRequest.Type.MIN);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedMin == readingResponse.getValue();
    }

    


    private boolean averageByGroupIdIsCorrect() throws IOException, InterruptedException {
        String groupId = outsideTemperatures.get(outsideTemperatures.size() - 1).getGroupId();
        double expectedAverage = getValuesByGroupId(groupId, outsideTemperatures).average().getAsDouble();
        LOGGER.info("Expected average by group Id {}", expectedAverage);

        ReadingRequest readingsRequest = getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.AVERAGE);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedAverage == readingResponse.getValue();
    }

    private boolean maxByGroupIdIsCorrect() throws IOException, InterruptedException {
        String groupId = outsideTemperatures.get(outsideTemperatures.size() - 1).getGroupId();
        double expectedMax = getValuesByGroupId(groupId, outsideTemperatures).max().getAsDouble();
        LOGGER.info("Expected max by group Id {}", expectedMax);

        ReadingRequest readingsRequest = getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.MAX);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedMax == readingResponse.getValue();
    }

    private boolean minByGroupIdIsCorrect() throws IOException, InterruptedException {
        String groupId = outsideTemperatures.get(outsideTemperatures.size()-1).getGroupId();
        double expectedMin = getValuesByGroupId(groupId, outsideTemperatures).min().getAsDouble();
        LOGGER.info("Expected min by group Id {}", expectedMin);

        ReadingRequest readingsRequest = getReadingRequestForGroupId(groupId);
        readingsRequest.setType(ReadingRequest.Type.MIN);
        ReadingResponse readingResponse = readingsRequestClient.sendReadingRequest(readingsRequest);
        return expectedMin == readingResponse.getValue();
    }

    private DoubleStream getValuesByDeviceId(String deviceId, List<OutsideTemperature> outsideTemperatureMessagess) {
        return outsideTemperatureMessagess.stream()
                .filter(outsideTemperature -> outsideTemperature.getDeviceId().equals(deviceId))
                .mapToDouble(model -> model.getValue());
    }
    
    private DoubleStream getValuesByGroupId(String groupId, List<OutsideTemperature> outsideTemperatureMessages) {
        return outsideTemperatureMessages.stream()
                .filter(outsideTemperature -> outsideTemperature.getGroupId().equals(groupId))
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
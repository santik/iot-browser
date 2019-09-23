package com.relay42.browser.functional.steps;

import com.relay42.browser.functional.AsyncEventHelper;
import com.relay42.browser.functional.service.MessagesGenerator;
import com.relay42.browser.functional.service.ReadingsRequestClient;
import com.relay42.browser.functional.kafka.KafkaMessagesTestPublisher;
import com.relay42.generated.OutsideHumidity;
import com.relay42.generated.OutsideTemperature;
import com.relay42.generated.WindSpeed;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class SendsHttpRequestAndGetInformationSteps extends BaseSteps {

    @Autowired
    private KafkaMessagesTestPublisher kafkaMessagesTestPublisher;

    @Autowired
    private ReadingsRequestClient readingsRequestClient;

    private List<OutsideTemperature> outsideTemperatures = new ArrayList<>();
    private List<OutsideHumidity> outsideHumidities = new ArrayList<>();
    private List<WindSpeed> windSpeeds = new ArrayList<>();

    @Given("multiple OutsideTemperature messages")
    public void generateOutsideTemperatureMessages() {
        outsideTemperatures = MessagesGenerator.generateOutsideTemperatureList();
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
        outsideHumidities = MessagesGenerator.generateOutsideHumidityList();
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

    @Given("multiple WindSpeed messages")
    public void generateWindSpeedMessages() {
        windSpeeds = MessagesGenerator.generateWindSpeedList();
    }

    @When("WindSpeed messages are published")
    public void publishWindSpeedMessages() {
        windSpeeds.forEach(windSpeed -> kafkaMessagesTestPublisher.publishWindSpeed(windSpeed));
    }

    @Then("request for reading from WindSpeed returns correct value")
    public void verifyReadingRequestSuccededForWindSpeed() {
        AsyncEventHelper.await(this::requestReturnCorrectWindSpeedData, "Data saved");
    }

    private boolean requestReturnCorrectWindSpeedData() throws IOException, InterruptedException {
        String deviceId = windSpeeds.get(0).getDeviceId();
        double expectedAverageByDeviceId = getValuesFromWindSpeedByDeviceId(deviceId, windSpeeds).average().getAsDouble();
        double expectedMaxByDeviceId = getValuesFromWindSpeedByDeviceId(deviceId, windSpeeds).max().getAsDouble();
        double expectedMinByDeviceId = getValuesFromWindSpeedByDeviceId(deviceId, windSpeeds).min().getAsDouble();

        String groupId = windSpeeds.get(windSpeeds.size() - 1).getGroupId();
        double expectedAverageByGroupId = getValuesFromWindSpeedByGroupId(groupId, windSpeeds).average().getAsDouble();
        double expectedMaxByGroupId = getValuesFromWindSpeedByGroupId(groupId, windSpeeds).max().getAsDouble();
        double expectedMinByGroupId = getValuesFromWindSpeedByGroupId(groupId, windSpeeds).min().getAsDouble();

        return averageByDeviceIdIsCorrect(deviceId, expectedAverageByDeviceId)
                && maxByDeviceIdIsCorrect(deviceId, expectedMaxByDeviceId)
                && minByDeviceIdIsCorrect(deviceId, expectedMinByDeviceId)
                && averageByGroupIdIsCorrect(groupId, expectedAverageByGroupId)
                && maxByGroupIdIsCorrect(groupId, expectedMaxByGroupId)
                && minByGroupIdIsCorrect(groupId, expectedMinByGroupId);
    }


    private boolean averageByDeviceIdIsCorrect(String deviceId, double expectedAverage) throws IOException, InterruptedException {
        return expectedAverage == readingsRequestClient.getAverageByDeviceId(deviceId);
    }

    private boolean maxByDeviceIdIsCorrect(String deviceId, double expectedMax) throws IOException, InterruptedException {
        return expectedMax == readingsRequestClient.getMaxByDeviceId(deviceId);
    }

    private boolean minByDeviceIdIsCorrect(String deviceId, double expectedMin) throws IOException, InterruptedException {
        return expectedMin == readingsRequestClient.getMinByDeviceId(deviceId);
    }

    private boolean averageByGroupIdIsCorrect(String groupId, double expectedAverage ) throws IOException, InterruptedException {
        return expectedAverage == readingsRequestClient.getAverageByGroupId(groupId);
    }

    private boolean maxByGroupIdIsCorrect(String groupId, double expectedMax) throws IOException, InterruptedException {
        return expectedMax == readingsRequestClient.getMaxByGroupId(groupId);
    }

    private boolean minByGroupIdIsCorrect(String groupId, double expectedMin) throws IOException, InterruptedException {
        return expectedMin == readingsRequestClient.getMinByGroupId(groupId);
    }

    private DoubleStream getValuesFromOutsideTemperatureByDeviceId(String deviceId, List<OutsideTemperature> outsideTemperatureMessages) {
        return outsideTemperatureMessages.stream()
                .filter(outsideTemperature -> outsideTemperature.getDeviceId().equals(deviceId))
                .mapToDouble(model -> model.getValue());
    }
    
    private DoubleStream getValuesFromOutsideTemperatureByGroupId(String groupId, List<OutsideTemperature> outsideTemperaturesMessages) {
        return outsideTemperaturesMessages.stream()
                .filter(outsideTemperature -> outsideTemperature.getGroupId().equals(groupId))
                .mapToDouble(model -> model.getValue());
    }

    private DoubleStream getValuesFromOutsideHumiditiesByGroupId(String groupId, List<OutsideHumidity> outsideHumidityMessages) {
        return outsideHumidityMessages.stream()
                .filter(outsideHumidity -> outsideHumidity.getGroupId().equals(groupId))
                .mapToDouble(model -> model.getValue());
    }

    private DoubleStream getValuesFromOutsideHumiditiesByDeviceId(String deviceId, List<OutsideHumidity> outsideHumidityMessages) {
        return outsideHumidityMessages.stream()
                .filter(outsideHumidity -> outsideHumidity.getDeviceId().equals(deviceId))
                .mapToDouble(model -> model.getValue());
    }

    private DoubleStream getValuesFromWindSpeedByGroupId(String groupId, List<WindSpeed> windSpeedList) {
        return windSpeedList.stream()
                .filter(windSpeed -> windSpeed.getGroupId().equals(groupId))
                .mapToDouble(model -> model.getValue());
    }

    private DoubleStream getValuesFromWindSpeedByDeviceId(String deviceId, List<WindSpeed> windSpeedList) {
        return windSpeedList.stream()
                .filter(windSpeed -> windSpeed.getDeviceId().equals(deviceId))
                .mapToDouble(model -> model.getValue());
    }

}

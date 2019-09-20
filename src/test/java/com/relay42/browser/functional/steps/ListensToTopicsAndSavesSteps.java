package com.relay42.browser.functional.steps;

import com.relay42.browser.database.model.OutsideTemperatureModel;
import com.relay42.browser.database.repository.OutsideTemperatureRepository;
import com.relay42.browser.functional.AsyncEventHelper;
import com.relay42.browser.functional.EnvironmentContainer;
import com.relay42.browser.functional.MessagesGenerator;
import com.relay42.browser.functional.kafka.OutsideTemperatureTestPublisher;
import com.relay42.generated.OutsideTemperature;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ListensToTopicsAndSavesSteps extends BaseSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListensToTopicsAndSavesSteps.class);


    @Autowired
    private OutsideTemperatureTestPublisher outsideTemperatureTestPublisher;

    private OutsideTemperature outsideTemperature;
    
    @Given("OutsideTemperature message")
    public void createOutsideTemperatureMessage() {
        outsideTemperature = MessagesGenerator.generateOutsideTemperatureMessage();
    }

    @When("OutsideTemperature message is published")
    public void publishOutsideTemperatureMessage() {
        outsideTemperatureTestPublisher.publish(outsideTemperature);
    }

    @Then("data from OutsideTemperature message is saved")
    public void verifyOutsideTemperatureMessageSaved() {
        AsyncEventHelper.await(this::dataFromOutsideTemperatureMessageIsSaved, "Data saved");
    }

    private boolean dataFromOutsideTemperatureMessageIsSaved() {
        OutsideTemperatureRepository outsideTemperatureRepository = EnvironmentContainer.getEnvironment().getOutsideTemperatureRepository();

        OutsideTemperatureModel outsideTemperatureModelExpected = OutsideTemperatureModel.createFromKafkaMessage(outsideTemperature);
        LOGGER.info("Expected model {}", outsideTemperatureModelExpected);
        List<OutsideTemperatureModel> outsideTemperatureModels = outsideTemperatureRepository.findAll();
        LOGGER.info("Models {}", outsideTemperatureModels);
        return containsExpected(outsideTemperatureModels, outsideTemperatureModelExpected);
    }

    private boolean containsExpected(List<OutsideTemperatureModel> outsideTemperatureModels, OutsideTemperatureModel outsideTemperatureModelExpected) {
        return outsideTemperatureModels.stream().anyMatch(outsideTemperatureModel -> outsideTemperatureModel.getDeviceId().equals(outsideTemperatureModelExpected.getDeviceId())
        && outsideTemperatureModel.getGroupId().equals(outsideTemperatureModelExpected.getGroupId())
        && outsideTemperatureModel.getValue() == outsideTemperatureModelExpected.getValue());
    }
}

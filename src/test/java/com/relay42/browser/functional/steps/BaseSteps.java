package com.relay42.browser.functional.steps;

import com.relay42.browser.functional.TestingConfiguration;
import org.springframework.cloud.stream.config.BinderFactoryConfiguration;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@ContextConfiguration(classes = {
        BinderFactoryConfiguration.class,
        BindingServiceConfiguration.class,
        TestingConfiguration.class,
})
@TestPropertySource(locations = {"classpath:application-functional.properties"})
public abstract class BaseSteps {
}

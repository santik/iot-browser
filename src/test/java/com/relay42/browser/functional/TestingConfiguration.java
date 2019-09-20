package com.relay42.browser.functional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;

@Configuration
@ComponentScan(basePackageClasses = TestingConfiguration.class)
@IntegrationComponentScan(basePackageClasses = TestingConfiguration.class)
public class TestingConfiguration {
}

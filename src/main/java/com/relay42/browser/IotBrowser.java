package com.relay42.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@IntegrationComponentScan
public class IotBrowser {
    public static void main(String[] args) {
        SpringApplication.run(IotBrowser.class, args);
    }
}

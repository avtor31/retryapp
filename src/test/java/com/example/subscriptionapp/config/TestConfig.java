// src/test/java/com/example/subscriptionapp/config/TestConfig.java

package com.example.subscriptionapp.config;

import com.example.subscriptionapp.repository.FakeSubscriptionRepository;
import com.example.subscriptionapp.repository.FlakySubscriptionRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public FakeSubscriptionRepository flakySubscriptionRepository() {
        return new FlakySubscriptionRepository(2);
    }
}
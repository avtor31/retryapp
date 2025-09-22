// src/test/java/com/example/subscriptionapp/config/TestConfig.java

package com.avtor31.retryapp.config;

import com.avtor31.retryapp.repository.FakeSubscriptionRepository;
import com.avtor31.retryapp.repository.FlakySubscriptionRepository;
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
// src/main/java/com/example/subscriptionapp/config/RetryConfig.java

package com.example.subscriptionapp.config;

import org.postgresql.util.PSQLException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RetryConfig {

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Стратегия повторов: максимум 3 попытки
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(DataAccessException.class, true);
        retryableExceptions.put(PSQLException.class, true);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3, retryableExceptions);


        retryTemplate.setRetryPolicy(retryPolicy);

        // Стратегия задержки: экспоненциальная — 500мс, 1000мс, 2000мс
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500L);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(10000L); // ограничим максимумом

        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
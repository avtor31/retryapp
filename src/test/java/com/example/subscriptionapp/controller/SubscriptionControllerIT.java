// src/test/java/com/example/subscriptionapp/controller/SubscriptionControllerIT.java

package com.example.subscriptionapp.controller;

import com.example.subscriptionapp.dto.SubscriptionResponse;
import com.example.subscriptionapp.repository.FakeSubscriptionRepository;
import com.example.subscriptionapp.repository.FlakySubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class SubscriptionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRetryAndSucceedOnThirdAttempt(CapturedOutput output) throws Exception {
        // Arrange & Act
        MvcResult result = mockMvc.perform(
                        get("/api/v3/subscription/subscriptions")
                                .param("subscriptionType", "premium")
                                .param("subscriptionStatus", "active")
                )
                .andExpect(status().isOk())
                .andReturn();

        // Assert: проверяем ответ
        String content = result.getResponse().getContentAsString();
        SubscriptionResponse response = SubscriptionResponse.fromJson(content);

        assertThat(response.subscriptions()).hasSize(3);
        assertThat(response.subscriptions()).containsExactly("sub-001", "sub-002", "sub-003");

        // Assert: проверяем логи через CapturedOutput
        String logOutput = output.getOut();

        assertThat(logOutput).contains("Attempt #1 for method: getSubscriptions");
        assertThat(logOutput).contains("Attempt #2 for method: getSubscriptions");
        assertThat(logOutput).contains("Attempt #3 for method: getSubscriptions");
        assertThat(logOutput).doesNotContain("Attempt #4");

        assertThat(logOutput).contains("DataAccessException caught, retrying... attempt: 1");
        assertThat(logOutput).contains("DataAccessException caught, retrying... attempt: 2");
        assertThat(logOutput).doesNotContain("attempt: 3");
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public FakeSubscriptionRepository flakySubscriptionRepository() {
            return new FlakySubscriptionRepository(2);
        }
    }
}
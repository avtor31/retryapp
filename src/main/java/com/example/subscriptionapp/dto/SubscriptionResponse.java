// src/main/java/com/example/subscriptionapp/dto/SubscriptionResponse.java

package com.example.subscriptionapp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SubscriptionResponse(@JsonProperty("subscriptions") List<String> subscriptions) {

    @JsonCreator
    public SubscriptionResponse {
        // Конструктор record остаётся неизменным
    }

    // Статический метод для десериализации из строки (для тестов)
    public static SubscriptionResponse fromJson(String json) throws Exception {
        var mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        return mapper.readValue(json, SubscriptionResponse.class);
    }
}
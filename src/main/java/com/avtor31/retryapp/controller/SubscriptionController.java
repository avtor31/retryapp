// src/main/java/com/example/subscriptionapp/controller/SubscriptionController.java

package com.avtor31.retryapp.controller;

import com.avtor31.retryapp.dto.SubscriptionResponse;
import com.avtor31.retryapp.service.SubscriptionListService;
import org.postgresql.util.PSQLException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3/subscription")
public class SubscriptionController {

    private final SubscriptionListService subscriptionListService;

    public SubscriptionController(SubscriptionListService subscriptionListService) {
        this.subscriptionListService = subscriptionListService;
    }

    @GetMapping("/subscriptions")
    public SubscriptionResponse getSubscriptions(
            @RequestParam String subscriptionType,
            @RequestParam String subscriptionStatus) throws PSQLException {
        var subscriptions = subscriptionListService.getSubscriptions(subscriptionType, subscriptionStatus);
        return new SubscriptionResponse(subscriptions);
    }
}
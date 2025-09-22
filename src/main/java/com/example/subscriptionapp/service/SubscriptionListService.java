// src/main/java/com/example/subscriptionapp/service/SubscriptionListService.java

package com.example.subscriptionapp.service;


import com.example.subscriptionapp.annotation.RetryEnabled;
import com.example.subscriptionapp.repository.FakeSubscriptionRepository;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RetryEnabled
public class SubscriptionListService {

    private final FakeSubscriptionRepository repository;

    public SubscriptionListService(FakeSubscriptionRepository repository) {
        this.repository = repository;
    }

    public List<String> getSubscriptions(String subscriptionType, String subscriptionStatus) throws PSQLException {
        return repository.findSubscriptions(subscriptionType, subscriptionStatus);
    }
}
package com.example.subscriptionapp.repository;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FakeSubscriptionRepository {

    public List<String> findSubscriptions(String type, String status) {
        return List.of("sub-001", "sub-002", "sub-003");
    }
}
// src/test/java/com/example/subscriptionapp/repository/FlakySubscriptionRepository.java

package com.avtor31.retryapp.repository;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Только для тестов!
public class FlakySubscriptionRepository extends FakeSubscriptionRepository {

    private final AtomicInteger callCount = new AtomicInteger(0);
    private final int failTimes;

    public FlakySubscriptionRepository(int failTimes) {
        this.failTimes = failTimes;
    }

    @Override
    public List<String> findSubscriptions(String type, String status) {
        int current = callCount.incrementAndGet();
        if (current <= failTimes) {
            throw new DataAccessException("Simulated transient failure for testing retry") {};
        }
        return List.of("sub-001", "sub-002", "sub-003");
    }
}
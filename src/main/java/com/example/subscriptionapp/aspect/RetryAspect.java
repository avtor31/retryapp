// src/main/java/com/example/subscriptionapp/aspect/RetryAspect.java

package com.example.subscriptionapp.aspect;

import com.example.subscriptionapp.annotation.RetryEnabled;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {

    private static final Logger log = LoggerFactory.getLogger(RetryAspect.class);

    private final RetryTemplate retryTemplate;

    public RetryAspect(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }

    @Around("@within(retryEnabled)")
    public Object retry(ProceedingJoinPoint joinPoint, RetryEnabled retryEnabled) throws Throwable {
        return retryTemplate.execute(new RetryCallback<Object, Throwable>() {
            @Override
            public Object doWithRetry(RetryContext context) throws Throwable {
                log.info("Attempt #{} for method: {}", context.getRetryCount() + 1, joinPoint.getSignature().getName());
                try {
                    return joinPoint.proceed();
                } catch (Exception e) {
                    if (e instanceof DataAccessException) {
                        log.warn("DataAccessException caught, retrying... attempt: {}", context.getRetryCount() + 1);
                        throw e; // RetryTemplate перехватит и решит, повторять ли
                    } else {
                        // Не retryable — пробрасываем сразу
                        throw e;
                    }
                }
            }
        });
    }
}
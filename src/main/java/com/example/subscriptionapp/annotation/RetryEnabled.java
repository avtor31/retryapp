package com.example.subscriptionapp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryEnabled {
    int maxRetries() default 3;
    long initialDelay() default 500L;
    double multiplier() default 2.0;
}
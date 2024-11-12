package com.epam.gym.app.aspect.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginCounterAspect {

    private final Counter loginCounter;

    public LoginCounterAspect(MeterRegistry meterRegistry) {
        loginCounter = Counter.builder("endpoint_counter")
                .description("Count the endpoints invocation")
                .register(meterRegistry);
    }

    @Around("@annotation(com.epam.gym.app.annotation.LoginCounter)")
    public Object loginCounter(ProceedingJoinPoint joinPoint) throws Throwable {
        loginCounter.increment();
        return joinPoint.proceed();
    }
}

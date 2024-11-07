package com.epam.gym.app.actuator.health_indicator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionHealthIndicator implements HealthIndicator {

    private static final String MESSAGE = "message";
    private final SessionChecker sessionChecker;

    @Override
    public Health health() {
        if (sessionChecker.getHttpSession() == null) {
            return Health.down().withDetail(MESSAGE, "Session is not available").build();
        }
        return Health.up().withDetail(MESSAGE, "Session is available").build();
    }
}

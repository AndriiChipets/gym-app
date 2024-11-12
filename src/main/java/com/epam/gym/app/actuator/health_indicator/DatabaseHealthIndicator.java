package com.epam.gym.app.actuator.health_indicator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@AllArgsConstructor
public class DatabaseHealthIndicator implements HealthIndicator {

    private static final String MESSAGE = "message";
    private final DataSource dataSource;

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return Health.up().withDetail(MESSAGE, "DB available").build();
            } else {
                return Health.down().withDetail(MESSAGE, "DB unavailable").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail(MESSAGE, "DB Error").build();
        }
    }
}

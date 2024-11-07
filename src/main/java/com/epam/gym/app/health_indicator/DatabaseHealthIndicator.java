package com.epam.gym.app.health_indicator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@AllArgsConstructor
public class DatabaseHealthIndicator implements HealthIndicator {

    private static final String DATABASE = "Database";
    private final DataSource dataSource;

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1000)) {
                return Health.up().withDetail(DATABASE, "Available").build();
            } else {
                return Health.down().withDetail(DATABASE, "Unavailable").build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail(DATABASE, "Error").build();
        }
    }
}

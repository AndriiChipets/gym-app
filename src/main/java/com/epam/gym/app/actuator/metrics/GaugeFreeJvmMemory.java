package com.epam.gym.app.actuator.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GaugeFreeJvmMemory {

    private final Gauge gauge;

    public GaugeFreeJvmMemory(MeterRegistry meterRegistry) {
        gauge = Gauge.builder("jvm_available_memory", this::getJVMAvailableSpaceInPercent)
                .description("Count free JVM memory")
                .register(meterRegistry);
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 10000)
    public long getJVMAvailableSpaceInPercent() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        return freeMemory * 100 / totalMemory;
    }
}

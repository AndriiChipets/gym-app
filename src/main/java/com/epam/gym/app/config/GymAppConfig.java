package com.epam.gym.app.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;

@Configuration
@ComponentScan(basePackages = "com.epam.gym.app")
@EnableTransactionManagement
@Log4j2
public class GymAppConfig {

    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .condition(Conditions.exclude(Conditions.requestTo("/api/welcome"),
                        Conditions.contentType("application/octet-stream"),
                        Conditions.header("X-Secret", "true")))
                .sink(new DefaultSink(new DefaultHttpLogFormatter(), new DefaultHttpLogWriter()))
                .build();
    }
}

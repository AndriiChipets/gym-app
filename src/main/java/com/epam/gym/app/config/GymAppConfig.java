package com.epam.gym.app.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = "com.epam.gym.app")
@EnableJpaRepositories(basePackages = "com.epam.gym.app.repository")
@EnableTransactionManagement
@Log4j2
public class GymAppConfig {

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}

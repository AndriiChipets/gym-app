package com.epam.gym.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = "com.epam.gym.app")
public class GymAppConfig {

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}

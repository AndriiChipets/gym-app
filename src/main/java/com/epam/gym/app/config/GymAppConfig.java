package com.epam.gym.app.config;

import com.epam.gym.app.storage.TraineeStorage;
import com.epam.gym.app.storage.TrainerStorage;
import com.epam.gym.app.storage.TrainingStorage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = "com.epam.gym.app")
@Log4j2
public class GymAppConfig {

    @Bean
    public TrainerStorage trainerStorage(
            @Value("${file.path.trainer}") Resource resource) {
        return new TrainerStorage(resource);
    }

    @Bean
    public TraineeStorage traineeStorage(
            @Value("${file.path.trainee}") Resource resource) {
        return new TraineeStorage(resource);
    }

    @Bean
    public TrainingStorage trainingStorage(
            @Value("${file.path.training}") Resource resource) {
        return new TrainingStorage(resource);
    }

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}

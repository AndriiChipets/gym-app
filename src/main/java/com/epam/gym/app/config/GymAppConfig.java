package com.epam.gym.app.config;

import com.epam.gym.app.storage.TraineeStorage;
import com.epam.gym.app.storage.TrainerStorage;
import com.epam.gym.app.storage.TrainingStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = "com.epam.gym.app")
public class GymAppConfig {

    @Bean
    public TrainerStorage trainerStorage(
            @Value("${file.path.trainer}") String path) {
        return new TrainerStorage(path);
    }

    @Bean
    public TraineeStorage traineeStorage(
            @Value("${file.path.trainee}") String path) {
        return new TraineeStorage(path);
    }

    @Bean
    public TrainingStorage trainingStorage(
            @Value("${file.path.training}") String path) {
        return new TrainingStorage(path);
    }

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}

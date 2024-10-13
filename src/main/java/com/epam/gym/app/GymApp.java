package com.epam.gym.app;

import com.epam.gym.app.config.GymAppConfig;
import com.epam.gym.app.controller.FrontController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GymApp {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(GymAppConfig.class)) {
            FrontController controller = context.getBean(FrontController.class);
            controller.run();
        }
    }
}

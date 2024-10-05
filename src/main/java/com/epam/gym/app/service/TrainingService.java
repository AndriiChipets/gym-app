package com.epam.gym.app.service;

import com.epam.gym.app.entity.Training;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {

    public void save(Training training) {

    }

    public Training find(long id) {
        return Training.builder().build();
    }
}

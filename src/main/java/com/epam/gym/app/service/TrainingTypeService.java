package com.epam.gym.app.service;

import com.epam.gym.app.entity.TrainingType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService {
    public List<TrainingType> findAll() {
        return List.of();
    }

    public TrainingType find(String trainingTypeName) {
        return null;
    }
}

package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Trainee;

import java.util.Map;

public class TraineeStorage implements Storage<Long, Trainee> {
    @Override
    public Map<Long, Trainee> getData() {
        return Map.of();
    }
}

package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Trainer;

import java.util.Map;

public class TrainerStorage implements Storage<Long, Trainer> {
    @Override
    public Map<Long, Trainer> getData() {
        return Map.of();
    }
}

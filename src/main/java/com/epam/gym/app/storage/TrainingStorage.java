package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Training;

import java.util.Map;

public class TrainingStorage implements Storage<Long, Training> {

    @Override
    public Map<Long, Training> getData() {
        return Map.of();
    }
}

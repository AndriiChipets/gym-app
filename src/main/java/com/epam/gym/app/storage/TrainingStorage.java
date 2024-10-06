package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Training;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class TrainingStorage extends Storage<Long, Training> {

    public TrainingStorage(String path) {
        super(path);
    }

    @Override
    protected Long getEntityId(Training entity) {
        return entity.getId();
    }

    @Override
    protected TypeReference<List<Training>> getTypeReference() {
        return new TypeReference<List<Training>>() {
        };
    }
}

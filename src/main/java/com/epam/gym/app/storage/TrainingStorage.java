package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Training;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;

import java.util.List;

@Log4j2
public class TrainingStorage extends Storage<Long, Training> {

    public TrainingStorage(Resource resource) {
        super(resource);
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

package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.utils.UtilClass;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class TraineeStorage extends Storage<Long, Trainee> {

    public TraineeStorage(String path) {
        super(path);
    }

    @Override
    protected void init() {
        super.init();
        getData().values().forEach(trainee -> {
            trainee.setPassword(UtilClass.generateRandomPassword());
            trainee.setUsername(UtilClass.generateUserName(
                    trainee.getFirstname(), trainee.getLastname()
            ));
        });
    }

    @Override
    protected Long getEntityId(Trainee entity) {
        return entity.getId();
    }

    @Override
    protected TypeReference<List<Trainee>> getTypeReference() {
        return new TypeReference<List<Trainee>>() {
        };
    }
}

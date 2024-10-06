package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.utils.UtilClass;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class TrainerStorage extends Storage<Long, Trainer> {

    public TrainerStorage(String path) {
        super(path);
    }

    @Override
    protected void init() {
        super.init();
        getData().values().forEach(trainer -> {
            trainer.setPassword(UtilClass.generateRandomPassword());
            trainer.setUsername(UtilClass.generateUserName(
                    trainer.getFirstname(), trainer.getLastname()
            ));
        });
    }

    @Override
    protected Long getEntityId(Trainer entity) {
        return entity.getId();
    }

    @Override
    protected TypeReference<List<Trainer>> getTypeReference() {
        return new TypeReference<List<Trainer>>() {
        };
    }
}

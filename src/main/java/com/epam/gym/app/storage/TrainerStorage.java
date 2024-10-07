package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.utils.UtilClass;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;

import java.util.List;

@Log4j2
public class TrainerStorage extends Storage<Long, Trainer> {

    public TrainerStorage(Resource resource) {
        super(resource);
    }

    @Override
    protected void init() {
        super.init();
        List<Trainer> trainers = getData().values().stream().toList();
        setUserName(trainers);
        setPassword(trainers);
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

    private void setUserName(List<Trainer> trainers) {
        log.info("Set userName to Trainers while Storage initialization");
        trainers.forEach(trainer ->
                trainer.setUsername(UtilClass.generateUserName(
                        trainer.getFirstname(), trainer.getLastname())));
    }

    private void setPassword(List<Trainer> trainers) {
        log.info("Set Password to Trainers while Storage initialization");
        trainers.forEach(trainee ->
                trainee.setPassword(UtilClass.generateRandomPassword()));
    }
}

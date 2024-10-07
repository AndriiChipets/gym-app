package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.utils.UtilClass;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;

import java.util.List;

@Log4j2
public class TraineeStorage extends Storage<Long, Trainee> {

    public TraineeStorage(Resource resource) {
        super(resource);
    }

    @Override
    protected void init() {
        super.init();
        List<Trainee> trainees = getData().values().stream().toList();
        setUserName(trainees);
        setPassword(trainees);
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

    private void setUserName(List<Trainee> trainees) {
        log.info("Set userName to Trainees while Storage initialization");
        trainees.forEach(trainee ->
                trainee.setUsername(UtilClass.generateUserName(
                        trainee.getFirstname(), trainee.getLastname())));
    }

    private void setPassword(List<Trainee> trainees) {
        log.info("Set Password to Trainees while Storage initialization");
        trainees.forEach(trainee ->
                trainee.setPassword(UtilClass.generateRandomPassword()));
    }
}

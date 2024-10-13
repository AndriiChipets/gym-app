package com.epam.gym.app.storage;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.utils.UserUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

@Getter
@Log4j2
public class TraineeStorage extends Storage<Long, Trainee> {

    private TrainerStorage trainerStorage;

    public TraineeStorage(Resource resource) {
        super(resource);
    }

    public void setTrainerStorage(TrainerStorage trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Override
    protected void init() {
        super.init();
        List<Trainee> trainees = getData().values().stream().toList();
        List<Trainer> trainers = new ArrayList<>();
        if (trainerStorage != null) {
            trainers.addAll(trainerStorage.getData().values().stream().toList());
        }
        setUserName(trainees, trainers);
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

    private void setUserName(List<Trainee> trainees, List<Trainer> trainers) {
        log.info("Set userName to Trainees while Storage initialization");
        trainees.forEach(trainee ->
                trainee.setUsername(UserUtil.generateUsername(
                        trainee.getFirstname(),
                        trainee.getLastname(),
                        trainers,
                        trainees)));
    }

    private void setPassword(List<Trainee> trainees) {
        log.info("Set Password to Trainees while Storage initialization");
        trainees.forEach(trainee ->
                trainee.setPassword(UserUtil.generateRandomPassword()));
    }
}

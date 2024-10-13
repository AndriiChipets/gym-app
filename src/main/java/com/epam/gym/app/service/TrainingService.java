package com.epam.gym.app.service;

import com.epam.gym.app.dao.TrainingDao;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TrainingService {

    private final TrainingDao trainingDao;

    public Training save(Training training) {
        log.debug("Save Training with name {}", training.getName());
        Training savedTraining = trainingDao.save(training);
        if (savedTraining != null) {
            log.debug("Training saved successfully");
            return savedTraining;
        } else {
            log.error("Training wasn't saved successfully");
            throw new NoEntityPresentException("Training wasn't saved successfully");
        }
    }

    public Training find(long id) {
        log.debug("Find Training with id {}", id);
        return trainingDao.findById(id).orElseThrow(
                () -> {
                    log.error("There is no Training with provided id {}", id);
                    throw new NoEntityPresentException("There is no Training with provided id: " + id);
                });
    }

    public List<Training> findAll() {
        log.debug("Find all Trainings");
        return trainingDao.findAll();
    }
}

package com.epam.gym.app.service;

import com.epam.gym.app.entity.Training;
import com.epam.gym.app.repository.TrainingRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TrainingService {

    private final TrainingRepository trainingRepository;

    @Transactional
    public Training save(Training training) {
        log.debug("Save Training with name {}", training.getName());
        Training savedTraining = trainingRepository.save(training);
        log.debug("Training saved successfully");
        return savedTraining;
    }

    @Transactional(readOnly = true)
    public Training find(long id) {
        log.debug("Find Training with id {}", id);

        return trainingRepository.findById(id).orElseThrow(
                () -> {
                    log.error("There is no Training with provided id {}", id);
                    return new NoEntityPresentException("There is no Training with provided id: " + id);
                });
    }

    @Transactional(readOnly = true)
    public List<Training> findAll() {
        log.debug("Find all Trainings");
        return trainingRepository.findAll();
    }
}

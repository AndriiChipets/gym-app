package com.epam.gym.app.service;

import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.repository.TrainerRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TrainerService {

    private final TrainerRepository trainerRepository;

    @Transactional
    public Trainer save(Trainer trainer) {
        log.debug("Save Trainer with first name {} and last name {}",
                trainer.getFirstname(), trainer.getLastname());
        Trainer savedTrainer = trainerRepository.save(trainer);
        log.debug("Trainer has been saved successfully");
        return savedTrainer;
    }

    @Transactional(readOnly = true)
    public Trainer find(String username) {
        log.debug("Find Trainer with username {}", username);
        return trainerRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("There is no Trainer with provided username {}", username);
                    return new NoEntityPresentException("There is no Trainer with provided username: " + username);
                });
    }

    @Transactional(readOnly = true)
    public List<Trainer> findAll() {
        log.debug("Find all Trainers");
        return trainerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public boolean login(String username, String password) {
        log.debug("Check if Trainer with username {} is exist in DataBase", username);
        boolean isExists = trainerRepository.existsByUsernameAndPassword(username, password);
        if (isExists) {
            log.debug("Trainer with username {} is exist in DataBase", username);
            return true;
        }
        log.debug("Trainer with username {} isn't exist in DataBase", username);
        return false;
    }

    @Transactional(readOnly = true)
    public List<Training> getTrainingsList(String trainerUsername, LocalDate dateFrom, LocalDate dateTo, String traineeUsername) {
        log.debug("Get Trainings list for Trainer with username {}", trainerUsername);
        Trainer trainer = trainerRepository.findByUsername(trainerUsername).orElseThrow(
                () -> {
                    log.error("There is no Trainer with provided username {}", trainerUsername);
                    return new NoEntityPresentException("There is no Trainer with provided username: " + trainerUsername);
                });
        return trainer.getTrainings()
                .stream()
                .filter(training -> training.getTrainer().getUsername().equals(traineeUsername))
                .filter(training -> training.getDate().isAfter(dateFrom) && training.getDate().isBefore(dateTo))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Trainer> getTrainersListNotAssignedOnTrainee(String traineeUsername) {
        return trainerRepository.findAllNotAssignedOnTrainee(traineeUsername);
    }
}

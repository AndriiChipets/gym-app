package com.epam.gym.app.service;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.repository.TraineeRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TraineeService {

    private final TraineeRepository traineeRepository;

    public Trainee save(Trainee trainee) {
        log.debug("Save Trainee with first name {} and last name {}",
                trainee.getFirstname(), trainee.getLastname());
        Trainee savedTrainee = traineeRepository.save(trainee);
        log.debug("Trainee has been saved successfully");
        return savedTrainee;
    }

    public void delete(String username) {
        log.debug("Delete Trainee with username {}", username);
        traineeRepository.deleteByUsername(username);
        log.debug("Trainee with username {} deleted successfully", username);
    }

    public Trainee find(String username) {
        log.debug("Find Trainee with username {}", username);
        return traineeRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("There is no Trainee with provided username {}", username);
                    return new NoEntityPresentException("There is no Trainee with provided username: " + username);
                });
    }

    public List<Trainee> findAll() {
        log.debug("Find all Trainees");
        return traineeRepository.findAll();
    }

    public boolean login(String username, String password) {
        log.debug("Trainee if user with username {} is exist in DataBase", username);
        boolean isExists = traineeRepository.existsByUsernameAndPassword(username, password);
        if (isExists) {
            log.debug("Trainee with username {} is exist in DataBase", username);
            return true;
        }
        log.debug("Trainee with username {} isn't exist in DataBase", username);
        return false;
    }

    public List<Training> getTrainingsList(String traineeUsername,
                                           LocalDate dateFrom,
                                           LocalDate dateTo,
                                           String trainerUsername) {

        Trainee trainee = find(traineeUsername);
        return trainee.getTrainings()
                .stream()
                .filter(training -> training.getTrainer().getUsername().equals(trainerUsername))
                .filter(training -> training.getDate().isAfter(dateFrom) && training.getDate().isBefore(dateTo))
                .toList();
    }

    public void addTrainerToTraineeList(Trainee trainee, Trainer trainer) {
        trainee.addTrainer(trainer);
        traineeRepository.save(trainee);
    }

    public void removeTrainerToTraineeList(Trainee trainee, Trainer trainer) {
        trainee.removeTrainer(trainer);
        traineeRepository.save(trainee);
    }
}

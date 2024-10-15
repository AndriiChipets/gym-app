package com.epam.gym.app.service;

import com.epam.gym.app.dao.TraineeDao;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Training;
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

    private final TraineeDao traineeDao;

    public Trainee save(Trainee trainee) {
        log.debug("Save Trainee with first name {} and last name {}",
                trainee.getFirstname(), trainee.getLastname());
        Trainee savedTrainee = traineeDao.save(trainee);
        if (savedTrainee != null) {
            log.debug("Trainee was saved successfully");
            return savedTrainee;
        } else {
            log.error("Trainee wasn't saved successfully");
            throw new NoEntityPresentException("Trainee wasn't saved successfully");
        }
    }

    public Trainee update(Trainee trainee) {
        log.debug("Update Trainee with id {}", trainee.getId());
        Trainee updatedTrainee = traineeDao.update(trainee);
        log.debug("Trainee with id {} updated successfully", trainee.getId());
        return updatedTrainee;
    }

    public void delete(long id) {
        log.debug("Delete Trainee with id {}", id);
        traineeDao.deleteById(id);
        log.debug("Trainee with id {} deleted successfully", id);
    }

    public Trainee find(String username) {
        log.debug("Find Trainee with username {}", username);
        return traineeDao.findUsername(username).orElseThrow(
                () -> {
                    log.error("There is no Trainee with provided username {}", username);
                    throw new NoEntityPresentException("There is no Trainee with provided username: " + username);
                });
    }

    public List<Trainee> findAll() {
        log.debug("Find all Trainees");
        return traineeDao.findAll();
    }

    public boolean login(String username, String password) {
        return true;
    }

    public List<Training> getTrainingsList(String username, LocalDate dateFrom, LocalDate dateTo, String trainerName) {
        return List.of();
    }
}

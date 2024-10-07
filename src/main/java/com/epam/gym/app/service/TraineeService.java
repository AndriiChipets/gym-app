package com.epam.gym.app.service;

import com.epam.gym.app.dao.TraineeDao;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TraineeService {

    private final TraineeDao traineeDao;

    public void save(Trainee trainee) {
        log.info("Save Trainee with first name {} and last name {}",
                trainee.getFirstname(), trainee.getLastname());
        traineeDao.save(trainee);
        log.info("Trainee saved successfully");
    }

    public void update(Trainee trainee) {
        log.info("Update Trainee with id {}", trainee.getId());
        traineeDao.update(trainee);
        log.info("Trainee with id {} updated successfully", trainee.getId());
    }

    public void delete(long id) {
        log.info("Delete Trainee with id {}", id);
        traineeDao.deleteById(id);
        log.info("Trainee with id {} deleted successfully", id);
    }

    public Trainee find(long id) {
        log.info("Find Trainee with id {}", id);
        return traineeDao.findById(id).orElseThrow(
                () -> {
                    log.error("There is no Trainee with provided id {}", id);
                    throw new NoEntityPresentException("There is no Trainee with provided id: " + id);
                });
    }

    public List<Trainee> findAll() {
        log.info("Find all Trainees");
        return traineeDao.findAll();
    }
}

package com.epam.gym.app.service;

import com.epam.gym.app.dao.TrainerDao;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TrainerService {

    private final TrainerDao trainerDao;

    public Trainer save(Trainer trainer) {
        log.debug("Save Trainer with first name {} and last name {}",
                trainer.getFirstname(), trainer.getLastname());
        Trainer savedTrainer = trainerDao.save(trainer);
        if (savedTrainer != null) {
            log.debug("Trainer saved successfully");
            return savedTrainer;
        } else {
            log.error("Trainer wasn't saved successfully");
            throw new NoEntityPresentException("Trainer wasn't saved successfully");
        }
    }

    public Trainer update(Trainer trainer) {
        log.debug("Update Trainer with id {}", trainer.getId());
        Trainer updatedTrainer = trainerDao.update(trainer);
        log.debug("Trainer with id {} deleted successfully", trainer.getId());
        return updatedTrainer;
    }

    public Trainer find(long id) {
        log.debug("Find Trainer with id {}", id);
        return trainerDao.findById(id).orElseThrow(
                () -> {
                    log.error("There is no Trainer with provided id {}", id);
                    throw new NoEntityPresentException("There is no Trainer with provided id: " + id);
                });
    }

    public List<Trainer> findAll() {
        log.debug("Find all Trainers");
        return trainerDao.findAll();
    }
}

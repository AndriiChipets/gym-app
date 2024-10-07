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

    public void save(Trainer trainer) {
        log.info("Save Trainer with first name {} and last name {}",
                trainer.getFirstname(), trainer.getLastname());
        trainerDao.save(trainer);
        log.info("Trainer saved successfully");
    }

    public void update(Trainer trainer) {
        log.info("Update Trainer with id {}", trainer.getId());
        trainerDao.update(trainer);
        log.info("Trainer with id {} deleted successfully", trainer.getId());
    }

    public Trainer find(long id) {
        log.info("Find Trainer with id {}", id);
        return trainerDao.findById(id).orElseThrow(
                () -> {
                    log.error("There is no Trainer with provided id {}", id);
                    throw new NoEntityPresentException("There is no Trainer with provided id: " + id);
                });
    }

    public List<Trainer> findAll() {
        log.info("Find all Trainers");
        return trainerDao.findAll();
    }
}

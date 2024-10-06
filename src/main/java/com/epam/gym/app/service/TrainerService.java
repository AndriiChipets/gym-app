package com.epam.gym.app.service;

import com.epam.gym.app.dao.TrainerDao;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.service.exception.NoEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainerService {

    private final TrainerDao trainerDao;

    public void save(Trainer trainer) {
        trainerDao.save(trainer);
    }

    public void update(Trainer trainer) {
        trainerDao.update(trainer);
    }

    public Trainer find(long id) {
        return trainerDao.findById(id).orElseThrow(
                () -> new NoEntityException("There is no Trainer with provided id: " + id));
    }

    public List<Trainer> findAll() {
        return trainerDao.findAll();
    }
}

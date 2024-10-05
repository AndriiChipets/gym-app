package com.epam.gym.app.service;

import com.epam.gym.app.dao.TrainingDao;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.service.exception.NoEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrainingService {

    private final TrainingDao trainingDao;

    public void save(Training training) {
        trainingDao.save(training);
    }

    public Training find(long id) {
        return trainingDao.findById(id).orElseThrow(
                () -> new NoEntityException("There is no Training with provided id: " + id));
    }
}

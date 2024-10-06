package com.epam.gym.app.service;

import com.epam.gym.app.dao.TraineeDao;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.service.exception.NoEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TraineeService {

    private final TraineeDao traineeDao;

    public void save(Trainee trainee) {
        traineeDao.save(trainee);
    }

    public void update(Trainee trainee) {
        traineeDao.update(trainee);
    }

    public void delete(long id) {
        traineeDao.deleteById(id);
    }

    public Trainee find(long id) {
        return traineeDao.findById(id).orElseThrow(
                () -> new NoEntityException("There is no Trainee with provided id: " + id));
    }

    public List<Trainee> findAll() {
        return traineeDao.findAll();
    }
}

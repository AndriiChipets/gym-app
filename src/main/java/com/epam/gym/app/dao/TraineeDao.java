package com.epam.gym.app.dao;

import com.epam.gym.app.entity.Trainee;

public interface TraineeDao extends CrudDao<Long, Trainee> {
    void register(Trainee trainee);
}

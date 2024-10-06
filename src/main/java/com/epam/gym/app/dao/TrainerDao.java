package com.epam.gym.app.dao;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;

public interface TrainerDao extends CrudDao<Long, Trainer> {

    void register(Trainer trainer);
}

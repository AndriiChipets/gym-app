package com.epam.gym.app.dao.impl;

import com.epam.gym.app.dao.TrainingDao;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.storage.Storage;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDaoImpl extends AbstractCrudDaoImpl<Long, Training> implements TrainingDao {

    protected TrainingDaoImpl(Storage<Long, Training> storage) {
        super(storage);
    }

    @Override
    protected Long generateNextId() {
        return getLastId() + 1;
    }

    @Override
    protected Long getEntityId(Training entity) {
        return entity.getId();
    }

    private long getLastId() {
        if (getStorage().getData().isEmpty()) {
            return 0L;
        }
        return getStorage()
                .getData()
                .keySet()
                .stream()
                .max(Long::compareTo)
                .get();
    }
}

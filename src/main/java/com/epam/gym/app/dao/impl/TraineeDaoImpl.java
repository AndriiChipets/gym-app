package com.epam.gym.app.dao.impl;

import com.epam.gym.app.dao.TraineeDao;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.storage.Storage;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeDaoImpl extends AbstractCrudDaoImpl<Long, Trainee> implements TraineeDao {

    public TraineeDaoImpl(Storage<Long, Trainee> storage) {
        super(storage);
    }

    @Override
    public Long generateNextId() {
        return getLastId() + 1;
    }

    @Override
    protected Long getEntityId(Trainee entity) {
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
                .orElse(0L);
    }

    @Override
    protected void setEntityId(Long id, Trainee entity) {
        entity.setId(id);
    }
}

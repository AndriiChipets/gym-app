package com.epam.gym.app.dao.impl;

import com.epam.gym.app.dao.TrainerDao;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.storage.Storage;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerDaoImpl extends AbstractCrudDaoImpl<Long, Trainer> implements TrainerDao {

    protected TrainerDaoImpl(Storage<Long, Trainer> storage) {
        super(storage);
    }

    @Override
    public void save(Trainer trainer) {
        super.save(trainer);
    }

    @Override
    protected Long generateNextId() {
        return getLastId() + 1;
    }

    @Override
    protected Long getEntityId(Trainer entity) {
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

    @Override
    protected void setEntityId(Long id, Trainer entity) {
        entity.setId(id);
    }
}

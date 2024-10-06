package com.epam.gym.app.dao.impl;

import com.epam.gym.app.dao.TraineeDao;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.storage.Storage;
import com.epam.gym.app.utils.UtilClass;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeDaoImpl extends AbstractCrudDaoImpl<Long, Trainee> implements TraineeDao {

    public TraineeDaoImpl(Storage<Long, Trainee> storage) {
        super(storage);
    }

    @Override
    public void save(Trainee trainee) {
        super.save(trainee);
        register(trainee);
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
                .get();
    }

    @Override
    protected void setEntityId(Long id, Trainee entity) {
        entity.setId(id);
    }

    @Override
    public void register(Trainee trainee) {
        String firstName = trainee.getFirstname();
        String lastName = trainee.getLastname();
        String userName = UtilClass.generateUserName(firstName, lastName);
        String password = UtilClass.generateRandomPassword();

        trainee.setUsername(userName);
        trainee.setPassword(password);
    }
}

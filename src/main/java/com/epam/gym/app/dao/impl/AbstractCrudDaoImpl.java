package com.epam.gym.app.dao.impl;

import com.epam.gym.app.dao.CrudDao;
import com.epam.gym.app.storage.Storage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractCrudDaoImpl<ID, E> implements CrudDao<ID, E> {

    private final Storage<ID, E> storage;

    @Override
    public void save(E entity) {
        ID id = generateNextId();
        storage.getData().put(id, entity);
    }

    @Override
    public void update(E entity) {
        ID id = getEntityId(entity);
        storage.getData().put(id, entity);
    }

    @Override
    public Optional<E> findById(ID id) {
        return Optional.of(storage.getData().get(id));
    }

    @Override
    public void deleteById(ID id) {
        storage.getData().remove(id);
    }

    protected abstract ID generateNextId();

    protected abstract ID getEntityId(E entity);

}

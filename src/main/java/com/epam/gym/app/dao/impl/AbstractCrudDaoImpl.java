package com.epam.gym.app.dao.impl;

import com.epam.gym.app.dao.CrudDao;
import com.epam.gym.app.storage.Storage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Log4j2
public abstract class AbstractCrudDaoImpl<ID, E> implements CrudDao<ID, E> {

    private final Storage<ID, E> storage;

    @Override
    public void save(E entity) {
        ID id = generateNextId();
        log.info("Save entity with id {}", id);
        setEntityId(id, entity);
        storage.getData().put(id, entity);
        log.info("Entity with id {} saved successfully", id);
    }

    @Override
    public void update(E entity) {
        ID id = getEntityId(entity);
        log.info("Update entity with id {}", id);
        storage.getData().put(id, entity);
        log.info("Entity with id {} updated successfully", id);
    }

    @Override
    public Optional<E> findById(ID id) {
        log.info("Find entity with id {}", id);
        return Optional.ofNullable(storage.getData().get(id));
    }

    @Override
    public List<E> findAll() {
        log.info("Find all entities");
        return storage.getData().values().stream().toList();
    }

    @Override
    public void deleteById(ID id) {
        log.info("Delete entity with id {}", id);
        storage.getData().remove(id);
        log.info("Entity with id {} deleted successfully", id);
    }

    protected abstract ID generateNextId();

    protected abstract ID getEntityId(E entity);

    protected abstract void setEntityId(ID id, E entity);

}

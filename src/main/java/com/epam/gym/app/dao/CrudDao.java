package com.epam.gym.app.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<ID, E> {

    void save(E entity);

    Optional<E> findById(ID id);

    List<E> findAll();

    void update(E entity);

    void deleteById(ID id);
}
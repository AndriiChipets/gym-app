package com.epam.gym.app.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<ID, E> {

    E save(E entity);

    Optional<E> findById(ID id);

    List<E> findAll();

    E update(E entity);

    void deleteById(ID id);
}

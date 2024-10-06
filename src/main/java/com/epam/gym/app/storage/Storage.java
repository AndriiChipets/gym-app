package com.epam.gym.app.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public abstract class Storage<ID, E> {

    private final String path;
    private final Map<ID, E> data;

    protected Storage(String path) {
        this.path = path;
        this.data = new HashMap<>();
    }

    @PostConstruct
    protected void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<E> entities;
        try {
            entities = mapper.readValue(new File(path), getTypeReference());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        data.putAll(addDataToStore(entities));
    }

    private Map<ID, E> addDataToStore(List<E> entities) {
        return entities.stream().collect(Collectors.toMap(this::getEntityId, Function.identity()));
    }

    protected abstract ID getEntityId(E entity);

    protected abstract TypeReference<List<E>> getTypeReference();

}

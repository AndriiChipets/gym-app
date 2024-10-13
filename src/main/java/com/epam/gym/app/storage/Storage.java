package com.epam.gym.app.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Log4j2
public abstract class Storage<ID, E> {

    private final Resource resource;
    private final Map<ID, E> data;

    protected Storage(Resource resource) {
        this.resource = resource;
        this.data = new HashMap<>();
    }

    @PostConstruct
    protected void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<E> entities;
        try {
            entities = mapper.readValue(resource.getInputStream(), getTypeReference());
        } catch (IOException e) {
            log.error("Storage initialization from the file {} is failed", resource.getFilename());
            throw new IllegalArgumentException(e);
        }
        data.putAll(addDataToStore(entities));
        log.info("Storage from the file {} initialized successfully", resource.getFilename());
    }

    private Map<ID, E> addDataToStore(List<E> entities) {
        return entities.stream().collect(Collectors.toMap(this::getEntityId, Function.identity()));
    }

    protected abstract ID getEntityId(E entity);

    protected abstract TypeReference<List<E>> getTypeReference();
}

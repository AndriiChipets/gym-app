package com.epam.gym.app.service;

import com.epam.gym.app.dto.training_type.TrainingTypeDTO;
import com.epam.gym.app.entity.TrainingType;
import com.epam.gym.app.mapper.training_type.TrainingTypeMapper;
import com.epam.gym.app.repository.TrainingTypeRepository;
import com.epam.gym.app.exception.NoEntityPresentException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TrainingTypeService {

    TrainingTypeRepository trainingTypeRepository;
    TrainingTypeMapper typeMapper;

    @Transactional(readOnly = true)
    public TrainingTypeDTO find(String name) {
        log.debug("Find TrainingType with name {}", name);

        TrainingType trainingType = trainingTypeRepository.findByName(name).orElseThrow(
                () -> {
                    log.error("There is no Training type with name {}", name);
                    return new NoEntityPresentException("There is no TrainingType with name: " + name);
                });
        return typeMapper.mapTypeToTypeDto(trainingType);
    }

    @Transactional(readOnly = true)
    public List<TrainingTypeDTO> findAll() {
        log.debug("Find all TrainingTypes");
        return trainingTypeRepository.findAll()
                .stream()
                .map(typeMapper::mapTypeToTypeDto)
                .toList();
    }
}

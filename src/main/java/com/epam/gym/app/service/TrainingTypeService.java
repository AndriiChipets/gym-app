package com.epam.gym.app.service;

import com.epam.gym.app.entity.TrainingType;
import com.epam.gym.app.repository.TrainingTypeRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TrainingTypeService {

    TrainingTypeRepository trainingTypeRepository;

    public List<TrainingType> findAll() {
        log.debug("Find all TrainingTypes");
        return trainingTypeRepository.findAll();
    }

    public TrainingType find(String trainingTypeName) {
        log.debug("Find TrainingType with name {}", trainingTypeName);
        return trainingTypeRepository.findByName(trainingTypeName).orElseThrow(
                () -> {
                    log.error("There is no Training type with name {}", trainingTypeName);
                    return new NoEntityPresentException("There is no Training type with name: " + trainingTypeName);
                });
    }
}

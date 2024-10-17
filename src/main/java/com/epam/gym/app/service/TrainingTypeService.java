package com.epam.gym.app.service;

import com.epam.gym.app.entity.TrainingType;
import com.epam.gym.app.repository.TrainingTypeRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
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

    @Transactional(readOnly = true)
    public List<TrainingType> findAll() {
        log.debug("Find all TrainingTypes");
        return trainingTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TrainingType find(String name) {
        log.debug("Find TrainingType with name {}", name);

        return trainingTypeRepository.findByName(name).orElseThrow(
                () -> {
                    log.error("There is no Training type with name {}", name);
                    return new NoEntityPresentException("There is no Training type with name: " + name);
                });
    }
}

package com.epam.gym.app.service;

import com.epam.gym.app.dto.TrainingDTO;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.mapper.TrainingMapperStruct;
import com.epam.gym.app.repository.TrainingRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
@Validated
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapperStruct trainingMapper;

    @Transactional
    public TrainingDTO save(@Valid TrainingDTO trainingDto) {
        log.debug("Save Training with name {}", trainingDto.getName());
        Training training = trainingMapper.mapTrainingDtoToTraining(trainingDto);
        training = trainingRepository.save(training);
        log.debug("Training saved successfully");
        return trainingMapper.mapTrainingToTrainingDto(training);
    }

    @Transactional(readOnly = true)
    public TrainingDTO find(long id) {
        log.debug("Find Training with id {}", id);

        Training training = trainingRepository.findById(id).orElseThrow(
                () -> {
                    log.error("There is no Training with provided id {}", id);
                    return new NoEntityPresentException("There is no Training with provided id: " + id);
                });
        return trainingMapper.mapTrainingToTrainingDto(training);
    }

    @Transactional(readOnly = true)
    public List<TrainingDTO> findAll() {
        log.debug("Find all Trainings");
        return trainingRepository.findAll()
                .stream()
                .map(trainingMapper::mapTrainingToTrainingDto)
                .toList();
    }
}

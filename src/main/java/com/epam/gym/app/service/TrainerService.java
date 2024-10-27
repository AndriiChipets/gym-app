package com.epam.gym.app.service;

import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.dto.TrainingDTO;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.mapper.TrainerMapperStruct;
import com.epam.gym.app.mapper.TrainingMapperStruct;
import com.epam.gym.app.repository.TrainerRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
@Validated
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapperStruct trainerMapper;
    private final TrainingMapperStruct trainingMapper;

    @Transactional
    public TrainerDto save(@Valid TrainerDto trainerDto) {
        log.debug("Save Trainer with first name {} and last name {}",
                trainerDto.getFirstname(), trainerDto.getLastname());

        Trainer trainer = trainerMapper.mapTrainerDtoToTrainer(trainerDto);
        trainer = trainerRepository.save(trainer);

        log.debug("Trainer has been saved successfully");
        return trainerMapper.mapTrainerToTrainerDto(trainer);
    }

    @Transactional(readOnly = true)
    public TrainerDto find(String username) {
        log.debug("Find Trainer with username {}", username);

        Trainer trainer = findTrainer(username);
        return trainerMapper.mapTrainerToTrainerDto(trainer);
    }

    @Transactional(readOnly = true)
    public List<TrainerDto> findAll() {
        log.debug("Find all Trainers");
        return trainerRepository.findAll()
                .stream()
                .map(trainerMapper::mapTrainerToTrainerDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean login(String username, String password) {
        log.info("Check if Trainer with username {} is exist in DataBase", username);

        boolean isExists = trainerRepository.existsByUsernameAndPassword(username, password);
        if (isExists) {
            log.debug("Trainer with username {} is exist in DataBase", username);
            return true;
        }
        log.info("Trainer with username {} isn't exist in DataBase", username);
        return false;
    }

    @Transactional(readOnly = true)
    public List<TrainingDTO> getTrainingsList(String trainerUsername,
                                              LocalDate dateFrom,
                                              LocalDate dateTo,
                                              String traineeUsername) {

        log.debug("Find Trainer's Training list with username {} and criteria: " +
                "Trainee username {}, from date {}, to date {}", trainerUsername, traineeUsername, dateFrom, dateTo);

        Trainer trainer = findTrainer(trainerUsername);

        return trainer.getTrainings()
                .stream()
                .filter(training -> training.getTrainee().getUsername().equals(traineeUsername))
                .filter(training -> training.getDate().isAfter(dateFrom) && training.getDate().isBefore(dateTo))
                .map(trainingMapper::mapTrainingToTrainingDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TrainerDto> getTrainersListNotAssignedOnTrainee(String traineeUsername) {
        log.debug("Find Trainer list not assign on Trainee with username {}", traineeUsername);

        return trainerRepository.findAllNotAssignedOnTrainee(traineeUsername)
                .stream()
                .map(trainerMapper::mapTrainerToTrainerDto)
                .toList();
    }

    private Trainer findTrainer(String username) {
        return trainerRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("There is no Trainer with provided username {}", username);
                    return new NoEntityPresentException("There is no Trainer with provided username: " + username);
                });
    }
}

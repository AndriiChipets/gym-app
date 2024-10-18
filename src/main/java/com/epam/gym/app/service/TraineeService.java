package com.epam.gym.app.service;

import com.epam.gym.app.dto.TraineeDto;
import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.dto.TrainingDto;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.mapper.TraineeMapperStruct;
import com.epam.gym.app.mapper.TrainerMapperStruct;
import com.epam.gym.app.mapper.TrainingMapperStruct;
import com.epam.gym.app.repository.TraineeRepository;
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
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final TraineeMapperStruct traineeMapper;
    private final TrainerMapperStruct trainerMapper;
    private final TrainingMapperStruct trainingMapper;

    @Transactional
    public TraineeDto save(@Valid TraineeDto traineeDto) {
        log.debug("Save Trainee with first name {} and last name {}",
                traineeDto.getFirstname(), traineeDto.getLastname());

        Trainee trainee = traineeMapper.mapTraineeDtoToTrainee(traineeDto);
        trainee = traineeRepository.save(trainee);

        log.debug("Trainee has been saved successfully");
        return traineeMapper.mapTraineeToTraineeDto(trainee);
    }

    @Transactional
    public void delete(String username) {
        log.debug("Delete Trainee with username {}", username);
        traineeRepository.deleteByUsername(username);
        log.debug("Trainee with username {} deleted successfully", username);
    }

    @Transactional(readOnly = true)
    public TraineeDto find(String username) {
        log.debug("Find Trainee with username {}", username);

        Trainee trainee = findTrainee(username);
        return traineeMapper.mapTraineeToTraineeDto(trainee);
    }

    @Transactional(readOnly = true)
    public List<TraineeDto> findAll() {
        log.debug("Find all Trainees");
        return traineeRepository.findAll()
                .stream()
                .map(traineeMapper::mapTraineeToTraineeDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean login(String username, String password) {
        log.debug("Check if Trainee with username {} is exist in DataBase", username);

        boolean isExists = traineeRepository.existsByUsernameAndPassword(username, password);
        if (isExists) {
            log.info("Trainee with username {} is exist in DataBase", username);
            return true;
        }
        log.info("Trainee with username {} isn't exist in DataBase", username);
        return false;
    }

    @Transactional(readOnly = true)
    public List<TrainingDto> getTrainingsList(String traineeUsername,
                                              LocalDate dateFrom,
                                              LocalDate dateTo,
                                              String trainerUsername) {

        log.debug("Find Trainee's Training list with username {} and criteria: " +
                "Trainer username {}, from date {}, to date {}", traineeUsername, trainerUsername, dateFrom, dateTo);

        Trainee trainee = findTrainee(traineeUsername);

        return trainee.getTrainings()
                .stream()
                .filter(training -> training.getTrainer().getUsername().equals(trainerUsername))
                .filter(training -> training.getDate().isAfter(dateFrom) && training.getDate().isBefore(dateTo))
                .map(trainingMapper::mapTrainingToTrainingDto)
                .toList();
    }

    @Transactional
    public void addTrainerToTraineeList(@Valid TraineeDto traineeDto, @Valid TrainerDto trainerDto) {
        log.debug("Add Trainer with username {} to Trainee's trainer list with username {}",
                trainerDto.getUsername(), traineeDto.getUsername());

        Trainee trainee = traineeMapper.mapTraineeDtoToTrainee(traineeDto);
        Trainer trainer = trainerMapper.mapTrainerDtoToTrainer(trainerDto);

        trainee.addTrainer(trainer);
        traineeRepository.save(trainee);

        log.debug("Trainer with username {} added successfully to Trainee's trainer list with username {}",
                trainerDto.getUsername(), traineeDto.getUsername());
    }

    @Transactional
    public void removeTrainerToTraineeList(@Valid TraineeDto traineeDto, @Valid TrainerDto trainerDto) {
        log.debug("Remove Trainer with username {} from Trainee's trainer list with username {}",
                trainerDto.getUsername(), traineeDto.getUsername());

        Trainee trainee = traineeMapper.mapTraineeDtoToTrainee(traineeDto);
        Trainer trainer = trainerMapper.mapTrainerDtoToTrainer(trainerDto);

        trainee.removeTrainer(trainer);
        traineeRepository.save(trainee);

        log.debug("Trainer with username {} removed successfully from Trainee's trainer list with username {}",
                trainerDto.getUsername(), traineeDto.getUsername());
    }

    private Trainee findTrainee(String username) {
        return traineeRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("There is no Trainee with provided username {}", username);
                    return new NoEntityPresentException("There is no Trainee with provided username: " + username);
                });
    }
}

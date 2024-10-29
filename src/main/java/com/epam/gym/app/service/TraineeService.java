package com.epam.gym.app.service;

import com.epam.gym.app.dto.trainee.TraineeGetDTO;
import com.epam.gym.app.dto.trainee.TraineeRegDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainerListDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingFilterDTO;
import com.epam.gym.app.dto.trainee.TraineeUpdDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.mapper.trainee.TraineeGetMapper;
import com.epam.gym.app.mapper.trainee.TraineeRegMapper;
import com.epam.gym.app.mapper.trainee.TraineeTrainingMapper;
import com.epam.gym.app.mapper.trainee.TraineeUpdMapper;
import com.epam.gym.app.mapper.trainee.TraineeUserLoginMapper;
import com.epam.gym.app.mapper.trainer.TrainerListMapper;
import com.epam.gym.app.mapper.training.TrainingMapper;
import com.epam.gym.app.repository.TraineeRepository;
import com.epam.gym.app.repository.TrainerRepository;
import com.epam.gym.app.exception.NoEntityPresentException;
import com.epam.gym.app.utils.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRegMapper traineeRegMapper;
    private final TraineeGetMapper traineeGetMapper;
    private final TraineeUpdMapper traineeUpdMapper;
    private final TraineeTrainingMapper traineeTrainingMapper;
    private final TrainerListMapper trainerListMapper;
    private final TrainingMapper trainingMapper;
    private final TraineeUserLoginMapper traineeUserLoginMapper;

    @Transactional
    public UserLoginDTO save(TraineeRegDTO traineeDto) {
        log.debug("Save Trainee with first name {} and last name {}",
                traineeDto.getFirstname(), traineeDto.getLastname());

        Trainee trainee = traineeRegMapper.mapTraineeDtoToTrainee(traineeDto);
        String password = UserUtil.generateRandomPassword();
        String username = UserUtil.generateUsername(trainee.getFirstname(),
                trainee.getLastname(),
                trainerRepository.findAll(),
                traineeRepository.findAll());
        trainee.setIsActive(true);
        trainee.setPassword(password);
        trainee.setUsername(username);

        trainee = traineeRepository.save(trainee);

        log.debug("Trainee has been saved successfully");
        return traineeUserLoginMapper.mapTraineeToUserLoginDTO(trainee);
    }

    @Transactional
    public TraineeUpdDTO update(TraineeUpdDTO traineeDto) {
        log.debug("Update Trainee with first name {} and last name {}",
                traineeDto.getFirstname(), traineeDto.getLastname());

        Trainee trainee = findTrainee(traineeDto.getUsername());
        traineeUpdMapper.updateTraineeFromDTO(traineeDto, trainee);
        trainee = traineeRepository.save(trainee);

        log.debug("Trainee has been saved successfully");
        return traineeUpdMapper.mapTraineeToTraineeUpdDTO(trainee);
    }

    @Transactional
    public void delete(String username) {
        log.debug("Delete Trainee with username {}", username);
        traineeRepository.deleteByUsername(username);
        log.debug("Trainee with username {} deleted successfully", username);
    }

    @Transactional(readOnly = true)
    public TraineeGetDTO find(String username) {
        log.debug("Find Trainee with username {}", username);

        Trainee trainee = findTrainee(username);
        return traineeGetMapper.mapTraineeToTraineeGetDTO(trainee);
    }

    @Transactional(readOnly = true)
    public List<TraineeTrainingDTO> getTrainingsList(TraineeTrainingFilterDTO trainingFilterDTO) {

        String traineeUsername = trainingFilterDTO.getUsername();
        String trainerUsername = trainingFilterDTO.getTrainerUsername();
        LocalDate dateFrom = LocalDate.parse(trainingFilterDTO.getDateFrom());
        LocalDate dateTo = LocalDate.parse(trainingFilterDTO.getDateTo());
        String typeName = trainingFilterDTO.getTypeName();

        log.debug("Find Trainee's Training list with username {} and criteria: " +
                        "Trainer username {}, from date {}, to date {} and training type {}",
                traineeUsername,
                trainerUsername,
                dateFrom,
                dateTo,
                typeName);

        Trainee trainee = findTrainee(traineeUsername);

        return trainee.getTrainings()
                .stream()
                .filter(training -> training.getTrainer().getUsername().equals(trainerUsername))
                .filter(training -> training.getDate().isAfter(dateFrom) && training.getDate().isBefore(dateTo))
                .filter(training -> training.getType().getName().equals(typeName))
                .map(traineeTrainingMapper::mapTrainingToTrainingDTO)
                .toList();
    }

    @Transactional
    public List<TrainerListDTO> updateTraineeTrainerList(TraineeTrainerListDTO traineeDto) {
        log.debug("Update Trainee's with username {} Trainer List", traineeDto.getUsername());

        Trainee trainee = findTrainee(traineeDto.getUsername());
        List<Trainer> trainers = trainerRepository.findAllByUsernameIn(traineeDto.getTrainersUsernames());

        trainee.addAllTrainers(trainers);
        traineeRepository.save(trainee);

        log.debug("Trainee's with username {} Trainer list updated successfully", traineeDto.getUsername());

        return trainee.getTrainers()
                .stream()
                .map(trainerListMapper::mapTrainerToTrainerListDTO)
                .toList();
    }

    @Transactional
    public void activateDeactivate(String username, boolean isActive) {
        log.debug("Change isActive on {} for Trainee with username {}", isActive, username);
        Trainee trainee = findTrainee(username);
        trainee.setIsActive(isActive);
        traineeRepository.save(trainee);
    }

    private Trainee findTrainee(String username) {
        return traineeRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("There is no Trainee with provided username {}", username);
                    return new NoEntityPresentException("There is no Trainee with provided username: " + username);
                });
    }
}

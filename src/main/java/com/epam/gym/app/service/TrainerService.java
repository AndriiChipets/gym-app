package com.epam.gym.app.service;

import com.epam.gym.app.dto.trainer.TrainerGetDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.trainer.TrainerRegDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingFilterDTO;
import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.mapper.trainer.TrainerGetMapper;
import com.epam.gym.app.mapper.trainer.TrainerListMapper;
import com.epam.gym.app.mapper.trainer.TrainerRegMapper;
import com.epam.gym.app.mapper.trainer.TrainerTrainingMapper;
import com.epam.gym.app.mapper.trainer.TrainerUpdMapper;
import com.epam.gym.app.mapper.trainer.TrainerUserLoginMapper;
import com.epam.gym.app.mapper.training.TrainingMapper;
import com.epam.gym.app.repository.TraineeRepository;
import com.epam.gym.app.repository.TrainerRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
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
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerGetMapper trainerGetMapper;
    private final TrainerRegMapper trainerRetMapper;
    private final TrainerUpdMapper trainerUpdMapper;
    private final TrainerListMapper trainerListMapper;
    private final TrainerTrainingMapper trainerTrainingMapper;
    private final TrainingMapper trainingMapper;
    private final TrainerUserLoginMapper trainerUserLoginMapper;

    @Transactional
    public UserLoginDTO save(TrainerRegDTO trainerDto) {
        log.debug("Save Trainer with first name {} and last name {}",
                trainerDto.getFirstname(), trainerDto.getLastname());

        Trainer trainer = trainerRetMapper.mapTrainerDtoToTrainer(trainerDto);
        String password = UserUtil.generateRandomPassword();
        String username = UserUtil.generateUsername(trainer.getFirstname(),
                trainer.getLastname(),
                trainerRepository.findAll(),
                traineeRepository.findAll());
        trainer.setIsActive(true);
        trainer.setPassword(password);
        trainer.setUsername(username);

        trainer = trainerRepository.save(trainer);

        log.debug("Trainer has been saved successfully");
        return trainerUserLoginMapper.mapTrainerToUserLoginDTO(trainer);
    }

    @Transactional(readOnly = true)
    public TrainerGetDTO find(String username) {
        log.debug("Find Trainer with username {}", username);

        Trainer trainer = findTrainer(username);
        return trainerGetMapper.mapTrainerToTrainerGetDTO(trainer);
    }

    @Transactional
    public TrainerUpdDTO update(TrainerUpdDTO trainerDto) {
        log.debug("Update Trainer with first name {} and last name {}",
                trainerDto.getFirstname(), trainerDto.getLastname());

        Trainer trainer = findTrainer(trainerDto.getUsername());
        trainerUpdMapper.updateTrainerFromDTO(trainerDto, trainer);
        trainer = trainerRepository.save(trainer);

        log.debug("Trainee has been saved successfully");
        return trainerUpdMapper.mapTrainerToTrainerUpdDTO(trainer);
    }

    @Transactional(readOnly = true)
    public List<TrainerTrainingDTO> getTrainingsList(TrainerTrainingFilterDTO trainingFilterDTO) {

        String trainerUsername = trainingFilterDTO.getUsername();
        String traineeUsername = trainingFilterDTO.getTraineeUsername();
        LocalDate dateFrom = LocalDate.parse(trainingFilterDTO.getDateFrom());
        LocalDate dateTo = LocalDate.parse(trainingFilterDTO.getDateTo());

        log.debug("Find Trainer's Training list with username {} and criteria: " +
                        "Trainer username {}, from date {}, to date {}",
                traineeUsername,
                trainerUsername,
                dateFrom,
                dateTo);

        Trainer trainer = findTrainer(trainerUsername);

        return trainer.getTrainings()
                .stream()
                .filter(training -> training.getTrainee().getUsername().equals(traineeUsername))
                .filter(training -> training.getDate().isAfter(dateFrom) && training.getDate().isBefore(dateTo))
                .map(trainerTrainingMapper::mapTrainingToTrainingDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TrainerListDTO> getTrainersListNotAssignedOnTrainee(String traineeUsername) {
        log.debug("Find Trainer list not assign on Trainee with username {}", traineeUsername);

        return trainerRepository.findAllNotAssignedOnTrainee(traineeUsername)
                .stream()
                .map(trainerListMapper::mapTrainerToTrainerListDTO)
                .toList();
    }

    @Transactional
    public void activateDeactivate(String username, boolean isActive) {
        log.debug("Change isActive on {} for Trainer with username {}", isActive, username);
        Trainer trainer = findTrainer(username);
        trainer.setIsActive(isActive);
        trainerRepository.save(trainer);
    }

    private Trainer findTrainer(String username) {
        return trainerRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("There is no Trainer with provided username {}", username);
                    return new NoEntityPresentException("There is no Trainer with provided username: " + username);
                });
    }
}

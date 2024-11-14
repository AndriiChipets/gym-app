package com.epam.gym.app.service;

import com.epam.gym.app.dto.trainer.TrainerGetDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.trainer.TrainerRegDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingFilterDTO;
import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.mapper.trainer.TrainerGetMapper;
import com.epam.gym.app.mapper.trainer.TrainerListMapper;
import com.epam.gym.app.mapper.trainer.TrainerRegMapper;
import com.epam.gym.app.mapper.trainer.TrainerTrainingMapper;
import com.epam.gym.app.mapper.trainer.TrainerUpdMapper;
import com.epam.gym.app.repository.TraineeRepository;
import com.epam.gym.app.repository.TrainerRepository;
import com.epam.gym.app.exception.NoEntityPresentException;
import com.epam.gym.app.utils.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final TrainerRegMapper trainerRegMapper;
    private final TrainerUpdMapper trainerUpdMapper;
    private final TrainerListMapper trainerListMapper;
    private final TrainerTrainingMapper trainerTrainingMapper;
    private final PasswordEncoder encoder;

    @Transactional
    public UserLoginDTO save(TrainerRegDTO trainerDto) {
        log.debug("Save Trainer with first name {} and last name {}",
                trainerDto.getFirstname(), trainerDto.getLastname());

        Trainer trainer = trainerRegMapper.mapTrainerDtoToTrainer(trainerDto);
        String password = UserUtil.generateRandomPassword();
        String encryptedPassword = encoder.encode(password);
        String username = UserUtil.generateUsername(trainer.getFirstname(),
                trainer.getLastname(),
                trainerRepository.findAll(),
                traineeRepository.findAll());
        trainer.setIsActive(true);
        trainer.setPassword(encryptedPassword);
        trainer.setUsername(username);

        trainer = trainerRepository.save(trainer);

        log.debug("Trainer has been saved successfully");
        return UserLoginDTO.builder()
                .username(trainer.getUsername())
                .password(password)
                .build();
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
    public TrainerGetDTO find(String username) {
        log.debug("Find Trainer with username {}", username);

        Trainer trainer = findTrainer(username);
        return trainerGetMapper.mapTrainerToTrainerGetDTO(trainer);
    }

    @Transactional(readOnly = true)
    public List<TrainerTrainingDTO> getTrainingsList(TrainerTrainingFilterDTO trainingFilterDTO) {

        String trainerUsername = trainingFilterDTO.getUsername();
        String traineeUsername = trainingFilterDTO.getTraineeUsername();
        LocalDate dateFrom = trainingFilterDTO.getDateFrom() == null ? null :
                LocalDate.parse(trainingFilterDTO.getDateFrom());
        LocalDate dateTo = trainingFilterDTO.getDateTo() == null ? null :
                LocalDate.parse(trainingFilterDTO.getDateTo());

        log.debug("Find Trainer's Training list with username {} and criteria: " +
                        "Trainer username {}, from date {}, to date {}",
                traineeUsername,
                trainerUsername,
                dateFrom,
                dateTo);

        List<Training> trainings = trainerRepository.getFilteredTrainings(
                trainerUsername, traineeUsername, dateFrom, dateTo);

        return trainings.stream().
                map(trainerTrainingMapper::mapTrainingToTrainingDTO)
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

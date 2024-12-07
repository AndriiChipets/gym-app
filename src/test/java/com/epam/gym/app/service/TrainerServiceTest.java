package com.epam.gym.app.service;

import com.epam.gym.app.dto.trainer.TrainerGetDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.trainer.TrainerRegDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingFilterDTO;
import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.dto.user.AuthResponse;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.exception.NoEntityPresentException;
import com.epam.gym.app.mapper.trainer.TrainerGetMapper;
import com.epam.gym.app.mapper.trainer.TrainerListMapper;
import com.epam.gym.app.mapper.trainer.TrainerRegMapper;
import com.epam.gym.app.mapper.trainer.TrainerTrainingMapper;
import com.epam.gym.app.mapper.trainer.TrainerUpdMapper;
import com.epam.gym.app.repository.RolesRepository;
import com.epam.gym.app.repository.TokenRepository;
import com.epam.gym.app.repository.TraineeRepository;
import com.epam.gym.app.repository.TrainerRepository;
import com.epam.gym.app.security.JwtService;
import com.epam.gym.app.utils.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TrainerService.class})
@DisplayName("TrainerServiceTest")
class TrainerServiceTest {

    @MockBean
    TrainerRepository trainerRepository;

    @MockBean
    TraineeRepository traineeRepository;

    @MockBean
    RolesRepository rolesRepository;

    @MockBean
    TrainerGetMapper trainerGetMapper;

    @MockBean
    TrainerRegMapper trainerRegMapper;

    @MockBean
    TrainerUpdMapper trainerUpdMapper;

    @MockBean
    TrainerListMapper trainerListMapper;

    @MockBean
    TrainerTrainingMapper trainerTrainingMapper;

    @MockBean
    PasswordEncoder encoder;

    @MockBean
    JwtService jwtService;

    @MockBean
    TokenRepository tokenRepository;

    @Autowired
    TrainerService trainerService;

    @Test
    @DisplayName("save() method should return saved Trainer when saving is successful")
    void save_shouldReturnTrainerWhenSavingIsSuccessful() {

        String username = "firstname.lastname";
        String password = "123456789";
        Trainer trainer = Trainer.builder().build();
        TrainerRegDTO trainerDto = TrainerRegDTO.builder().build();

        try (MockedStatic<UserUtil> utilClassMockedStatic = mockStatic(UserUtil.class)) {
            when(trainerRegMapper.mapTrainerDtoToTrainer(any(TrainerRegDTO.class))).thenReturn(trainer);
            utilClassMockedStatic.when(UserUtil::generateRandomPassword).thenReturn(password);
            utilClassMockedStatic.when(() -> UserUtil.generateUsername(
                    anyString(), anyString(), anyList(), anyList())).thenReturn(username);
            when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);
        }
        AuthResponse actual = trainerService.save(trainerDto);

        assertNotNull(actual);
        verify(trainerRepository).save(trainer);
    }

    @Test
    @DisplayName("update() method should return updated Trainer when update is successful")
    void update_shouldReturnUpdatedTrainerWhenUpdatingIsSuccessful() {

        String username = "firstname.lastname";
        Trainer trainer = Trainer.builder().username(username).build();
        TrainerUpdDTO trainerDto = TrainerUpdDTO.builder().username(username).build();

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);
        when(trainerUpdMapper.mapTrainerToTrainerUpdDTO(any(Trainer.class))).thenReturn(trainerDto);

        TrainerUpdDTO actual = trainerService.update(trainerDto);

        assertNotNull(actual);
        assertEquals(trainerDto, actual);
        verify(trainerRepository).save(trainer);
    }

    @Test
    @DisplayName("find() method should return Trainer when Trainer is present")
    void find_shouldReturnTrainer_whenTrainerPresent() {

        String username = "firstname.lastname";
        Trainer trainer = Trainer.builder().build();
        TrainerGetDTO expected = TrainerGetDTO.builder().build();

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerGetMapper.mapTrainerToTrainerGetDTO(any(Trainer.class))).thenReturn(expected);
        TrainerGetDTO actual = trainerService.find(username);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainerRepository).findByUsername(username);
    }

    @Test
    @DisplayName("find() method should throw NoEntityPresentException when Trainer isn't present")
    void find_shouldThrowNoEntityPresentException_whenTrainerIsNotPresent() {

        String username = "fake.username";

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoEntityPresentException.class,
                () -> trainerService.find(username));

        assertEquals("There is no Trainer with provided username: " + username, exception.getMessage());
        verify(trainerRepository, times(1)).findByUsername(username);
    }


    @Test
    @DisplayName("getTrainingsList() method should return List of Trainer's trainings by criteria when criteria is present")
    void getTrainingsList_shouldReturnListOfTrainerTrainingsByCriteria_WhenCriteriaIsPresent() {

        String trainerUsername = "trainer.username";
        String traineeUsername = "trainee.username";
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(10);

        Training training = Training.builder().build();
        List<Training> trainings = List.of(training, training, training);

        TrainerTrainingDTO trainerTrainingDTO = TrainerTrainingDTO.builder().build();
        TrainerTrainingFilterDTO filterDTO = TrainerTrainingFilterDTO
                .builder()
                .username(trainerUsername)
                .traineeUsername(traineeUsername)
                .dateFrom(dateFrom.toString())
                .dateTo(dateTo.toString())
                .build();

        when(trainerRepository.getFilteredTrainings(
                anyString(), anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(trainings);
        when(trainerTrainingMapper.mapTrainingToTrainingDTO(any(Training.class))).thenReturn(trainerTrainingDTO);

        List<TrainerTrainingDTO> actual = trainerService.getTrainingsList(filterDTO);

        assertNotNull(actual);
        verify(trainerRepository).getFilteredTrainings(
                trainerUsername, traineeUsername, dateFrom, dateTo);
    }

    @Test
    @DisplayName("getTrainingsList() method should return List of all Trainer's trainings when criteria is not present")
    void getTrainingsList_shouldReturnListOfAllTrainerTrainings_WhenCriteriaIsNotPresent() {

        String trainerUsername = "trainer.username";

        Training training = Training.builder().build();
        List<Training> trainings = List.of(training, training, training);

        TrainerTrainingDTO trainerTrainingDTO = TrainerTrainingDTO.builder().build();
        TrainerTrainingFilterDTO filterDTO = TrainerTrainingFilterDTO
                .builder()
                .username(trainerUsername)
                .build();

        when(trainerRepository.getFilteredTrainings(
                anyString(), anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(trainings);
        when(trainerTrainingMapper.mapTrainingToTrainingDTO(any(Training.class))).thenReturn(trainerTrainingDTO);

        List<TrainerTrainingDTO> actual = trainerService.getTrainingsList(filterDTO);

        assertNotNull(actual);
        verify(trainerRepository).getFilteredTrainings(
                trainerUsername, null, null, null);
    }

    @Test
    @DisplayName("getTrainersListNotAssignedOnTrainee() method should return List of Trainers not assigned on Trainee")
    void getTrainersListNotAssignedOnTrainee_shouldReturnListOfTrainersNotAssignedOnTrainee() {

        String traineeUsername = "trainee.username";
        Trainer trainer = Trainer.builder().build();
        TrainerListDTO trainerListDTO = TrainerListDTO.builder().build();
        List<Trainer> trainers = List.of(trainer, trainer, trainer);
        List<TrainerListDTO> expected = List.of(trainerListDTO, trainerListDTO, trainerListDTO);

        when(trainerRepository.findAllNotAssignedOnTrainee(anyString())).thenReturn(trainers);
        when(trainerListMapper.mapTrainerToTrainerListDTO(any(Trainer.class))).thenReturn(trainerListDTO);

        List<TrainerListDTO> actual = trainerService.getTrainersListNotAssignedOnTrainee(traineeUsername);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainerRepository).findAllNotAssignedOnTrainee(traineeUsername);
    }

    @Test
    @DisplayName("activateDeactivate() method should activate Trainer's profile when isActive true")
    void activateDeactivate_shouldActivateTrainerProfile_whenIsActiveTrue() {

        String username = "firstname.lastname";
        boolean isActive = true;
        Trainer trainer = Trainer.builder().username(username).isActive(isActive).build();

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        trainerService.activateDeactivate(username, isActive);

        assertTrue(trainer.getIsActive());
        verify(trainerRepository, times(1)).save(trainer);
    }
}

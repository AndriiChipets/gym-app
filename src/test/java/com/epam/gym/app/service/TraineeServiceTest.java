package com.epam.gym.app.service;

import com.epam.gym.app.dto.trainee.TraineeGetDTO;
import com.epam.gym.app.dto.trainee.TraineeRegDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainerListDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingFilterDTO;
import com.epam.gym.app.dto.trainee.TraineeUpdDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.user.AuthResponse;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.exception.NoEntityPresentException;
import com.epam.gym.app.mapper.trainee.TraineeGetMapper;
import com.epam.gym.app.mapper.trainee.TraineeRegMapper;
import com.epam.gym.app.mapper.trainee.TraineeTrainingMapper;
import com.epam.gym.app.mapper.trainee.TraineeUpdMapper;
import com.epam.gym.app.mapper.trainer.TrainerListMapper;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TraineeService.class})
@DisplayName("TraineeServiceTest")
class TraineeServiceTest {

    @MockBean
    TraineeRepository traineeRepository;

    @MockBean
    TrainerRepository trainerRepository;

    @MockBean
    RolesRepository rolesRepository;

    @MockBean
    TraineeRegMapper traineeRegMapper;

    @MockBean
    TraineeGetMapper traineeGetMapper;

    @MockBean
    TraineeUpdMapper traineeUpdMapper;

    @MockBean
    TraineeTrainingMapper traineeTrainingMapper;

    @MockBean
    TrainerListMapper trainerListMapper;

    @MockBean
    PasswordEncoder encoder;

    @MockBean
    JwtService jwtService;

    @MockBean
    TokenRepository tokenRepository;

    @Autowired
    TraineeService traineeService;

    @Test
    @DisplayName("save() method should return saved Trainee when saving is successful")
    void save_shouldReturnTraineeWhenSavingIsSuccessful() {

        String username = "firstname.lastname";
        String password = "123456789";
        Trainee trainee = Trainee.builder().build();
        TraineeRegDTO traineeDto = TraineeRegDTO.builder().build();

        try (MockedStatic<UserUtil> utilClassMockedStatic = mockStatic(UserUtil.class)) {
            when(traineeRegMapper.mapTraineeDtoToTrainee(any(TraineeRegDTO.class))).thenReturn(trainee);
            utilClassMockedStatic.when(UserUtil::generateRandomPassword).thenReturn(password);
            utilClassMockedStatic.when(() -> UserUtil.generateUsername(
                    anyString(), anyString(), anyList(), anyList())).thenReturn(username);
            when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);
        }
        AuthResponse actual = traineeService.save(traineeDto);

        assertNotNull(actual);
        verify(traineeRepository).save(trainee);
    }

    @Test
    @DisplayName("update() method should return updated Trainee when update is successful")
    void update_shouldReturnUpdatedTraineeWhenUpdatingIsSuccessful() {

        String username = "firstname.lastname";
        Trainee trainee = Trainee.builder().username(username).build();
        TraineeUpdDTO traineeDto = TraineeUpdDTO.builder().username(username).build();

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);
        when(traineeUpdMapper.mapTraineeToTraineeUpdDTO(any(Trainee.class))).thenReturn(traineeDto);

        TraineeUpdDTO actual = traineeService.update(traineeDto);

        assertNotNull(actual);
        assertEquals(traineeDto, actual);
        verify(traineeRepository).save(trainee);
    }

    @Test
    @DisplayName("delete() method should delete Trainee")
    void delete_shouldDeleteTrainee() {
        String username = "firstname.lastname";
        traineeService.delete(username);
        verify(traineeRepository).deleteByUsername(username);
    }

    @Test
    @DisplayName("find() method should return Trainee when Trainee is present")
    void find_shouldReturnTrainee_whenTraineePresent() {

        String username = "firstname.lastname";
        Trainee trainee = Trainee.builder().build();
        TraineeGetDTO expected = TraineeGetDTO.builder().build();

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(traineeGetMapper.mapTraineeToTraineeGetDTO(any(Trainee.class))).thenReturn(expected);
        TraineeGetDTO actual = traineeService.find(username);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(traineeRepository).findByUsername(username);
    }

    @Test
    @DisplayName("find() method should throw NoEntityPresentException when Trainee isn't present")
    void find_shouldThrowNoEntityPresentException_whenTraineeIsNotPresent() {

        String username = "fake.username";

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoEntityPresentException.class,
                () -> traineeService.find(username));

        assertEquals("There is no Trainee with provided username: " + username, exception.getMessage());
        verify(traineeRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("getTrainingsList() method should return List of Trainee's trainings by criteria when criteria is present")
    void getTrainingsList_shouldReturnListOfTraineeTrainingsByCriteria_WhenCriteriaIsPresent() {

        String traineeUsername = "trainee.username";
        String trainerUsername = "trainer.username";
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(10);
        String typeName = "Training type";

        Training training = Training.builder().build();
        List<Training> trainings = List.of(training, training, training);

        TraineeTrainingDTO traineeTrainingDTO = TraineeTrainingDTO.builder().build();
        TraineeTrainingFilterDTO filterDTO = TraineeTrainingFilterDTO
                .builder()
                .username(traineeUsername)
                .trainerUsername(trainerUsername)
                .dateFrom(dateFrom.toString())
                .dateTo(dateTo.toString())
                .typeName(typeName)
                .build();

        when(traineeRepository.getFilteredTrainings(
                anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString()))
                .thenReturn(trainings);
        when(traineeTrainingMapper.mapTrainingToTrainingDTO(any(Training.class))).thenReturn(traineeTrainingDTO);

        List<TraineeTrainingDTO> actual = traineeService.getTrainingsList(filterDTO);

        assertNotNull(actual);
        verify(traineeRepository).getFilteredTrainings(
                traineeUsername, trainerUsername, dateFrom, dateTo, typeName);
    }

    @Test
    @DisplayName("getTrainingsList() method should return List of all Trainee's trainings when criteria is not present")
    void getTrainingsList_shouldReturnListOfAllTraineeTrainings_WhenCriteriaIsNotPresent() {

        String traineeUsername = "trainee.username";

        Training training = Training.builder().build();
        List<Training> trainings = List.of(training, training, training);

        TraineeTrainingDTO traineeTrainingDTO = TraineeTrainingDTO.builder().build();
        TraineeTrainingFilterDTO filterDTO = TraineeTrainingFilterDTO
                .builder()
                .username(traineeUsername)
                .build();

        when(traineeRepository.getFilteredTrainings(
                anyString(), anyString(), any(LocalDate.class), any(LocalDate.class), anyString()))
                .thenReturn(trainings);
        when(traineeTrainingMapper.mapTrainingToTrainingDTO(any(Training.class))).thenReturn(traineeTrainingDTO);

        List<TraineeTrainingDTO> actual = traineeService.getTrainingsList(filterDTO);

        assertNotNull(actual);
        verify(traineeRepository).getFilteredTrainings(
                traineeUsername, null, null, null, null);
    }

    @Test
    @DisplayName("updateTraineeTrainerList() method should update Trainee's trainers list")
    void updateTraineeTrainerList_shouldUpdateTraineeTrainerList() {

        String traineeUsername = "firstname.lastname";
        TraineeTrainerListDTO traineeTrainerListDto = TraineeTrainerListDTO
                .builder().username(traineeUsername).build();
        Trainee trainee = Trainee.builder().username(traineeUsername).build();
        TrainerListDTO trainerListDTO = TrainerListDTO.builder().build();

        List<Trainer> trainers = new ArrayList<>();
        Set<String> trainersUsernames = new HashSet<>();
        traineeTrainerListDto.setTrainersUsernames(trainersUsernames);

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findAllByUsernameIn(any(Set.class))).thenReturn(trainers);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);
        when(trainerListMapper.mapTrainerToTrainerListDTO(any(Trainer.class))).thenReturn(trainerListDTO);

        List<TrainerListDTO> actual = traineeService.updateTraineeTrainerList(traineeTrainerListDto);

        assertNotNull(actual);
        verify(traineeRepository).save(trainee);
    }

    @Test
    @DisplayName("activateDeactivate() method should activate Trainee's profile when isActive true")
    void activateDeactivate_shouldActivateTraineeProfile_whenIsActiveTrue() {

        String username = "firstname.lastname";
        boolean isActive = true;
        Trainee trainee = Trainee.builder().username(username).isActive(isActive).build();

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        traineeService.activateDeactivate(username, isActive);

        assertTrue(trainee.getIsActive());
        verify(traineeRepository, times(1)).save(trainee);
    }
}

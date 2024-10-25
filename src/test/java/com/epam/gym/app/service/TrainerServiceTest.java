package com.epam.gym.app.service;

import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.dto.TrainingDto;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.mapper.TrainerMapperStruct;
import com.epam.gym.app.mapper.TrainingMapperStruct;
import com.epam.gym.app.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {TrainerService.class})
@DisplayName("TrainerServiceTest")
class TrainerServiceTest {

    @MockBean
    TrainerRepository trainerRepository;

    @MockBean
    TrainerMapperStruct trainerMapper;

    @MockBean
    TrainingMapperStruct trainingMapper;

    @Autowired
    TrainerService trainerService;

    @Test
    @DisplayName("save() method should return saved Trainer when saving is successful")
    void save_shouldReturnTrainerWhenSavingIsSuccessful() {

        Trainer trainer = Trainer.builder().build();
        TrainerDto trainerDto = TrainerDto.builder().build();

        when(trainerMapper.mapTrainerDtoToTrainer(any(TrainerDto.class))).thenReturn(trainer);
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);
        when(trainerMapper.mapTrainerToTrainerDto(trainer)).thenReturn(trainerDto);
        TrainerDto actual = trainerService.save(trainerDto);

        assertNotNull(actual);
        verify(trainerRepository).save(trainer);
    }

    @Test
    @DisplayName("find() method should return Trainer when Trainer is present")
    void find_shouldReturnTrainer_whenTrainerPresent() {

        String username = "firstname.lastname";
        Trainer trainer = Trainer.builder().build();
        TrainerDto expected = TrainerDto.builder().username(username).build();

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerMapper.mapTrainerToTrainerDto(any(Trainer.class))).thenReturn(expected);
        TrainerDto actual = trainerService.find(username);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainerRepository).findByUsername(username);
    }

    @Test
    @DisplayName("find() method should throw NoEntityPresentException when Trainer isn't present")
    void find_shouldThrowNoEntityPresentException_whenTrainerIsNotPresent() {

        String username = "fake.username";

        when(trainerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoEntityPresentException.class,
                () -> trainerService.find(username));

        assertEquals("There is no Trainer with provided username: " + username, exception.getMessage());
        verify(trainerRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("findAll() method should return List of Trainers when Trainers present")
    void findAll_shouldReturnListTrainers_whenTrainersPresent() {

        Trainer trainer = Trainer.builder().build();
        TrainerDto trainerDto = TrainerDto.builder().build();
        List<Trainer> trainers = List.of(trainer, trainer, trainer);
        List<TrainerDto> expected = List.of(trainerDto, trainerDto, trainerDto);

        when(trainerRepository.findAll()).thenReturn(trainers);
        when(trainerMapper.mapTrainerToTrainerDto(any(Trainer.class))).thenReturn(trainerDto);
        List<TrainerDto> actual = trainerService.findAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainerRepository).findAll();
    }

    @Test
    @DisplayName("login() method should return true when username or password exists is present")
    void login_shouldReturnTrue_whenUsernameOrPasswordPresent() {

        String username = "firstname.lastname";
        String password = "valid password";

        when(trainerRepository.existsByUsernameAndPassword(anyString(), anyString())).thenReturn(true);
        boolean isExists = trainerService.login(username, password);

        assertTrue(isExists);
        verify(trainerRepository).existsByUsernameAndPassword(username, password);
    }

    @Test
    @DisplayName("login() method should return false when username or password exists is absent")
    void login_shouldReturnTrue_whenUsernameOrPasswordAbsent() {

        String username = "firstname.lastname";
        String password = "invalid password";

        when(trainerRepository.existsByUsernameAndPassword(anyString(), anyString())).thenReturn(false);
        boolean isExists = trainerService.login(username, password);

        assertFalse(isExists);
        verify(trainerRepository).existsByUsernameAndPassword(username, password);
    }

    @Test
    @DisplayName("getTrainingsList() method should return List of Trainer's trainings")
    void getTrainingsList_shouldReturnListOfTrainerTrainings() {

        String trainerUsername = "trainer.username";
        String traineeUsername = "trainee.username";
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(10);
        Trainee trainee = Trainee.builder().username(traineeUsername).build();
        Training training = Training.builder().trainee(trainee).date(dateFrom.minusDays(10)).build();
        List<Training> trainings = List.of(training, training, training);

        Trainer trainer = Trainer.builder().build();
        trainer.getTrainings().addAll(trainings);

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainingMapper.mapTrainingToTrainingDto(any(Training.class))).thenReturn(new TrainingDto());

        List<TrainingDto> actual = trainerService.getTrainingsList(trainerUsername, dateFrom, dateTo, traineeUsername);

        assertNotNull(actual);
        verify(trainerRepository).findByUsername(trainerUsername);
    }

    @Test
    @DisplayName("getTrainersListNotAssignedOnTrainee() method should return List of Trainers not assigned on Trainee")
    void getTrainersListNotAssignedOnTrainee_shouldReturnListOfTrainersNotAssignedOnTrainee() {

        String traineeUsername = "trainee.username";
        Trainer trainer = Trainer.builder().build();
        TrainerDto trainerDto = TrainerDto.builder().build();
        List<Trainer> trainers = List.of(trainer, trainer, trainer);
        List<TrainerDto> expected = List.of(trainerDto, trainerDto, trainerDto);

        when(trainerRepository.findAllNotAssignedOnTrainee(anyString())).thenReturn(trainers);
        when(trainerMapper.mapTrainerToTrainerDto(any(Trainer.class))).thenReturn(trainerDto);

        List<TrainerDto> actual = trainerService.getTrainersListNotAssignedOnTrainee(traineeUsername);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainerRepository).findAllNotAssignedOnTrainee(traineeUsername);
    }
}

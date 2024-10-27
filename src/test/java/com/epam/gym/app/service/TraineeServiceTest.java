package com.epam.gym.app.service;

import com.epam.gym.app.dto.TraineeDto;
import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.dto.TrainingDTO;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.mapper.TraineeMapperStruct;
import com.epam.gym.app.mapper.TrainerMapperStruct;
import com.epam.gym.app.mapper.TrainingMapperStruct;
import com.epam.gym.app.repository.TraineeRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {TraineeService.class})
@DisplayName("TraineeServiceTest")
class TraineeServiceTest {

    @MockBean
    TraineeRepository traineeRepository;

    @MockBean
    TraineeMapperStruct traineeMapper;

    @MockBean
    TrainerMapperStruct trainerMapper;

    @MockBean
    TrainingMapperStruct trainingMapper;

    @Autowired
    TraineeService traineeService;

    @Test
    @DisplayName("save() method should return saved Trainee when saving is successful")
    void save_shouldReturnTraineeWhenSavingIsSuccessful() {

        Trainee trainee = Trainee.builder().build();
        TraineeDto traineeDto = TraineeDto.builder().build();

        when(traineeMapper.mapTraineeDtoToTrainee(any(TraineeDto.class))).thenReturn(trainee);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);
        when(traineeMapper.mapTraineeToTraineeDto(trainee)).thenReturn(traineeDto);
        TraineeDto actual = traineeService.save(traineeDto);

        assertNotNull(actual);
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
        TraineeDto expected = TraineeDto.builder().username(username).build();

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(traineeMapper.mapTraineeToTraineeDto(any(Trainee.class))).thenReturn(expected);
        TraineeDto actual = traineeService.find(username);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(traineeRepository).findByUsername(username);
    }

    @Test
    @DisplayName("find() method should throw NoEntityPresentException when Trainee isn't present")
    void find_shouldThrowNoEntityPresentException_whenTraineeIsNotPresent() {

        String username = "fake.username";

        when(traineeRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoEntityPresentException.class,
                () -> traineeService.find(username));

        assertEquals("There is no Trainee with provided username: " + username, exception.getMessage());
        verify(traineeRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("findAll() method should return List of Trainees when Trainees present")
    void findAll_shouldReturnListTrainees_whenTraineesPresent() {

        Trainee trainee = Trainee.builder().build();
        TraineeDto traineeDto = TraineeDto.builder().build();
        List<Trainee> trainees = List.of(trainee, trainee, trainee);
        List<TraineeDto> expected = List.of(traineeDto, traineeDto, traineeDto);

        when(traineeRepository.findAll()).thenReturn(trainees);
        when(traineeMapper.mapTraineeToTraineeDto(any(Trainee.class))).thenReturn(traineeDto);
        List<TraineeDto> actual = traineeService.findAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(traineeRepository).findAll();
    }

    @Test
    @DisplayName("login() method should return true when username or password exists is present")
    void login_shouldReturnTrue_whenUsernameOrPasswordPresent() {

        String username = "firstname.lastname";
        String password = "valid password";

        when(traineeRepository.existsByUsernameAndPassword(anyString(), anyString())).thenReturn(true);
        boolean isExists = traineeService.login(username, password);

        assertTrue(isExists);
        verify(traineeRepository).existsByUsernameAndPassword(username, password);
    }

    @Test
    @DisplayName("login() method should return false when username or password exists is absent")
    void login_shouldReturnTrue_whenUsernameOrPasswordAbsent() {

        String username = "firstname.lastname";
        String password = "invalid password";

        when(traineeRepository.existsByUsernameAndPassword(anyString(), anyString())).thenReturn(false);
        boolean isExists = traineeService.login(username, password);

        assertFalse(isExists);
        verify(traineeRepository).existsByUsernameAndPassword(username, password);
    }

    @Test
    @DisplayName("getTrainingsList() method should return List of Trainee's trainings")
    void getTrainingsList_shouldReturnListOfTraineeTrainings() {

        String traineeUsername = "trainee.username";
        String trainerUsername = "trainer.username";
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(10);
        Trainer trainer = Trainer.builder().username(trainerUsername).build();
        Training training = Training.builder().trainer(trainer).date(dateFrom.minusDays(10)).build();
        List<Training> trainings = List.of(training, training, training);

        Trainee trainee = Trainee.builder().build();
        trainee.getTrainings().addAll(trainings);

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(trainingMapper.mapTrainingToTrainingDto(any(Training.class))).thenReturn(new TrainingDTO());

        List<TrainingDTO> actual = traineeService.getTrainingsList(traineeUsername, dateFrom, dateTo, trainerUsername);

        assertNotNull(actual);
        verify(traineeRepository).findByUsername(traineeUsername);
    }

    @Test
    @DisplayName("addTrainerToTraineeList() method should add Trainer to Trainee's trainers list")
    void addTrainerToTraineeList_shouldAddTrainerToTraineeTrainerList() {

        TrainerDto trainerDto = TrainerDto.builder().build();
        TraineeDto traineeDto = TraineeDto.builder().build();
        Trainer trainer = Trainer.builder().build();
        Trainee trainee = Trainee.builder().build();

        List<Trainer> trainers = List.of(trainer, trainer, trainer);
        trainee.getTrainers().addAll(trainers);

        when(trainerMapper.mapTrainerDtoToTrainer(any(TrainerDto.class))).thenReturn(trainer);
        when(trainerMapper.mapTrainerToTrainerDto(any(Trainer.class))).thenReturn(trainerDto);
        when(traineeMapper.mapTraineeDtoToTrainee(any(TraineeDto.class))).thenReturn(trainee);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        List<TrainerDto> actual = traineeService.addTrainerToTraineeList(traineeDto, trainerDto);

        assertNotNull(actual);
        verify(traineeRepository).save(trainee);
    }

    @Test
    @DisplayName("removeTrainerToTraineeList() method should remove Trainer from Trainee's trainers list")
    void removeTrainerToTraineeList_shouldRemoveTrainerFromTraineeTrainerList() {

        TrainerDto trainerDto = TrainerDto.builder().build();
        TraineeDto traineeDto = TraineeDto.builder().build();
        Trainer trainer = Trainer.builder().build();
        Trainee trainee = Trainee.builder().build();

        List<Trainer> trainers = List.of(trainer, trainer, trainer);
        trainee.getTrainers().addAll(trainers);

        when(trainerMapper.mapTrainerDtoToTrainer(any(TrainerDto.class))).thenReturn(trainer);
        when(trainerMapper.mapTrainerToTrainerDto(any(Trainer.class))).thenReturn(trainerDto);
        when(traineeMapper.mapTraineeDtoToTrainee(any(TraineeDto.class))).thenReturn(trainee);
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);

        List<TrainerDto> actual = traineeService.removeTrainerToTraineeList(traineeDto, trainerDto);

        assertNotNull(actual);
        verify(traineeRepository).save(trainee);
    }
}

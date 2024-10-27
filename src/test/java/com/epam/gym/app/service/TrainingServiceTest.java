package com.epam.gym.app.service;

import com.epam.gym.app.dto.TrainingDTO;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.mapper.TrainingMapperStruct;
import com.epam.gym.app.repository.TrainingRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TrainingService.class})
@DisplayName("TrainingServiceTest")
class TrainingServiceTest {

    @MockBean
    TrainingRepository trainingRepository;

    @MockBean
    TrainingMapperStruct trainingMapper;

    @Autowired
    TrainingService trainingService;

    @Test
    @DisplayName("save() method should return saved Training when saving is successful")
    void save_shouldReturnTrainingWhenSavingIsSuccessful() {

        Training training = Training.builder().build();
        TrainingDTO trainingDto = TrainingDTO.builder().build();

        when(trainingMapper.mapTrainingDtoToTraining(any(TrainingDTO.class))).thenReturn(training);
        when(trainingRepository.save(any(Training.class))).thenReturn(training);
        when(trainingMapper.mapTrainingToTrainingDto(any(Training.class))).thenReturn(trainingDto);
        TrainingDTO actual = trainingService.save(trainingDto);

        assertNotNull(actual);
        verify(trainingRepository).save(training);
    }

    @Test
    @DisplayName("find() method should return Training when Training is present")
    void find_shouldReturnTraining_whenTrainingPresent() {

        long trainingId = 1L;
        Training training = Training.builder().build();
        TrainingDTO expected = TrainingDTO.builder().id(trainingId).build();

        when(trainingRepository.findById(anyLong())).thenReturn(Optional.of(training));
        when(trainingMapper.mapTrainingToTrainingDto(any(Training.class))).thenReturn(expected);
        TrainingDTO actual = trainingService.find(trainingId);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainingRepository).findById(trainingId);
    }

    @Test
    @DisplayName("find() method should throw NoEntityPresentException when Training isn't present")
    void find_shouldThrowNoEntityPresentException_whenTrainingIsNotPresent() {

        long traineeId = 1000L;

        when(trainingRepository.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoEntityPresentException.class,
                () -> trainingService.find(traineeId));

        assertEquals("There is no Training with provided id: " + traineeId, exception.getMessage());
        verify(trainingRepository, times(1)).findById(traineeId);
    }

    @Test
    @DisplayName("findAll() method should return List of Trainings when Trainings present")
    void findAll_shouldReturnListTrainings_whenTrainingsPresent() {

        Training training = Training.builder().build();
        List<Training> trainings = List.of(training, training, training);
        TrainingDTO trainingDto = TrainingDTO.builder().build();
        List<TrainingDTO> expected = List.of(trainingDto, trainingDto, trainingDto);

        when(trainingRepository.findAll()).thenReturn(trainings);
        when(trainingMapper.mapTrainingToTrainingDto(any(Training.class))).thenReturn(trainingDto);
        List<TrainingDTO> actual = trainingService.findAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainingRepository).findAll();
    }
}

package com.epam.gym.app.service;

import com.epam.gym.app.entity.Training;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TrainingService.class})
@DisplayName("TrainingServiceTest")
class TrainingServiceTest {

//    @MockBean(name = "traineeDaoTest")
//    TrainingDao trainingDao;
//
//    @Autowired
//    TrainingService trainingService;
//
//    @Test
//    @DisplayName("save() method should return saved Training when saving is successful")
//    void save_shouldReturnTrainingWhenSavingIsSuccessful() {
//
//        long trainingId = 1L;
//        Training expected = Training.builder().build();
//        Training expectedWithId = Training.builder().id(trainingId).build();
//
//        when(trainingDao.save(expected)).thenReturn(expectedWithId);
//        Training actual = trainingService.save(expected);
//
//        assertNotNull(actual);
//        verify(trainingDao).save(expected);
//    }
//
//    @Test
//    @DisplayName("save() method should throw NoEntityPresentException when saving isn't successful")
//    void save_shouldThrowNoEntityPresentExceptionWhenSavingIsNotSuccessful() {
//
//        Training expected = Training.builder().build();
//
//        when(trainingDao.save(expected)).thenReturn(null);
//        Exception exception = assertThrows(NoEntityPresentException.class,
//                () -> trainingService.save(expected));
//
//        assertEquals("Training wasn't saved successfully", exception.getMessage());
//        verify(trainingDao, times(1)).save(expected);
//    }
//
//    @Test
//    @DisplayName("find() method should return Training when Training is present")
//    void find_shouldReturnTraining_whenTrainingPresent() {
//
//        long trainingId = 1L;
//        Training expected = Training.builder().id(trainingId).build();
//
//        when(trainingDao.findById(anyLong())).thenReturn(Optional.of(expected));
//        Training actual = trainingService.find(trainingId);
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//        verify(trainingDao).findById(trainingId);
//    }
//
//    @Test
//    @DisplayName("find() method should throw NoEntityPresentException when Training isn't present")
//    void find_shouldThrowNoEntityPresentException_whenTrainingIsNotPresent() {
//
//        long traineeId = 1000L;
//
//        when(trainingDao.findById(anyLong())).thenReturn(Optional.empty());
//        Exception exception = assertThrows(NoEntityPresentException.class,
//                () -> trainingService.find(traineeId));
//
//        assertEquals("There is no Training with provided id: " + traineeId, exception.getMessage());
//        verify(trainingDao, times(1)).findById(traineeId);
//    }
//
//    @Test
//    @DisplayName("findAll() method should return List of Trainings when Trainings present")
//    void findAll_shouldReturnListTrainings_whenTrainingsPresent() {
//
//        List<Training> expected = List.of(
//                Training.builder().build(),
//                Training.builder().build(),
//                Training.builder().build());
//
//        when(trainingDao.findAll()).thenReturn(expected);
//        List<Training> actual = trainingService.findAll();
//
//        assertNotNull(actual);
//        assertEquals(expected, actual);
//        verify(trainingDao).findAll();
//    }
}

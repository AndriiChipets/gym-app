package com.epam.gym.app.service;

import com.epam.gym.app.dao.TraineeDao;
import com.epam.gym.app.entity.Trainee;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {TraineeService.class})
@DisplayName("TraineeServiceTest")
class TraineeServiceTest {

    @MockBean
    TraineeDao traineeDao;

    @Autowired
    TraineeService traineeService;

    @Test
    @DisplayName("save() method should save Trainee")
    void save_shouldSaveTrainee() {

        Trainee trainee = Trainee.builder().build();

        traineeService.save(trainee);

        verify(traineeDao).save(trainee);
    }

    @Test
    @DisplayName("update() method should update Trainee")
    void update_shouldUpdateTrainee() {

        Trainee trainee = Trainee.builder().build();

        traineeService.update(trainee);

        verify(traineeDao).update(trainee);
    }

    @Test
    @DisplayName("delete() method should delete Trainee")
    void delete_shouldDeleteTrainee() {

        long traineeId = 1L;

        traineeService.delete(traineeId);

        verify(traineeDao).deleteById(traineeId);
    }

    @Test
    @DisplayName("find() method should return Trainee when Trainee is present")
    void find_shouldReturnTrainee_whenTraineePresent() {

        long traineeId = 1L;
        Trainee expected = Trainee.builder().id(traineeId).build();

        when(traineeDao.findById(anyLong())).thenReturn(Optional.of(expected));
        Trainee actual = traineeService.find(traineeId);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(traineeDao).findById(traineeId);
    }

    @Test
    @DisplayName("find() method should throw NoEntityPresentException when Trainee isn't present")
    void find_shouldThrowNoEntityPresentException_whenTraineeIsNotPresent() {

        long traineeId = 1000L;

        when(traineeDao.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoEntityPresentException.class,
                () -> traineeService.find(traineeId));

        assertEquals("There is no Trainee with provided id: " + traineeId, exception.getMessage());
        verify(traineeDao, times(1)).findById(traineeId);
    }

    @Test
    @DisplayName("findAll() method should return List of Trainees when Trainees present")
    void findAll_shouldReturnListTrainees_whenTraineesPresent() {

        List<Trainee> expected = List.of(
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build());

        when(traineeDao.findAll()).thenReturn(expected);
        List<Trainee> actual = traineeService.findAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(traineeDao).findAll();
    }
}

package com.epam.gym.app.service;

import com.epam.gym.app.dao.TrainerDao;
import com.epam.gym.app.entity.Trainer;
import org.junit.jupiter.api.Test;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import org.junit.jupiter.api.DisplayName;
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

@SpringBootTest(classes = {TrainerService.class})
@DisplayName("TrainerServiceTest")
class TrainerServiceTest {

    @MockBean
    TrainerDao trainerDao;

    @Autowired
    TrainerService trainerService;

    @Test
    @DisplayName("save() method should save Trainer")
    void save_shouldSaveTrainer() {

        Trainer trainer = Trainer.builder().build();

        trainerService.save(trainer);

        verify(trainerDao).save(trainer);
    }

    @Test
    @DisplayName("update() method should update Trainer")
    void update_shouldUpdateTrainer() {

        Trainer trainer = Trainer.builder().build();

        trainerService.update(trainer);

        verify(trainerDao).update(trainer);
    }

    @Test
    @DisplayName("find() method should return Trainer when Trainer is present")
    void find_shouldReturnTrainer_whenTrainerPresent() {

        long trainerId = 1L;
        Trainer expected = Trainer.builder().id(trainerId).build();

        when(trainerDao.findById(anyLong())).thenReturn(Optional.of(expected));
        Trainer actual = trainerService.find(trainerId);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainerDao).findById(trainerId);
    }

    @Test
    @DisplayName("find() method should throw NoEntityPresentException when Trainer isn't present")
    void find_shouldThrowNoEntityPresentException_whenTrainerIsNotPresent() {

        long traineeId = 1000L;

        when(trainerDao.findById(anyLong())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoEntityPresentException.class,
                () -> trainerService.find(traineeId));

        assertEquals("There is no Trainer with provided id: " + traineeId, exception.getMessage());
        verify(trainerDao, times(1)).findById(traineeId);
    }

    @Test
    @DisplayName("findAll() method should return List of Trainers when Trainers present")
    void findAll_shouldReturnListTrainers_whenTrainersPresent() {

        List<Trainer> expected = List.of(
                Trainer.builder().build(),
                Trainer.builder().build(),
                Trainer.builder().build());

        when(trainerDao.findAll()).thenReturn(expected);
        List<Trainer> actual = trainerService.findAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(trainerDao).findAll();
    }
}

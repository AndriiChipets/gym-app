package com.epam.gym.app.dao.impl;

import com.epam.gym.app.config.GymAppConfig;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.entity.TrainingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = GymAppConfig.class)
@SpringBootTest(classes = {TrainingDaoImpl.class})
@DisplayName("TrainingDaoImplTest")
class TrainingDaoImplTest {

    @Autowired
    TrainingDaoImpl trainingDao;

    @BeforeEach
    public void clearStorage() {
        trainingDao.getStorage().getData().clear();
    }

    @Test
    @DisplayName("save() method should save Training to Storage")
    void save_shouldSaveTrainingToStorage() {

        long id = 1L;
        String name = "Training";
        TrainingType type = TrainingType.FITNESS;
        Trainee trainee = Trainee.builder().build();
        Trainer trainer = Trainer.builder().build();
        int duration = 30;
        Training expected = Training.builder()
                .name(name)
                .type(type)
                .trainee(trainee)
                .trainer(trainer)
                .duration(duration)
                .build();

        trainingDao.save(expected);
        Training actual = trainingDao.findById(id).get();

        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("findById() method should return Training from Storage when Training is present")
    void findById_shouldReturnTrainingFromStorage_whenTrainingIsPresent() {

        long id = 1L;
        String name = "Training";
        TrainingType type = TrainingType.FITNESS;
        Trainee trainee = Trainee.builder().build();
        Trainer trainer = Trainer.builder().build();
        int duration = 30;
        Training expected = Training.builder()
                .name(name)
                .type(type)
                .trainee(trainee)
                .trainer(trainer)
                .duration(duration)
                .build();

        trainingDao.save(expected);
        Training actual = trainingDao.findById(id).get();

        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("findAll() method should return List of Trainings from Store")
    void findAll_shouldReturnListOfTrainingsFromStorage() {

        List<Training> expected = List.of(
                Training.builder().build(),
                Training.builder().build(),
                Training.builder().build(),
                Training.builder().build(),
                Training.builder().build(),
                Training.builder().build()
        );

        expected.forEach(t -> trainingDao.save(t));

        List<Training> actual = trainingDao.findAll();

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }
}

package com.epam.gym.app.dao.impl;

import com.epam.gym.app.config.GymAppConfig;
import com.epam.gym.app.entity.Trainer;
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
@SpringBootTest(classes = {TrainerDaoImpl.class})
@DisplayName("TrainerDaoImplTest")
class TrainerDaoImplTest {

    @Autowired
    TrainerDaoImpl trainerDao;

    @BeforeEach
    public void clearStorage() {
        trainerDao.getStorage().getData().clear();
    }

    @Test
    @DisplayName("save() method should save Trainer to Storage")
    void save_shouldSaveTrainerToStorage() {

        long id = 1L;
        String firstname = "firstname";
        String lastname = "firstname";
        TrainingType type = TrainingType.FITNESS;
        Trainer expected = Trainer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .trainingType(type)
                .build();

        trainerDao.save(expected);
        Trainer actual = trainerDao.findById(id).get();

        assertNotNull(actual);
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("update() method should update Trainer in Storage")
    void update_shouldUpdateTraineeInStorage() {

        String firstname = "firstname";
        String lastname = "firstname";
        TrainingType type = TrainingType.FITNESS;
        Trainer beforeUpd = Trainer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .trainingType(type)
                .build();

        long id = 1L;
        String firstnameUpd = "updFirstname";
        String lastnameUpd = "updLastname";
        TrainingType typeUpd = TrainingType.YOGA;
        Trainer afterUpd = Trainer.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .trainingType(type)
                .build();

        trainerDao.save(beforeUpd);
        trainerDao.update(afterUpd);
        Trainer actual = trainerDao.findById(id).get();

        assertNotNull(actual);
        assertEquals(afterUpd.getFirstname(), actual.getFirstname());
        assertEquals(afterUpd.getLastname(), actual.getLastname());
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("findById() method should return Trainer from Storage when Trainer is present")
    void findById_shouldReturnTrainerFromStorage_whenTrainerIsPresent() {

        long id = 1L;
        String firstname = "firstname";
        String lastname = "firstname";
        TrainingType type = TrainingType.FITNESS;
        Trainer expected = Trainer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .trainingType(type)
                .build();

        trainerDao.save(expected);
        Trainer actual = trainerDao.findById(id).get();

        assertNotNull(actual);
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("findAll() method should return List of Trainees from Store")
    void findAll_shouldReturnListOfTraineesFromStorage() {

        List<Trainer> expected = List.of(
                Trainer.builder().build(),
                Trainer.builder().build(),
                Trainer.builder().build(),
                Trainer.builder().build(),
                Trainer.builder().build(),
                Trainer.builder().build()
        );

        expected.forEach(t -> trainerDao.save(t));

        List<Trainer> actual = trainerDao.findAll();

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }
}

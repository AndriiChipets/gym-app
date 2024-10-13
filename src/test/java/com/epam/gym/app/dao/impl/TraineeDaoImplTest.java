package com.epam.gym.app.dao.impl;

import com.epam.gym.app.config.GymAppConfig;
import com.epam.gym.app.entity.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = GymAppConfig.class)
@SpringBootTest(classes = {TraineeDaoImpl.class})
@DisplayName("TraineeDaoImplTest")
class TraineeDaoImplTest {

    @Autowired
    TraineeDaoImpl traineeDao;

    @BeforeEach
    public void clearStorage() {
        traineeDao.getStorage().getData().clear();
    }

    @Test
    @DisplayName("save() method should save Trainee to Storage")
    void save_shouldSaveTraineeToStorage() {

        long id = 1L;
        String firstname = "firstname";
        String lastname = "firstname";
        LocalDate dateOfBirth = LocalDate.parse("2000-12-12");
        String address = "address";
        Trainee expected = Trainee.builder()
                .firstname(firstname)
                .lastname(lastname)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        Trainee actual = traineeDao.save(expected);

        assertNotNull(actual);
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("update() method should update Trainee in Storage")
    void update_shouldUpdateTraineeInStorage() {

        String firstname = "firstname";
        String lastname = "firstname";
        LocalDate dateOfBirth = LocalDate.parse("2000-12-12");
        String address = "address";
        Trainee beforeUpd = Trainee.builder()
                .firstname(firstname)
                .lastname(lastname)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        long id = 1L;
        String firstnameUpd = "updFirstname";
        String lastnameUpd = "updLastname";
        LocalDate dateOfBirthUpd = LocalDate.parse("2012-12-12");
        String addressUpd = "updAddress";
        Trainee afterUpd = Trainee.builder()
                .id(id)
                .firstname(firstnameUpd)
                .lastname(lastnameUpd)
                .dateOfBirth(dateOfBirthUpd)
                .address(addressUpd)
                .build();

        traineeDao.save(beforeUpd);
        Trainee actual = traineeDao.update(afterUpd);

        assertNotNull(actual);
        assertEquals(afterUpd.getFirstname(), actual.getFirstname());
        assertEquals(afterUpd.getLastname(), actual.getLastname());
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("findById() method should return Trainee from Storage when Trainee is present")
    void findById_shouldReturnTraineeFromStorage_whenTraineeIsPresent() {

        long id = 1L;
        String firstname = "firstname";
        String lastname = "firstname";
        LocalDate dateOfBirth = LocalDate.parse("2000-12-12");
        String address = "address";
        Trainee expected = Trainee.builder()
                .firstname(firstname)
                .lastname(lastname)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        traineeDao.save(expected);
        Trainee actual = traineeDao.findById(id).get();

        assertNotNull(actual);
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("findAll() method should return List of Trainees from Store")
    void findAll_shouldReturnListOfTraineesFromStorage() {

        List<Trainee> expected = List.of(
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build()
        );

        expected.forEach(t -> traineeDao.save(t));

        List<Trainee> actual = traineeDao.findAll();

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    @DisplayName("deleteById() method should delete Trainee from Storage")
    void deleteById_shouldDeleteTraineeFromStorage() {

        long traineeId = 2L;
        List<Trainee> expected = List.of(
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build(),
                Trainee.builder().build()
        );

        expected.forEach(t -> traineeDao.save(t));
        traineeDao.deleteById(traineeId);
        List<Trainee> actual = traineeDao.findAll();

        assertNotNull(actual);
        assertEquals(expected.size() - 1, actual.size());
    }
}

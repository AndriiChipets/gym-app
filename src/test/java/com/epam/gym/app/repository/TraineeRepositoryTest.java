package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Training;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        TraineeRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {
                "classpath:sql/schema.sql",
                "classpath:sql/data.sql",
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@DisplayName("TraineeRepositoryTest")
class TraineeRepositoryTest {

    @Autowired
    TraineeRepository traineeRepository;

    @Test
    @DisplayName("findByUsername method should return Trainee when Trainee is present")
    void findByName_shouldReturnTrainee_whenThereAreAnyTraineeRelatedToEnteredUsername() {

        long id = 1;
        String firstname = "Fn";
        String lastname = "Ln";
        String username = "Fn.Ln";
        String password = "1234567890";
        boolean isActive = true;
        String address = "Address1";
        LocalDate dateOfBirth = LocalDate.parse("2001-12-12");

        Optional<Trainee> expTraineeOpt =
                Optional.of(Trainee.builder()
                        .id(id)
                        .firstname(firstname)
                        .lastname(lastname)
                        .username(username)
                        .password(password)
                        .isActive(isActive)
                        .address(address)
                        .dateOfBirth(dateOfBirth)
                        .build());

        Optional<Trainee> actTraineeOpt = traineeRepository.findByUsername(username);

        assertTrue(actTraineeOpt.isPresent());
        assertEquals(expTraineeOpt, actTraineeOpt);
    }

    @Test
    @DisplayName("findByUsername method should return empty Optional when Trainee isn't present")
    void findByName_shouldReturnEmptyOptional_whenThereIsNotAnyTraineeRelatedToEnteredUsername() {

        String username = "Invalid username";
        Optional<Trainee> expTraineeOpt = Optional.empty();

        Optional<Trainee> actTraineeOpt = traineeRepository.findByUsername(username);

        assertFalse(actTraineeOpt.isPresent());
        assertEquals(expTraineeOpt, actTraineeOpt);
    }

    @Test
    @DisplayName("deleteByUsername method should delete Trainee when Trainee is present")
    void deleteByName_shouldDeleteTrainee_whenThereAreAnyTraineeRelatedToEnteredUsername() {

        String username = "Fn.Ln";
        Optional<Trainee> expTraineeOptAftDel = Optional.empty();

        traineeRepository.deleteByUsername(username);
        Optional<Trainee> actTraineeOptAftDel = traineeRepository.findByUsername(username);

        assertFalse(actTraineeOptAftDel.isPresent());
        assertEquals(expTraineeOptAftDel, actTraineeOptAftDel);
    }

    @Test
    @DisplayName("getFilteredTrainings method should return List of Trainings filtered by criteria")
    void getFilteredTrainings_shouldReturnListOfTrainingsFilteredByCriteria() {

        String traineeUsername = "Fn.Ln";
        String trainerUsername = "David.Martinez";
        LocalDate dateFrom = LocalDate.parse("2024-10-19");
        LocalDate dateTo = LocalDate.parse("2024-10-25");
        String typeName = "Fitness";

        List<Training> actTrainings = traineeRepository.getFilteredTrainings(
                traineeUsername, trainerUsername, dateFrom, dateTo, typeName);

        assertFalse(actTrainings.isEmpty());
        assertEquals(1, actTrainings.size());
        assertEquals(traineeUsername, actTrainings.getFirst().getTrainee().getUsername());
        assertEquals(trainerUsername, actTrainings.getFirst().getTrainer().getUsername());
        assertTrue(actTrainings.getFirst().getDate().isAfter(dateFrom));
        assertTrue(actTrainings.getFirst().getDate().isBefore(dateTo));
        assertEquals(typeName, actTrainings.getFirst().getType().getName());
    }

    @Test
    @DisplayName("getFilteredTrainings method should return List of all Trainee Trainings when criteria are nulls")
    void getFilteredTrainings_shouldReturnListOfAllTraineeTrainings_whenCriteriaAreNulls() {

        String traineeUsername = "Fn.Ln";

        List<Training> actTrainings = traineeRepository.getFilteredTrainings(
                traineeUsername, null, null, null, null);

        assertFalse(actTrainings.isEmpty());
        assertEquals(5, actTrainings.size());
    }
}

package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.entity.TrainingType;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        TrainerRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {
                "classpath:sql/schema.sql",
                "classpath:sql/data.sql",
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@DisplayName("TrainerRepositoryTest")
class TrainerRepositoryTest {

    @Autowired
    TrainerRepository trainerRepository;

    @Test
    @DisplayName("findByUsername method should return Trainer when Trainer is present")
    void findByName_shouldReturnTrainer_whenThereAreAnyTrainerRelatedToEnteredUsername() {

        long trainerId = 9;
        String firstname = "David";
        String lastname = "Martinez";
        String username = "David.Martinez";
        String password = "7777777777";
        boolean isActive = true;

        long typeId = 1;
        String name = "Fitness";
        TrainingType specialization = TrainingType.builder().id(typeId).name(name).build();

        Optional<Trainer> expTrainerOpt =
                Optional.of(Trainer.builder()
                        .id(trainerId)
                        .firstname(firstname)
                        .lastname(lastname)
                        .username(username)
                        .password(password)
                        .isActive(isActive)
                        .specialization(specialization)
                        .build());

        Optional<Trainer> actTrainerOpt = trainerRepository.findByUsername(username);

        assertTrue(actTrainerOpt.isPresent());
        assertEquals(expTrainerOpt, actTrainerOpt);
    }

    @Test
    @DisplayName("findByUsername method should return empty Optional when Trainer isn't present")
    void findByName_shouldReturnEmptyOptional_whenThereIsNotAnyTrainerRelatedToEnteredUsername() {

        String username = "Invalid username";
        Optional<Trainer> expTraineeOpt = Optional.empty();

        Optional<Trainer> actTrainerOpt = trainerRepository.findByUsername(username);

        assertFalse(actTrainerOpt.isPresent());
        assertEquals(expTraineeOpt, actTrainerOpt);
    }

    @Test
    @DisplayName("findAllNotAssignedOnTrainee method should return List of Trainers Not assigned on Trainee")
    void findAllNotAssignedOnTrainee_shouldReturnListOfTrainers_whenTrainersNotAssignedOnTrainee() {

        String traineeUsername = "Fn.Ln";

        List<Trainer> actTrainers = trainerRepository.findAllNotAssignedOnTrainee(traineeUsername);

        assertFalse(actTrainers.isEmpty());
        assertEquals(2, actTrainers.size());
    }

    @Test
    @DisplayName("findAllByUsernameIn method should return List of Trainers when Trainers' usernames match")
    void findAllByUsernameIn_shouldReturnListOfTrainers_whenTrainersUsernamesMatch() {

        Set<String> trainersUsernames = Set.of(
                "Olivia.Anderson", "Daniel.Wilson",
                "Sophia.Rodriguez", "David.Martinez",
                "wrong.username"
        );

        List<Trainer> actTrainers = trainerRepository.findAllByUsernameIn(trainersUsernames);
        Set<String> actTrainersUsernames = actTrainers.
                stream().
                map(Trainer::getUsername).
                collect(Collectors.toSet());

        assertFalse(actTrainers.isEmpty());
        assertEquals(trainersUsernames.size() - 1, actTrainers.size());
        assertTrue(trainersUsernames.containsAll(actTrainersUsernames));
    }

    @Test
    @DisplayName("getFilteredTrainings method should return List of Trainings filtered by criteria")
    void getFilteredTrainings_shouldReturnListOfTrainingsFilteredByCriteria() {

        String trainerUsername = "Sophia.Rodriguez";
        String traineeUsername = "Sara.Johnson";
        LocalDate dateFrom = LocalDate.parse("2024-10-19");
        LocalDate dateTo = LocalDate.parse("2024-10-26");

        List<Training> actTrainings = trainerRepository.getFilteredTrainings(
                trainerUsername, traineeUsername, dateFrom, dateTo);

        assertFalse(actTrainings.isEmpty());
        assertEquals(1, actTrainings.size());
        assertEquals(traineeUsername, actTrainings.getFirst().getTrainee().getUsername());
        assertEquals(trainerUsername, actTrainings.getFirst().getTrainer().getUsername());
        assertTrue(actTrainings.getFirst().getDate().isAfter(dateFrom));
        assertTrue(actTrainings.getFirst().getDate().isBefore(dateTo));
    }

    @Test
    @DisplayName("getFilteredTrainings method should return List of all Trainer Trainings when criteria are nulls")
    void getFilteredTrainings_shouldReturnListOfAllTrainerTrainings_whenCriteriaAreNulls() {

        String trainerUsername = "Sophia.Rodriguez";

        List<Training> actTrainings = trainerRepository.getFilteredTrainings(
                trainerUsername, null, null, null);

        assertFalse(actTrainings.isEmpty());
        assertEquals(3, actTrainings.size());
    }
}

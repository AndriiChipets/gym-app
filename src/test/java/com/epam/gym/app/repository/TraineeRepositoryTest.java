package com.epam.gym.app.repository;

import com.epam.gym.app.config.GymAppConfig;
import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.testcontainer.MysqlTestContainer;
import com.epam.gym.app.utils.UserUtil;
import org.junit.ClassRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        TraineeRepository.class}))
@ContextConfiguration(classes = GymAppConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = "classpath:sql/schema.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@DisplayName("TraineeRepositoryTest")
public class TraineeRepositoryTest {

    @ClassRule
    public static MySQLContainer<?> mySQLContainer = MysqlTestContainer.getInstance();

    @Autowired
    TraineeRepository traineeRepository;

    @Test
    @DisplayName("findByUsername method should return Trainee when Trainee is present")
    void findByName_shouldReturnTrainee_whenThereAreAnyTraineeRelatedToEnteredUsername() {

        long id = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        String username = "firstname.lastname";
        String password = UserUtil.generateRandomPassword();
        boolean isActive = true;
        String address = "address";
        LocalDate dateOfBirth = LocalDate.parse("2000-12-12");

        Trainee trainee = Trainee.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .isActive(isActive)
                .address(address)
                .dateOfBirth(dateOfBirth)
                .build();

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

        traineeRepository.save(trainee);
        Optional<Trainee> actTraineeOpt = traineeRepository.findByUsername(username);

        assertEquals(expTraineeOpt, actTraineeOpt);
    }

    @Test
    @DisplayName("findByUsername method should return empty Optional when Trainee isn't present")
    void findByName_shouldReturnEmptyOptional_whenThereIsNotAnyTraineeRelatedToEnteredUsername() {

        String username = "Invalid username";
        Optional<Trainee> expectedTraineeOptional = Optional.empty();

        Optional<Trainee> actualTraineeOptional = traineeRepository.findByUsername(username);

        assertEquals(expectedTraineeOptional, actualTraineeOptional);
    }

    @Test
    @DisplayName("deleteByUsername method should delete Trainee when Trainee is present")
    void deleteByName_shouldDeleteTrainee_whenThereAreAnyTraineeRelatedToEnteredUsername() {

        long id = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        String username = "firstname.lastname";
        String password = UserUtil.generateRandomPassword();
        boolean isActive = true;
        String address = "address";
        LocalDate dateOfBirth = LocalDate.parse("2000-12-12");

        Trainee trainee = Trainee.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .isActive(isActive)
                .address(address)
                .dateOfBirth(dateOfBirth)
                .build();

        Optional<Trainee> expTraineeOptBfrDel =
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

        Optional<Trainee> expTraineeOptAftDel = Optional.empty();

        traineeRepository.save(trainee);
        Optional<Trainee> actTraineeOptBfrDel = traineeRepository.findByUsername(username);
        assertEquals(expTraineeOptBfrDel, actTraineeOptBfrDel);

        traineeRepository.deleteByUsername(username);
        Optional<Trainee> actTraineeOptAftDel = traineeRepository.findByUsername(username);
        assertEquals(expTraineeOptAftDel, actTraineeOptAftDel);
    }

    @Test
    @DisplayName("existsByUsernameAndPassword method should return true when Trainee is present")
    void existsByUsernameAndPassword_shouldReturnTrue_whenThereAreAnyTraineeRelatedToEnteredUsernameAndPassword() {

        String firstname = "firstname";
        String lastname = "lastname";
        String username = "firstname.lastname";
        String password = "1234567890";
        boolean isActive = true;
        String address = "address";
        LocalDate dateOfBirth = LocalDate.parse("2000-12-12");

        Trainee trainee = Trainee.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .isActive(isActive)
                .address(address)
                .dateOfBirth(dateOfBirth)
                .build();

        boolean expected = true;

        traineeRepository.save(trainee);
        boolean actual = traineeRepository.existsByUsernameAndPassword(username, password);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("existsByUsernameAndPassword method should return false when Trainee isn't present")
    void existsByUsernameAndPassword_shouldReturnFalse_whenThereIsNotAnyTraineeRelatedToEnteredUsernameAndPassword() {

        String username = "wrong.username";
        String password = "1234567890";

        boolean expected = false;

        boolean actual = traineeRepository.existsByUsernameAndPassword(username, password);

        assertEquals(expected, actual);
    }
}

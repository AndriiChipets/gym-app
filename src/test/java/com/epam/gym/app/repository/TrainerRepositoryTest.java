package com.epam.gym.app.repository;

import com.epam.gym.app.config.GymAppConfig;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.TrainingType;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        TrainerRepository.class}))
@ContextConfiguration(classes = GymAppConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = "classpath:sql/schema.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@DisplayName("TrainerRepositoryTest")
public class TrainerRepositoryTest {

    @ClassRule
    public static MySQLContainer<?> mySQLContainer = MysqlTestContainer.getInstance();

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    TrainingTypeRepository typeRepository;

    @Test
    @DisplayName("findByUsername method should return Trainer when Trainer is present")
    void findByName_shouldReturnTrainer_whenThereAreAnyTrainerRelatedToEnteredUsername() {

        long trainerId = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        String username = "firstname.lastname";
        String password = UserUtil.generateRandomPassword();
        boolean isActive = true;

        long typeId = 1;
        String name = "YOGA";
        TrainingType typeBefSave = TrainingType.builder().name(name).build();
        typeRepository.save(typeBefSave);
        TrainingType typeAftSave = TrainingType.builder().id(typeId).name(name).build();

        Trainer trainer = Trainer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .isActive(isActive)
                .specialization(typeAftSave)
                .build();

        Optional<Trainer> expTrainerOpt =
                Optional.of(Trainer.builder()
                        .id(trainerId)
                        .firstname(firstname)
                        .lastname(lastname)
                        .username(username)
                        .password(password)
                        .isActive(isActive)
                        .specialization(typeAftSave)
                        .build());

        trainerRepository.save(trainer);
        Optional<Trainer> actTrainerOpt = trainerRepository.findByUsername(username);

        assertEquals(expTrainerOpt, actTrainerOpt);
    }

    @Test
    @DisplayName("findByUsername method should return empty Optional when Trainer isn't present")
    void findByName_shouldReturnEmptyOptional_whenThereIsNotAnyTrainerRelatedToEnteredUsername() {

        String username = "Invalid username";
        Optional<Trainer> expTraineeOpt = Optional.empty();

        Optional<Trainer> actTrainerOpt = trainerRepository.findByUsername(username);

        assertEquals(expTraineeOpt, actTrainerOpt);
    }

    @Test
    @DisplayName("existsByUsernameAndPassword method should return true when Trainer is present")
    void existsByUsernameAndPassword_shouldReturnTrue_whenThereAreAnyTrainerRelatedToEnteredUsernameAndPassword() {

        String firstname = "firstname";
        String lastname = "lastname";
        String username = "firstname.lastname";
        String password = UserUtil.generateRandomPassword();
        boolean isActive = true;

        long typeId = 1;
        String name = "YOGA";
        TrainingType typeBefSave = TrainingType.builder().name(name).build();
        typeRepository.save(typeBefSave);
        TrainingType typeAftSave = TrainingType.builder().id(typeId).name(name).build();

        Trainer trainer = Trainer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .isActive(isActive)
                .specialization(typeAftSave)
                .build();

        boolean expected = true;

        trainerRepository.save(trainer);
        boolean actual = trainerRepository.existsByUsernameAndPassword(username, password);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("existsByUsernameAndPassword method should return false when Trainer isn't present")
    void existsByUsernameAndPassword_shouldReturnFalse_whenThereIsNotAnyTrainerRelatedToEnteredUsernameAndPassword() {

        String username = "wrong.username";
        String password = "1234567890";

        boolean expected = false;

        boolean actual = trainerRepository.existsByUsernameAndPassword(username, password);

        assertEquals(expected, actual);
    }
}

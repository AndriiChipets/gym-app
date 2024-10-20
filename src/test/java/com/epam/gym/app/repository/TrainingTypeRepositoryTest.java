package com.epam.gym.app.repository;

import com.epam.gym.app.config.GymAppConfig;
import com.epam.gym.app.entity.TrainingType;
import com.epam.gym.app.testcontainer.MysqlTestContainer;
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
        TrainingTypeRepository.class}))
@ContextConfiguration(classes = GymAppConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = "classpath:sql/schema.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@DisplayName("TrainingTypeRepositoryTest")
public class TrainingTypeRepositoryTest {

    @ClassRule
    public static MySQLContainer<?> mySQLContainer = MysqlTestContainer.getInstance();

    @Autowired
    TrainingTypeRepository typeRepository;

    @Test
    @DisplayName("findByName method should return TrainingType when TrainingType is present")
    void findByName_shouldReturnTrainingType_whenThereAreAnyTrainingTypeRelatedToEnteredName() {

        long id = 1;
        String name = "YOGA";
        TrainingType type = TrainingType.builder().name(name).build();
        Optional<TrainingType> expTypeOpt =
                Optional.of(TrainingType.builder().name(name).id(id).build());

        typeRepository.save(type);
        Optional<TrainingType> actTypeOpt = typeRepository.findByName(name);

        assertEquals(expTypeOpt, actTypeOpt);
    }

    @Test
    @DisplayName("findByName method should return empty Optional when TrainingType isn't present")
    void findByName_shouldReturnEmptyOptional_whenThereIsNotAnyTrainingTypeRelatedToEnteredName() {

        String name = "Invalid name";
        Optional<TrainingType> expTypeOpt = Optional.empty();

        Optional<TrainingType> actTypeOpt = typeRepository.findByName(name);

        assertEquals(expTypeOpt, actTypeOpt);
    }
}

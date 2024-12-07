package com.epam.gym.app.repository;

import com.epam.gym.app.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        UserRepository.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {
                "classpath:sql/schema.sql",
                "classpath:sql/data.sql",
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@DisplayName("UserRepositoryTest")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("findByUsername method should return User when User is present")
    void findByUsername_shouldReturnUser_whenThereAreAnyUserRelatedToEnteredUsername() {

        long id = 1;
        String firstname = "Fn";
        String lastname = "Ln";
        String username = "Fn.Ln";
        String password = "1234567890";
        boolean isActive = true;

        Optional<User> expUserOpt =
                Optional.of(User.builder()
                        .id(id)
                        .firstname(firstname)
                        .lastname(lastname)
                        .username(username)
                        .password(password)
                        .isActive(isActive)
                        .build());

        Optional<User> actUserOpt = userRepository.findByUsername(username);

        assertTrue(actUserOpt.isPresent());
        assertEquals(expUserOpt.get().getId(), actUserOpt.get().getId());
        assertEquals(expUserOpt.get().getUsername(), actUserOpt.get().getUsername());
        assertEquals(expUserOpt.get().getPassword(), actUserOpt.get().getPassword());
    }

    @Test
    @DisplayName("findByUsername method should return empty Optional when User isn't present")
    void findByName_shouldReturnEmptyOptional_whenThereIsNotAnyUserWithEnteredUsername() {

        String username = "Invalid name";
        Optional<User> expUserOpt = Optional.empty();

        Optional<User> actUserOpt = userRepository.findByUsername(username);

        assertFalse(actUserOpt.isPresent());
        assertEquals(expUserOpt, actUserOpt);
    }

    @Test
    @DisplayName("existsByUsernameAndPassword method should return true when User is present")
    void existsByUsernameAndPassword_shouldReturnTrue_whenThereAreAnyUserRelatedToEnteredUsernameAndPassword() {

        String username = "Fn.Ln";
        String password = "1234567890";

        boolean isExist = userRepository.existsByUsernameAndPassword(username, password);

        assertTrue(isExist);
    }

    @Test
    @DisplayName("existsByUsernameAndPassword method should return false when User isn't present")
    void existsByUsernameAndPassword_shouldReturnFalse_whenThereIsNotAnyUserRelatedToEnteredUsernameAndPassword() {

        String username = "wrong.username";
        String password = "1234567890";

        boolean isExist = userRepository.existsByUsernameAndPassword(username, password);

        assertFalse(isExist);
    }
}

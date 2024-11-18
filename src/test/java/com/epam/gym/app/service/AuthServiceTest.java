package com.epam.gym.app.service;

import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.entity.User;
import com.epam.gym.app.exception.UserNotLoginException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.epam.gym.app.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
@DisplayName("AuthServiceTest")
class AuthServiceTest {

    @MockBean
    UserRepository authRepository;

    @Autowired
    UserService authService;

    @Test
    @DisplayName("changePassword() method should return true when User's password is changed successfully")
    void changePassword_shouldReturnTrue_whenUserPasswordIsChangedSuccessfully() {

        String username = "firstname.lastname";
        String oldPassword = "old valid password";
        String newPassword = "new valid password";
        UserChangePasswordDTO userChangePasswordDTO = UserChangePasswordDTO.builder()
                .username(username)
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();
        User oldUser = User.builder().username(username).password(oldPassword).build();
        User newUser = User.builder().username(username).password(newPassword).build();

        when(authRepository.existsByUsernameAndPassword(anyString(), anyString())).thenReturn(true);
        when(authRepository.findByUsername(anyString())).thenReturn(Optional.of(oldUser));
        when(authRepository.save(any(User.class))).thenReturn(newUser);
        boolean isPasswordChanged = authService.changePassword(userChangePasswordDTO);

        assertTrue(isPasswordChanged);
        verify(authRepository).existsByUsernameAndPassword(username, oldPassword);
    }

    @Test
    @DisplayName("changePassword() method throw UserNotLoginException when User's password is not changed")
    void changePassword_shouldThrowUserNotLoginException_whenUserPasswordIsNotChanged() {

        String username = "firstname.lastname";
        String oldPassword = "old invalid password";
        String newPassword = "new valid password";
        UserChangePasswordDTO userChangePasswordDTO = UserChangePasswordDTO.builder()
                .username(username)
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();

        when(authRepository.existsByUsernameAndPassword(anyString(), anyString())).thenReturn(false);
        Exception exception = assertThrows(UserNotLoginException.class,
                () -> authService.changePassword(userChangePasswordDTO));

        assertEquals("Old password or username is incorrect", exception.getMessage());
        verify(authRepository, times(1)).existsByUsernameAndPassword(username, oldPassword);
    }
}

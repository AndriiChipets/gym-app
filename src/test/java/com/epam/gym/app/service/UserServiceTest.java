package com.epam.gym.app.service;

import com.epam.gym.app.dto.user.AuthRequest;
import com.epam.gym.app.dto.user.AuthResponse;
import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.entity.User;
import com.epam.gym.app.exception.UserNotLoginException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.epam.gym.app.repository.TokenRepository;
import com.epam.gym.app.security.JwtService;
import com.epam.gym.app.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserService.class})
@DisplayName("AuthServiceTest")
class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    JwtService jwtService;

    @MockBean
    TokenRepository tokenRepository;

    @Autowired
    UserService userService;

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

        when(userRepository.existsByUsernameAndPassword(anyString(), anyString())).thenReturn(true);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(oldUser));
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        boolean isPasswordChanged = userService.changePassword(userChangePasswordDTO);

        assertTrue(isPasswordChanged);
        verify(userRepository).existsByUsernameAndPassword(username, oldPassword);
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

        when(userRepository.existsByUsernameAndPassword(anyString(), anyString())).thenReturn(false);
        Exception exception = assertThrows(UserNotLoginException.class,
                () -> userService.changePassword(userChangePasswordDTO));

        assertEquals("Old password or username is incorrect", exception.getMessage());
        verify(userRepository, times(1)).existsByUsernameAndPassword(username, oldPassword);
    }

    @Test
    @DisplayName("authenticate() method should return AuthResponse with Token when User authenticated successfully")
    void authenticate_shouldReturnAuthResponseWithToken_whenUserAuthenticatedSuccessfully() {

        String username = "firstname.lastname";
        String password = "1234567890";
        String tokenName = "token";
        AuthRequest authRequest = AuthRequest
                .builder()
                .username(username)
                .password(password)
                .build();

        AuthResponse expResponse = AuthResponse
                .builder()
                .tokenName(tokenName)
                .build();

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn(tokenName);
        AuthResponse actualResponse = userService.authenticate(authRequest);

        assertNotNull(actualResponse);
        assertEquals(expResponse.getTokenName(), actualResponse.getTokenName());
        verify(userRepository).findByUsername(username);
    }

    @Test
    @DisplayName("authenticate() method throw UserNotLoginException when User not authenticated successfully")
    void authenticate_shouldThrowUserNotLoginException_whenUserIsNotNotAuthenticated() {

        String username = "firstname.lastname";
        String password = "1234567890";
        AuthRequest authRequest = AuthRequest
                .builder()
                .username(username)
                .password(password)
                .build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotLoginException.class,
                () -> userService.authenticate(authRequest));

        assertEquals("User with username: " + username + " not login", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }
}

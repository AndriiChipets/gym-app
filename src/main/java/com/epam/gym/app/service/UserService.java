package com.epam.gym.app.service;

import com.epam.gym.app.dto.user.AuthRequest;
import com.epam.gym.app.dto.user.AuthResponse;
import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.entity.User;
import com.epam.gym.app.exception.NoEntityPresentException;
import com.epam.gym.app.exception.UserNotLoginException;
import com.epam.gym.app.security.JwtService;
import com.epam.gym.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public boolean changePassword(UserChangePasswordDTO changePasswordDTO) {
        String username = changePasswordDTO.getUsername();
        String oldPassword = changePasswordDTO.getOldPassword();
        String newPassword = changePasswordDTO.getNewPassword();

        if (!authRepository.existsByUsernameAndPassword(username, oldPassword)) {
            log.warn("Old password or username is incorrect");
            throw new UserNotLoginException("Old password or username is incorrect");
        }

        User user = authRepository.findByUsername(username).get();
        user.setPassword(newPassword);
        authRepository.save(user);

        return user.getPassword().equals(newPassword);
    }

    @Transactional(readOnly = true)
    public AuthResponse authenticate(AuthRequest request) {

        final String username = request.getUsername();
        final String password = request.getPassword();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        User user = authRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("There is no User with provided username {}", username);
                    return new NoEntityPresentException("There is no User with provided username: " + username);
                }
        );
        String token = jwtService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }
}

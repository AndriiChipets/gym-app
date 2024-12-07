package com.epam.gym.app.service;

import com.epam.gym.app.dto.user.AuthRequest;
import com.epam.gym.app.dto.user.AuthResponse;
import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.entity.Token;
import com.epam.gym.app.entity.User;
import com.epam.gym.app.exception.UserNotLoginException;
import com.epam.gym.app.repository.TokenRepository;
import com.epam.gym.app.security.JwtService;
import com.epam.gym.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Transactional
    public boolean changePassword(UserChangePasswordDTO changePasswordDTO) {
        String username = changePasswordDTO.getUsername();
        String oldPassword = changePasswordDTO.getOldPassword();
        String newPassword = changePasswordDTO.getNewPassword();

        if (!userRepository.existsByUsernameAndPassword(username, oldPassword)) {
            log.warn("Old password or username is incorrect");
            throw new UserNotLoginException("Old password or username is incorrect");
        }

        User user = userRepository.findByUsername(username).get();
        user.setPassword(newPassword);
        userRepository.save(user);

        return user.getPassword().equals(newPassword);
    }

    @Transactional
    public AuthResponse authenticate(AuthRequest request) {

        final String username = request.getUsername();
        final String password = request.getPassword();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("User with username {} not login", username);
                    return new UserNotLoginException("User with username: " + username + " not login");
                }
        );

        String tokenName = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveToken(tokenName, user);

        return AuthResponse.builder().tokenName(tokenName).build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> userValidTokens = tokenRepository.findAllTokenByUserId(user.getId());

        if (!userValidTokens.isEmpty()) {
            userValidTokens.forEach(t -> t.setLoggedOut(true));
            tokenRepository.saveAll(userValidTokens);
        }
    }

    private void saveToken(String tokenName, User user) {
        Token token = Token.builder()
                .name(tokenName)
                .isLoggedOut(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }
}

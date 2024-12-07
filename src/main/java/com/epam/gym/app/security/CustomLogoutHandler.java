package com.epam.gym.app.security;

import com.epam.gym.app.entity.Token;
import com.epam.gym.app.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader("Authorization");
        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            return;
        }
        String tokenName = authHeader.substring(7);
        Token token = tokenRepository.findByName(tokenName).orElse(null);

        if (Objects.nonNull(token)) {
            token.setLoggedOut(true);
            tokenRepository.save(token);
        }

    }
}

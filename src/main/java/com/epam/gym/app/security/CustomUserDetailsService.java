package com.epam.gym.app.security;

import com.epam.gym.app.entity.User;
import com.epam.gym.app.exception.NoEntityPresentException;
import com.epam.gym.app.repository.AuthRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Set<GrantedAuthority> authorities = new HashSet<>();

        User user = authRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("There is no User with provided username {}", username);
                    return new NoEntityPresentException("There is no User with provided username: " + username);
                });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}

package com.epam.gym.app.security;

import com.epam.gym.app.entity.User;
import com.epam.gym.app.exception.BruteForceException;
import com.epam.gym.app.repository.UserRepository;
import com.epam.gym.app.security.brute_force.LoginAttemptService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service("userDetailsService")
@AllArgsConstructor
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String username) {

        if (loginAttemptService.isBlocked()) {
            throw new BruteForceException("blocked");
        }

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            return new org.springframework.security.core.userdetails.User(
                    " ",
                    " ",
                    true,
                    true,
                    true,
                    true,
                    new HashSet<>());
        }
        return userOpt.get();
    }
}

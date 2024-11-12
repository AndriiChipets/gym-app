package com.epam.gym.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.epam.gym.app.utils.Constants.TRAINEE_REST_URL;
import static com.epam.gym.app.utils.Constants.TRAINER_REST_URL;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, TRAINER_REST_URL, TRAINEE_REST_URL).permitAll()
                        .anyRequest().authenticated()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}

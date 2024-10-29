package com.epam.gym.app.controller;

import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.service.AuthService;
import com.epam.gym.app.exception.NoEntityPresentException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        if (!authService.login(userLoginDTO)) {
            throw new NoEntityPresentException("User is not login");
        }
    }

    @PutMapping("/login")
    public void changePassword(@Valid @RequestBody UserChangePasswordDTO changePasswordDTO) {
        if (!authService.changePassword(changePasswordDTO)) {
            throw new IllegalArgumentException("password wasn't changed");
        }
    }
}

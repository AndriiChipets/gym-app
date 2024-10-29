package com.epam.gym.app.controller;

import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.exception.UnsatisfiedActionException;
import com.epam.gym.app.exception.UserNotLoginException;
import com.epam.gym.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Controller", description = "Methods for User logging and change User password")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    @Operation(summary = "Login User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully login"),
            @ApiResponse(responseCode = "404", description = "User with provided password and username is not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        if (!authService.login(userLoginDTO)) {
            throw new UserNotLoginException("Password or username is incorrect");
        }
    }

    @PutMapping("/login")
    @Operation(summary = "Change User password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User password successfully changed"),
            @ApiResponse(responseCode = "400", description = "User old password or username is incorrect"),
    })
    public void changePassword(@Valid @RequestBody UserChangePasswordDTO changePasswordDTO) {
        if (!authService.changePassword(changePasswordDTO)) {
            throw new UnsatisfiedActionException("password wasn't changed");
        }
    }
}

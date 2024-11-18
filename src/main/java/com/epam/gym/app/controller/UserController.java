package com.epam.gym.app.controller;

import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.exception.UnsatisfiedActionException;
import com.epam.gym.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.gym.app.utils.Constants.AUTH_REST_URL;

@RestController
@RequestMapping(value = AUTH_REST_URL)
@AllArgsConstructor
@Tag(name = "Authentication Controller", description = "Methods for User logging and change User password")
public class UserController {

    private final UserService userService;

    @PutMapping
    @Operation(summary = "Change User password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User password successfully changed"),
            @ApiResponse(responseCode = "400", description = "User old password or username is incorrect"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public void changePassword(@Valid @RequestBody UserChangePasswordDTO changePasswordDTO) {
        if (!userService.changePassword(changePasswordDTO)) {
            throw new UnsatisfiedActionException("password is not changed");
        }
    }
}

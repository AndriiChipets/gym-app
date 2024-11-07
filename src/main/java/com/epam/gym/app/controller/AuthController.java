package com.epam.gym.app.controller;

import com.epam.gym.app.annotation.Authenticated;
import com.epam.gym.app.annotation.LoginCounter;
import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.exception.UnsatisfiedActionException;
import com.epam.gym.app.exception.UserNotLoginException;
import com.epam.gym.app.service.AuthService;
import com.epam.gym.app.utils.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.gym.app.utils.Constants.AUTH_REST_URL;

@RestController
@RequestMapping(value = AUTH_REST_URL)
@AllArgsConstructor
@Tag(name = "Authentication Controller", description = "Methods for User logging and change User password")
public class AuthController {

    private final AuthService authService;

    @GetMapping
    @LoginCounter
    @Operation(summary = "Login User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully login"),
            @ApiResponse(responseCode = "404", description = "User with provided password and username is not found"),
    })
    @ResponseStatus(HttpStatus.OK)
    public void login(
            @RequestParam @NotBlank String username,
            @RequestParam
            @Size(min = UserUtil.PASSWORD_LENGTH, max = UserUtil.PASSWORD_LENGTH, message
                    = "password must be exactly " + UserUtil.PASSWORD_LENGTH + " characters length")
            String password,
            HttpSession session) {

        if (!authService.login(username, password)) {
            throw new UserNotLoginException("User is not login");
        }

        session.setAttribute("loggedInUser", UserLoginDTO.builder().username(username).password(password).build());
    }

    @PutMapping
    @Authenticated
    @Operation(summary = "Change User password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User password successfully changed"),
            @ApiResponse(responseCode = "400", description = "User old password or username is incorrect"),
            @ApiResponse(responseCode = "501", description = "Network Authentication Required")
    })
    public void changePassword(@Valid @RequestBody UserChangePasswordDTO changePasswordDTO) {
        if (!authService.changePassword(changePasswordDTO)) {
            throw new UnsatisfiedActionException("password is not changed");
        }
    }
}

package com.epam.gym.app.controller;

import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@DisplayName("AuthControllerTest")
class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    UserLoginDTO userLoginDTO;

    UserChangePasswordDTO userChangePasswordDTO;

    @BeforeEach
    public void setup() {

        userLoginDTO = UserLoginDTO
                .builder()
                .username("firstname.lastname")
                .password("1234567890")
                .build();

        userChangePasswordDTO = UserChangePasswordDTO
                .builder()
                .username("firstname.lastname")
                .oldPassword("1234567890")
                .newPassword("0987654321")
                .build();
    }

    @Test
    @DisplayName("login() method should return OK status when User login successfully")
    void login_shouldReturnOKStatus_whenUserLoginSuccessfully() throws Exception {

        given(authService.login(any(UserLoginDTO.class))).willReturn(true);
        ResultActions response = mockMvc.perform(get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDTO)));

        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("login() method should throw UserNotLoginException when User is not login")
    void login_shouldThrowUserNotLoginException_whenUserIsNotLogin() throws Exception {

        given(authService.login(any(UserLoginDTO.class))).willReturn(false);
        ResultActions response = mockMvc.perform(get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDTO)));

        response.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("NOT_LOGIN")));
    }

    @Test
    @DisplayName("changePassword() method should return OK status when User password changed successfully")
    void changePassword_shouldReturnOKStatus_whenUserPasswordChangedSuccessfully() throws Exception {

        given(authService.changePassword(any(UserChangePasswordDTO.class))).willReturn(true);
        ResultActions response = mockMvc.perform(put("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDTO)));

        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("changePassword() method should throw UnsatisfiedActionException when password in not changed")
    void changePassword_shouldThrowUnsatisfiedActionException_whenPasswordIsNotChanged() throws Exception {

        given(authService.changePassword(any(UserChangePasswordDTO.class))).willReturn(false);
        ResultActions response = mockMvc.perform(put("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLoginDTO)));

        response.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("UNSATISFIED_ACTION")));
    }
}

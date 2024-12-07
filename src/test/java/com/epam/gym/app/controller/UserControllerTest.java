package com.epam.gym.app.controller;

import com.epam.gym.app.dto.user.AuthRequest;
import com.epam.gym.app.dto.user.AuthResponse;
import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.epam.gym.app.utils.Constants.AUTH_REST_URL;
import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
@DisplayName("UserControllerTest")
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    UserChangePasswordDTO userChangePasswordDTO;
    AuthRequest authRequest;
    AuthResponse authResponse;

    @BeforeEach
    public void setup() {

        authRequest = AuthRequest
                .builder()
                .username("firstname.lastname")
                .password("1234567890")
                .build();

        authResponse = AuthResponse
                .builder()
                .tokenName("token")
                .build();

        userChangePasswordDTO = UserChangePasswordDTO
                .builder()
                .username("firstname.lastname")
                .oldPassword("1234567890")
                .newPassword("0987654321")
                .build();
    }

    @Test
    @DisplayName("login() method should return AuthResponse with Token when User is login successfully")
    @WithMockUser
    void login_shouldReturnAuthResponseWithToken_whenUserLoginSuccessfully() throws Exception {

        given(userService.authenticate(any(AuthRequest.class))).willReturn(authResponse);
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_REST_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenName", is(authResponse.getTokenName())));
    }

    @Test
    @DisplayName("changePassword() method should return OK status when User password changed successfully")
    @WithMockUser
    void changePassword_shouldReturnOKStatus_whenUserPasswordChangedSuccessfully() throws Exception {

        given(userService.changePassword(any(UserChangePasswordDTO.class))).willReturn(true);
        ResultActions response = mockMvc.perform(put(AUTH_REST_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userChangePasswordDTO)));

        response.andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("changePassword() method should throw UnsatisfiedActionException when password in not changed")
    @WithMockUser
    void changePassword_shouldThrowUnsatisfiedActionException_whenPasswordIsNotChanged() throws Exception {

        given(userService.changePassword(any(UserChangePasswordDTO.class))).willReturn(false);
        ResultActions response = mockMvc.perform(put(AUTH_REST_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userChangePasswordDTO)));

        response.andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("UNSATISFIED_ACTION")));
    }
}

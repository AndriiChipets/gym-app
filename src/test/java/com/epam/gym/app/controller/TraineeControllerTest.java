package com.epam.gym.app.controller;

import com.epam.gym.app.dto.trainee.TraineeGetDTO;
import com.epam.gym.app.dto.trainee.TraineeRegDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainerListDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingFilterDTO;
import com.epam.gym.app.dto.trainee.TraineeUpdDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.service.TraineeService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TraineeController.class)
@DisplayName("TraineeControllerTest")
class TraineeControllerTest {

    @MockBean
    private TraineeService traineeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("registerTrainee() method should register Trainee and return OK status when TraineeRegDTO is correct")
    void registerTrainee_shouldRegisterTraineeAndReturnOkStatus_whenTraineeRegDTOIsCorrect() throws Exception {

        String firstname = "firstname";
        String lastname = "lastname";
        String dateOfBirth = "2000-10-10";
        String address = "Some address";

        TraineeRegDTO traineeRegDTO = TraineeRegDTO.builder()
                .firstname(firstname)
                .lastname(lastname)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        String username = "firstname.lastname";
        String password = "1234567890";
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username(username)
                .password(password)
                .build();

        given(traineeService.save(any(TraineeRegDTO.class))).willReturn(userLoginDTO);
        ResultActions response = mockMvc.perform(post("/trainee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(traineeRegDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.password", is(password)));
    }

    @Test
    @DisplayName("getTrainee() method should return Trainee when Trainee with username is present")
    void getTrainee_shouldReturnTrainee_whenUserWithUsernameIsPresent() throws Exception {

        String username = "firstname.lastname";
        String firstname = "firstname";
        String lastname = "lastname";
        boolean isActive = true;
        Set<TrainerListDTO> trainers = new HashSet<>();

        TraineeGetDTO traineeGetDTO = TraineeGetDTO.builder()
                .firstname(firstname)
                .lastname(lastname)
                .isActive(isActive)
                .trainers(trainers)
                .build();

        given(traineeService.find(anyString())).willReturn(traineeGetDTO);
        ResultActions response = mockMvc.perform(get("/trainee")
                .param("username", username));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trainers.size()", is(trainers.size())))
                .andExpect(jsonPath("$.firstname", is(firstname)))
                .andExpect(jsonPath("$.lastname", is(lastname)))
                .andExpect(jsonPath("$.isActive", is(isActive)));
    }

    @Test
    @DisplayName("updateTrainee() method should update Trainee when TraineeUpdDTO is correct")
    void updateTrainee_shouldUpdateTrainee_whenTraineeUpdDTOIsCorrect() throws Exception {

        String username = "firstname.lastname";
        String firstname = "firstname";
        String lastname = "lastname";
        boolean isActive = true;

        TraineeUpdDTO traineeUpdDTO = TraineeUpdDTO.builder()
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .isActive(isActive)
                .build();

        given(traineeService.update(any(TraineeUpdDTO.class))).willReturn(traineeUpdDTO);
        ResultActions response = mockMvc.perform(put("/trainee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(traineeUpdDTO)));

        response.andDo(print())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.firstname", is(firstname)))
                .andExpect(jsonPath("$.lastname", is(lastname)))
                .andExpect(jsonPath("$.isActive", is(isActive)));
    }

    @Test
    @DisplayName("deleteTrainee() method should delete Trainee when Trainee with username is present")
    void getTrainee_shouldDeleteTrainee_whenUserWithUsernameIsPresent() throws Exception {

        String username = "firstname.lastname";

        willDoNothing().given(traineeService).delete(username);
        ResultActions response = mockMvc.perform(delete("/trainee")
                .param("username", username));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("updateTraineeTrainerList() method should update list of Trainee's Trainers")
    void updateTraineeTrainerList_shouldUpdateListOfTraineeTrainers() throws Exception {

        String username = "firstname.lastname";
        Set<String> trainersUsernames = new HashSet<>();
        TraineeTrainerListDTO traineeTrainerListDTO = TraineeTrainerListDTO.builder()
                .username(username)
                .trainersUsernames(trainersUsernames)
                .build();
        List<TrainerListDTO> trainers = new ArrayList<>();

        given(traineeService.updateTraineeTrainerList(any(TraineeTrainerListDTO.class))).willReturn(trainers);
        ResultActions response = mockMvc.perform(put("/trainee/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(traineeTrainerListDTO)));

        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(trainersUsernames.size())));
    }

    @Test
    @DisplayName("getTraineeTrainingList() method should return list of Trainee's Trainings")
    void getTraineeTrainingList_shouldReturnListOfTraineeTrainings() throws Exception {

        String username = "firstname.lastname";
        TraineeTrainingFilterDTO trainingFilterDTO = TraineeTrainingFilterDTO.builder().username(username).build();
        List<TraineeTrainingDTO> trainings = new ArrayList<>();

        given(traineeService.getTrainingsList(any(TraineeTrainingFilterDTO.class))).willReturn(trainings);
        ResultActions response = mockMvc.perform(get("/trainee/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainingFilterDTO)));

        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(trainings.size())));
    }

    @Test
    @DisplayName("activateDeactivateTrainee() method should change Trainee's isActive status")
    void activateDeactivateTrainee_shouldChangeTraineeIsActiveStatus() throws Exception {

        String username = "firstname.lastname";
        boolean isActive = true;

        willDoNothing().given(traineeService).activateDeactivate(username, isActive);
        ResultActions response = mockMvc.perform(patch("/trainee")
                .param("username", username)
                .param("isActive", String.valueOf(isActive)));

        response.andDo(print())
                .andExpect(status().isOk());
    }
}

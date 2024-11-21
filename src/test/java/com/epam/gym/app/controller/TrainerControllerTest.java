package com.epam.gym.app.controller;

import com.epam.gym.app.dto.trainee.TraineeListDTO;
import com.epam.gym.app.dto.trainer.TrainerGetDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.trainer.TrainerRegDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingFilterDTO;
import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.service.TrainerService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.epam.gym.app.utils.Constants.TRAINEE_TRAINERS_REST_URL;
import static com.epam.gym.app.utils.Constants.TRAINER_REST_URL;
import static com.epam.gym.app.utils.Constants.TRAININGS_REST_URL;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TrainerController.class)
@DisplayName("TrainerControllerTest")
class TrainerControllerTest {

    @MockBean
    private TrainerService trainerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("registerTrainer() method should register Trainer and return OK status when TrainerRegDTO is correct")
    @WithMockUser
    void registerTrainer_shouldRegisterTrainerAndReturnOkStatus_whenTrainerRegDTOIsCorrect() throws Exception {

        String firstname = "firstname";
        String lastname = "lastname";
        String specialization = "Yoga";

        TrainerRegDTO trainerRegDTO = TrainerRegDTO.builder()
                .firstname(firstname)
                .lastname(lastname)
                .specialization(specialization)
                .build();

        String username = "firstname.lastname";
        String password = "1234567890";
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .username(username)
                .password(password)
                .build();

        given(trainerService.save(any(TrainerRegDTO.class))).willReturn(userLoginDTO);
        ResultActions response = mockMvc.perform(post(TRAINER_REST_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerRegDTO)));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.password", is(password)));
    }

    @Test
    @DisplayName("getTrainer() method should return Trainer when Trainer with username is present")
    @WithMockUser
    void getTrainer_shouldReturnTrainer_whenUserWithUsernameIsPresent() throws Exception {

        String username = "firstname.lastname";
        String firstname = "firstname";
        String lastname = "lastname";
        boolean isActive = true;
        Set<TraineeListDTO> trainees = new HashSet<>();

        TrainerGetDTO trainerGetDTO = TrainerGetDTO.builder()
                .firstname(firstname)
                .lastname(lastname)
                .isActive(isActive)
                .trainees(trainees)
                .build();

        given(trainerService.find(anyString())).willReturn(trainerGetDTO);
        ResultActions response = mockMvc.perform(get(TRAINER_REST_URL + "/{name}", username));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.trainees.size()", is(trainees.size())))
                .andExpect(jsonPath("$.firstname", is(firstname)))
                .andExpect(jsonPath("$.lastname", is(lastname)))
                .andExpect(jsonPath("$.isActive", is(isActive)));
    }

    @Test
    @DisplayName("updateTrainer() method should update Trainer when TrainerUpdDTO is correct")
    @WithMockUser
    void updateTrainer_shouldUpdateTrainer_whenTrainerUpdDTOIsCorrect() throws Exception {

        String username = "firstname.lastname";
        String firstname = "firstname";
        String lastname = "lastname";
        boolean isActive = true;

        TrainerUpdDTO trainerUpdDTO = TrainerUpdDTO.builder()
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .isActive(isActive)
                .build();

        given(trainerService.update(any(TrainerUpdDTO.class))).willReturn(trainerUpdDTO);
        ResultActions response = mockMvc.perform(put(TRAINER_REST_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerUpdDTO)));

        response.andDo(print())
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.firstname", is(firstname)))
                .andExpect(jsonPath("$.lastname", is(lastname)))
                .andExpect(jsonPath("$.isActive", is(isActive)));
    }

    @Test
    @DisplayName("getNotAssignedOnTraineeActiveTrainers() method should return list of Trainers not assigned on Trainee")
    @WithMockUser
    void getNotAssignedOnTraineeActiveTrainers_shouldListOfTrainersNotAssignedOnTrainee() throws Exception {

        String traineeUsername = "firstname.lastname";
        List<TrainerListDTO> trainers = new ArrayList<>();

        given(trainerService.getTrainersListNotAssignedOnTrainee(anyString())).willReturn(trainers);
        ResultActions response = mockMvc.perform(get(TRAINER_REST_URL + TRAINEE_TRAINERS_REST_URL)
                .param("traineeUsername", traineeUsername));

        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(trainers.size())));
    }

    @Test
    @DisplayName("getTrainerTrainingList() method should return list of Trainer's Trainings")
    @WithMockUser
    void getTrainerTrainingList_shouldReturnListOfTrainerTrainings() throws Exception {

        String username = "firstname.lastname";
        List<TrainerTrainingDTO> trainings = new ArrayList<>();

        given(trainerService.getTrainingsList(any(TrainerTrainingFilterDTO.class))).willReturn(trainings);
        ResultActions response = mockMvc.perform(get(TRAINER_REST_URL + TRAININGS_REST_URL)
                .param("username", username));

        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(trainings.size())));
    }

    @Test
    @DisplayName("activateDeactivateTrainer() method should change Trainer's isActive status")
    @WithMockUser
    void activateDeactivateTrainer_shouldChangeTrainerIsActiveStatus() throws Exception {

        String username = "firstname.lastname";
        boolean isActive = true;

        willDoNothing().given(trainerService).activateDeactivate(username, isActive);
        ResultActions response = mockMvc.perform(patch(TRAINER_REST_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("username", username)
                .param("isActive", String.valueOf(isActive)));

        response.andDo(print())
                .andExpect(status().isOk());
    }
}

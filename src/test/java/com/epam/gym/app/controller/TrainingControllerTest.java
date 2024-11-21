package com.epam.gym.app.controller;

import com.epam.gym.app.dto.training.TrainingDTO;
import com.epam.gym.app.service.TrainingService;
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

import static com.epam.gym.app.utils.Constants.TRAINING_REST_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(TrainingController.class)
@DisplayName("TrainingControllerTest")
class TrainingControllerTest {

    @MockBean
    TrainingService trainingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    TrainingDTO trainingDTO;

    @BeforeEach
    public void setup() {

        trainingDTO = TrainingDTO.builder()
                .name("Morning Yoga")
                .typeName("Yoga")
                .date("2024-10-10")
                .duration(60)
                .traineeUsername("TraineeUsername")
                .trainerUsername("TrainerUsername")
                .build();
    }

    @Test
    @DisplayName("addTraining() method should add Training and return OK status when TrainingDTO is correct")
    @WithMockUser
    void addTraining_shouldAddTrainingAndReturnOkStatus_whenTrainingDTOIsCorrect() throws Exception {

        given(trainingService.save(any(TrainingDTO.class))).willReturn(trainingDTO);
        ResultActions response = mockMvc.perform(post(TRAINING_REST_URL)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainingDTO)));

        response.andDo(print()).andExpect(status().isOk());
    }
}

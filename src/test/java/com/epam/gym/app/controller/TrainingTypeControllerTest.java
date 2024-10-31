package com.epam.gym.app.controller;

import com.epam.gym.app.dto.training_type.TrainingTypeDTO;
import com.epam.gym.app.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingTypeController.class)
@DisplayName("TrainingTypeControllerTest")
class TrainingTypeControllerTest {

    @MockBean
    private TrainingTypeService trainingTypeService;

    @Autowired
    private MockMvc mockMvc;

    TrainingTypeDTO trainingTypeDTO;

    @BeforeEach
    public void setup() {

        trainingTypeDTO = TrainingTypeDTO
                .builder()
                .id(1L).name("Fitness")
                .build();
    }

    @Test
    @DisplayName("getTrainingTypes() method should return list of TrainingTypes when some is present")
    void getTrainingTypes_shouldReturnListOfTrainingTypes_whenSomeIsPresent() throws Exception {

        List<TrainingTypeDTO> employeesList = List.of(trainingTypeDTO);

        given(trainingTypeService.findAll()).willReturn(employeesList);
        ResultActions response = mockMvc.perform(get("/training-types"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(employeesList.size())))
                .andExpect(jsonPath("$[0].id", is(Math.toIntExact(trainingTypeDTO.getId()))))
                .andExpect(jsonPath("$[0].name", is(trainingTypeDTO.getName())));

    }
}

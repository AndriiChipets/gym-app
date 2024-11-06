package com.epam.gym.app.controller;

import com.epam.gym.app.annotation.Authenticated;
import com.epam.gym.app.dto.training.TrainingDTO;
import com.epam.gym.app.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.epam.gym.app.utils.Constants.TRAINING_REST_URL;

@RestController
@RequestMapping(value = TRAINING_REST_URL)
@AllArgsConstructor
@Tag(name = "Training Controller", description = "Operations related to the Training")
public class TrainingController {

    TrainingService trainingService;

    @PostMapping
    @Authenticated
    @Operation(summary = "Add new Training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Training successfully added"),
            @ApiResponse(responseCode = "404", description = "New Training is not added"),
            @ApiResponse(responseCode = "501", description = "Network Authentication Required")
    })
    @ResponseStatus(HttpStatus.OK)
    public void addTraining(@Valid @RequestBody TrainingDTO trainingDTO) {
        trainingService.save(trainingDTO);
    }
}

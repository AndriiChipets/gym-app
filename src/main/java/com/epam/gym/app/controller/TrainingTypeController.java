package com.epam.gym.app.controller;

import com.epam.gym.app.dto.training_type.TrainingTypeDTO;
import com.epam.gym.app.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.epam.gym.app.utils.Constants.TRAINING_TYPE_REST_URL;

@RestController
@RequestMapping(value = TRAINING_TYPE_REST_URL)
@AllArgsConstructor
@Tag(name = "Training type Controller", description = "Operations related to the Training type")
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get list of Training types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List of training types successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "List is not retrieved"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    @ResponseStatus(HttpStatus.OK)
    public List<TrainingTypeDTO> getTrainingTypes() {
        return trainingTypeService.findAll();
    }
}

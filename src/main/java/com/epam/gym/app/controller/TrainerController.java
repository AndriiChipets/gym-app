package com.epam.gym.app.controller;

import com.epam.gym.app.dto.trainer.TrainerGetDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.trainer.TrainerRegDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingFilterDTO;
import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.dto.user.AuthResponse;
import com.epam.gym.app.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.epam.gym.app.utils.Constants.TRAINEE_TRAINERS_REST_URL;
import static com.epam.gym.app.utils.Constants.TRAINER_REST_URL;
import static com.epam.gym.app.utils.Constants.TRAININGS_REST_URL;

@RestController
@RequestMapping(value = TRAINER_REST_URL)
@AllArgsConstructor
@Tag(name = "Trainer Controller", description = "Operations related to the Trainer")
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping
    @Operation(summary = "Register new Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Trainer successfully registered"),
            @ApiResponse(responseCode = "404", description = "New Trainer is not registered")
    })
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse registerTrainer(@Valid @RequestBody TrainerRegDTO trainerDto) {
        return trainerService.save(trainerDto);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainer successfully found"),
            @ApiResponse(responseCode = "404", description = "Trainer with provided password and username is not found"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public TrainerGetDTO getTrainer(@PathVariable("name") @NotBlank String username) {
        return trainerService.find(username);
    }

    @PutMapping
    @Operation(summary = "Update Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainer successfully updated"),
            @ApiResponse(responseCode = "404", description = "Trainer is not updated"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public TrainerUpdDTO updateTrainer(@Valid @RequestBody TrainerUpdDTO trainerUpdDTO) {
        return trainerService.update(trainerUpdDTO);
    }

    @GetMapping(TRAINEE_TRAINERS_REST_URL)
    @Operation(summary = "Get List of Trainer's Trainees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List of Trainer's Trainees successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "List of Trainer's Trainees is not retrieved"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public List<TrainerListDTO> getNotAssignedOnTraineeActiveTrainers(@NotBlank @RequestParam String traineeUsername) {
        return trainerService.getTrainersListNotAssignedOnTrainee(traineeUsername);
    }

    @GetMapping(TRAININGS_REST_URL)
    @Operation(summary = "Get List of Trainer's Trainings filtered by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List of Trainer's Trainings successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "List of Trainer's Trainings is not retrieved"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public List<TrainerTrainingDTO> getTrainerTrainingsList(
            @RequestParam @NotBlank String username,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) String traineeUsername) {

        TrainerTrainingFilterDTO trainingFilterDTO = TrainerTrainingFilterDTO.builder()
                .username(username)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .traineeUsername(traineeUsername)
                .build();

        return trainerService.getTrainingsList(trainingFilterDTO);
    }

    @PatchMapping
    @Operation(summary = "Activate or deactivate Trainer's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The status of Trainer's profile successfully changed"),
            @ApiResponse(responseCode = "404", description = "The status of Trainer's profile is not changed"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    @ResponseStatus(HttpStatus.OK)
    public void activateDeactivateTrainer(
            @RequestParam @NotBlank String username,
            @RequestParam @NotNull boolean isActive) {

        trainerService.activateDeactivate(username, isActive);
    }
}

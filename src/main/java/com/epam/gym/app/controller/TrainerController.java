package com.epam.gym.app.controller;

import com.epam.gym.app.annotation.Authenticated;
import com.epam.gym.app.dto.trainer.TrainerGetDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.trainer.TrainerRegDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingFilterDTO;
import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Trainer Controller", description = "Operations related to the Trainer")
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping("/trainer")
    @Operation(summary = "Register new Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Trainer successfully registered"),
            @ApiResponse(responseCode = "404", description = "New Trainer is not registered")
    })
    @ResponseStatus(HttpStatus.OK)
    public UserLoginDTO registerTrainer(@Valid @RequestBody TrainerRegDTO trainerDto) {
        return trainerService.save(trainerDto);
    }

    @Authenticated
    @GetMapping("/trainer")
    @Operation(summary = "Get Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainer successfully found"),
            @ApiResponse(responseCode = "404", description = "Trainer with provided password and username is not found"),
            @ApiResponse(responseCode = "501", description = "Network Authentication Required")
    })
    public TrainerGetDTO getTrainer(@NotBlank @RequestParam String username) {
        return trainerService.find(username);
    }

    @Authenticated
    @PutMapping("/trainer")
    @Operation(summary = "Update Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainer successfully updated"),
            @ApiResponse(responseCode = "404", description = "Trainer is not updated"),
            @ApiResponse(responseCode = "501", description = "Network Authentication Required")
    })
    public TrainerUpdDTO updateTrainer(@Valid @RequestBody TrainerUpdDTO trainerUpdDTO) {
        return trainerService.update(trainerUpdDTO);
    }

    @Authenticated
    @GetMapping("/trainer/trainees")
    @Operation(summary = "Get List of Trainer's Trainees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List of Trainer's Trainees successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "List of Trainer's Trainees is not retrieved"),
            @ApiResponse(responseCode = "501", description = "Network Authentication Required")
    })
    public List<TrainerListDTO> getNotAssignedOnTraineeActiveTrainers(@NotBlank @RequestParam String traineeUsername) {
        return trainerService.getTrainersListNotAssignedOnTrainee(traineeUsername);
    }

    @Authenticated
    @GetMapping("/trainer/trainings")
    @Operation(summary = "Get List of Trainer's Trainings filtered by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List of Trainer's Trainings successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "List of Trainer's Trainings is not retrieved"),
            @ApiResponse(responseCode = "501", description = "Network Authentication Required")
    })
    public List<TrainerTrainingDTO> getTrainerTrainingsList(
            @Valid @RequestBody TrainerTrainingFilterDTO trainingFilterDTO) {

        return trainerService.getTrainingsList(trainingFilterDTO);
    }

    @Authenticated
    @PatchMapping("/trainer")
    @Operation(summary = "Activate or deactivate Trainer's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The status of Trainer's profile successfully changed"),
            @ApiResponse(responseCode = "404", description = "The status of Trainer's profile is not changed"),
            @ApiResponse(responseCode = "501", description = "Network Authentication Required")
    })
    @ResponseStatus(HttpStatus.OK)
    public void activateDeactivateTrainer(
            @NotBlank @RequestParam String username,
            @NotNull @RequestParam boolean isActive) {

        trainerService.activateDeactivate(username, isActive);
    }
}

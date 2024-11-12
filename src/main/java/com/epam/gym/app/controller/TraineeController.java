package com.epam.gym.app.controller;

import com.epam.gym.app.annotation.Authenticated;
import com.epam.gym.app.dto.trainee.TraineeGetDTO;
import com.epam.gym.app.dto.trainee.TraineeRegDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainerListDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingFilterDTO;
import com.epam.gym.app.dto.trainee.TraineeUpdDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.service.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import static com.epam.gym.app.utils.Constants.TRAINEE_REST_URL;
import static com.epam.gym.app.utils.Constants.TRAINERS_REST_URL;
import static com.epam.gym.app.utils.Constants.TRAININGS_REST_URL;

@RestController
@RequestMapping(value = TRAINEE_REST_URL)
@AllArgsConstructor
@Tag(name = "Trainee Controller", description = "Operations related to the Trainee")
public class TraineeController {

    private final TraineeService traineeService;

    @PostMapping
    @Operation(summary = "Register new Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Trainee successfully registered"),
            @ApiResponse(responseCode = "404", description = "New Trainee is not registered")
    })
    @ResponseStatus(HttpStatus.OK)
    public UserLoginDTO registerTrainee(@Valid @RequestBody TraineeRegDTO traineeDto) {
        return traineeService.save(traineeDto);
    }

    @GetMapping("/{name}")
    @Authenticated
    @Operation(summary = "Get Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee successfully found"),
            @ApiResponse(responseCode = "404", description = "Trainee with provided password and username is not found"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public TraineeGetDTO getTrainee(@PathVariable("name") @NotBlank String username) {
        return traineeService.find(username);
    }

    @PutMapping
    @Authenticated
    @Operation(summary = "Update Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee successfully updated"),
            @ApiResponse(responseCode = "404", description = "Trainee is not updated"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public TraineeUpdDTO updateTrainee(@Valid @RequestBody TraineeUpdDTO traineeUpdDTO) {
        return traineeService.update(traineeUpdDTO);
    }

    @DeleteMapping
    @Authenticated
    @Operation(summary = "Delete Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Trainee is not deleted"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrainee(@RequestParam @NotBlank String username) {
        traineeService.delete(username);
    }

    @PutMapping(TRAINERS_REST_URL)
    @Authenticated
    @Operation(summary = "Update the list of Trainee's Trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List of Trainee's Trainers successfully updated"),
            @ApiResponse(responseCode = "404", description = "List of Trainee's Trainers is not updated"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public List<TrainerListDTO> updateTraineeTrainerList(
            @Valid @RequestBody TraineeTrainerListDTO traineeTrainerListDTO) {

        return traineeService.updateTraineeTrainerList(traineeTrainerListDTO);
    }

    @GetMapping(TRAININGS_REST_URL)
    @Authenticated
    @Operation(summary = "Get List of Trainee's Trainings filtered by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "List of Trainee's Trainings successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "List of Trainee's Trainings is not retrieved"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    public List<TraineeTrainingDTO> getTraineeTrainingList(
            @RequestParam @NotBlank String username,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) String trainerUsername,
            @RequestParam(required = false) String typeName) {

        TraineeTrainingFilterDTO trainingFilterDTO = TraineeTrainingFilterDTO.builder()
                .username(username)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .trainerUsername(trainerUsername)
                .typeName(typeName)
                .build();

        return traineeService.getTrainingsList(trainingFilterDTO);
    }

    @PatchMapping
    @Authenticated
    @Operation(summary = "Activate or deactivate Trainee's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The status of Trainee's profile successfully changed"),
            @ApiResponse(responseCode = "404", description = "The status of Trainee's profile is not changed"),
            @ApiResponse(responseCode = "401", description = "User Authentication Required")
    })
    @ResponseStatus(HttpStatus.OK)
    public void activateDeactivateTrainee(
            @RequestParam @NotBlank String username,
            @RequestParam @NotNull boolean isActive) {

        traineeService.activateDeactivate(username, isActive);
    }
}

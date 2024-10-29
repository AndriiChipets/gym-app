package com.epam.gym.app.controller;

import com.epam.gym.app.dto.trainee.TraineeGetDTO;
import com.epam.gym.app.dto.trainee.TraineeRegDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainerListDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingDTO;
import com.epam.gym.app.dto.trainee.TraineeTrainingFilterDTO;
import com.epam.gym.app.dto.trainee.TraineeUpdDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.service.TraineeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class TraineeController {

    private final TraineeService traineeService;

    @PostMapping("/trainee")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginDTO registerTrainee(@Valid @RequestBody TraineeRegDTO traineeDto) {
        return traineeService.save(traineeDto);
    }

    @GetMapping("/trainee")
    public TraineeGetDTO getTrainee(@NotBlank @RequestParam String username) {
        return traineeService.find(username);
    }

    @PutMapping("/trainee")
    public TraineeUpdDTO updateTrainee(@Valid @RequestBody TraineeUpdDTO traineeUpdDTO) {
        return traineeService.update(traineeUpdDTO);
    }

    @DeleteMapping("/trainee")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrainee(@NotBlank @RequestParam String username) {
        traineeService.delete(username);
    }

    @PutMapping("/trainee/trainers")
    public List<TrainerListDTO> updateTraineeTrainerList(
            @Valid @RequestBody TraineeTrainerListDTO traineeTrainerListDTO) {

        return traineeService.updateTraineeTrainerList(traineeTrainerListDTO);
    }

    @GetMapping("/trainee/trainings")
    public List<TraineeTrainingDTO> getTraineeTrainingList(
            @Valid @RequestBody TraineeTrainingFilterDTO trainingFilterDTO) {

        return traineeService.getTrainingsList(trainingFilterDTO);
    }

    @PatchMapping("/trainee")
    @ResponseStatus(HttpStatus.OK)
    public void activateDeactivateTrainee(
            @NotBlank @RequestParam String username,
            @NotNull @RequestParam boolean isActive) {

        traineeService.activateDeactivate(username, isActive);
    }
}

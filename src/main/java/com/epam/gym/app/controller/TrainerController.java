package com.epam.gym.app.controller;

import com.epam.gym.app.dto.trainer.TrainerGetDTO;
import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.dto.trainer.TrainerRegDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingDTO;
import com.epam.gym.app.dto.trainer.TrainerTrainingFilterDTO;
import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.service.TrainerService;
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
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping("/trainer")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginDTO registerTrainer(@RequestBody TrainerRegDTO trainerDto) {
        return trainerService.save(trainerDto);
    }

    @GetMapping("/trainer")
    public TrainerGetDTO getTrainer(@RequestParam String username) {
        return trainerService.find(username);
    }

    @PutMapping("/trainer")
    public TrainerUpdDTO updateTrainer(@RequestBody TrainerUpdDTO trainerUpdDTO) {
        return trainerService.update(trainerUpdDTO);
    }

    @GetMapping("/trainer/trainees")
    public List<TrainerListDTO> getNotAssignedOnTraineeActiveTrainers(@RequestParam String traineeUsername) {
        return trainerService.getTrainersListNotAssignedOnTrainee(traineeUsername);
    }

    @GetMapping("/trainer/trainings")
    public List<TrainerTrainingDTO> getTrainerTraainingsList(@RequestBody TrainerTrainingFilterDTO trainingFilterDTO) {
        return trainerService.getTrainingsList(trainingFilterDTO);
    }

    @PatchMapping("/trainer")
    @ResponseStatus(HttpStatus.OK)
    public void activateDeactivateTrainer(@RequestParam String username, @RequestParam boolean isActive) {
        trainerService.activateDeactivate(username, isActive);
    }
}

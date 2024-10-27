package com.epam.gym.app.controller;

import com.epam.gym.app.dto.TrainingDTO;
import com.epam.gym.app.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TrainingController {

    TrainingService trainingService;

    @PostMapping("/training")
    @ResponseStatus(HttpStatus.OK)
    public void addTraining(@RequestBody TrainingDTO trainingDTO) {
        trainingService.save(trainingDTO);
    }
}

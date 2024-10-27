package com.epam.gym.app.controller;

import com.epam.gym.app.dto.TrainingTypeDTO;
import com.epam.gym.app.service.TrainingTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    @GetMapping("/training-types")
    @ResponseStatus(HttpStatus.OK)
    public List<TrainingTypeDTO> getTrainingTypes() {
        return trainingTypeService.findAll();
    }
}

package com.epam.gym.app.controller;

import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.dto.TrainingDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

//@RestController
public class TrainerController {

    //    Trainer Registration (POST method)
//    a. Request
//    I. First Name (required)
//    II. Last Name (required)
//    III. Specialization (required) (Training type reference)
//    b. Response
//    I. Username
//    II. Password
    @PostMapping()
    public String registerTrainee() {
        return null;
    }

//    Get Trainer Profile (GET method)
//    a. Request
//    I. Username (required)
//    b. Response
//    I. First Name
//    II. Last Name
//   III. Specialization (Training type reference)
//    IV. Is Active
//     V. Trainees List
//       1. Trainee Username
//       2. Trainee First Name
//       3. Trainee Last Name

    @GetMapping()
    public TrainerDto getTrainer() {
        return null;
    }

    //    Update Trainer Profile (PUT method)
//    a. Request
//    I. Username (required)
//    II. First Name (required)
//    III. Last Name (required)
//    IV. Specialization (read only) (Training type reference)
//    V. Is Active (required)
//    b. Response
//    I. Username
//    II. First Name
//    III. Last Name
//    IV. Specialization (Training type reference)
//    V. Is Active
//    VI. Trainees List
//       1. Trainee Username
//       2. Trainee First Name
//       3. Trainee Last Name
    @PutMapping()
    public TrainerDto updateTrainer() {
        return null;
    }

//    Get not assigned on trainee active trainers. (GET method)
//    a. Request
//    I. Username (required)
//    b. Response
//    I. Trainer Username
//    II. Trainer First Name
//    III. Trainer Last Name
//    IV. Trainer Specialization (Training type reference)

    @GetMapping()
    public List<TrainerDto> getNotAssignedOnTraineeActiveTrainers() {
        return null;
    }

    //    Get Trainer Trainings List (GET method)
//    a. Request
//    I. Username (required)
//    II. Period From (optional)
//    III. Period To (optional)
//    IV. Trainee Name (optional)
//    b. Response
//    I. Training Name
//    II. Training Date
//    III. Training Type
//    IV. Training Duration
//    V. Trainee Name
    @GetMapping()
    public List<TrainingDTO> getTrainerTraainingsList() {
        return null;
    }

//    Activate/De-Activate Trainer (PATCH method)
//    a. Request
//    I. Username (required)
//    II. Is Active (required)
//    b. Response
//    I. 200 OK
    @PatchMapping()
    public void activateDeactivateTrainer() {

    }
}

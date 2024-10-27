package com.epam.gym.app.controller;

import com.epam.gym.app.dto.TraineeDto;
import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.dto.TrainingDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

//@RestController
public class TraineeController {

    //    Trainee Registration (POST method)
//    a. Request
//    I. First Name (required)
//    II. Last Name (required)
//    III. Date of Birth (optional)
//    IV. Address (optional)
//    b. Response
//    I. Username
//    II. Password
    @PostMapping()
    String register() {
        return null;
    }

    //    Get Trainee Profile (GET method)
//    a. Request
//    I. Username (required)
//    b. Response
//    I. First Name
//    II. Last Name
//    III. Date of Birth
//    IV. Address
//    V. Is Active
//    VI. Trainers List
//        1. Trainer Username
//        2. Trainer First Name
//        3. Trainer Last Name
//        4. Trainer Specialization (Training type reference)
    @GetMapping()
    public TraineeDto getTrainee() {
        return null;
    }

    //    Update Trainee Profile (PUT method)
//    a. Request
//   I. Username (required)
//   II. First Name (required)
//   III. Last Name (required)
//   IV. Date of Birth (optional)
//    V. Address (optional)
//    VI. Is Active (required)
//    b. Response
//    I. Username
//    II. First Name
//    III. Last Name
//    IV. Date of Birth
//    V. Address
//    VI. Is Active
//    VII. Trainers List
//       1. Trainer Username
//       2. Trainer First Name
//       3. Trainer Last Name
//       4. Trainer Specialization (Training type reference)
    @PutMapping()
    public TraineeDto updateTrainee() {
        return null;
    }

    //    Delete Trainee Profile (DELETE method)
//    a. Request
//    I. Username (required)
//    b. Response
//    I. 200 OK
    @DeleteMapping()
    public void deleteTrainee() {

    }

    //    Update Trainee's Trainer List (PUT method)
//    a. Request
//    I. Trainee Username
//    II. Trainers List (required)
//     1. Trainer Username (required)
//    b. Response
//    I. Trainers List
//    1. Trainer Username
//    2. Trainer First Name
//    3. Trainer Last Name
//    4. Trainer Specialization (Training type reference)
    @PutMapping()
    public List<TrainerDto> updateTraineeTrainerList() {
        return null;
    }

    //    Get Trainee Trainings List (GET method)
//    a. Request
//    I. Username (required)
//    II. Period From (optional)
//    III. Period To (optional)
//    IV. Trainer Name (optional)
//    V. Training Type (optional)
//    b. Response
//    I. Training Name
//    II. Training Date
//    III. Training Type
//    IV. Training Duration
//    V. Trainer Name
    @GetMapping()
    public List<TrainingDTO> getTraineeTrainingList() {
        return null;
    }

    //    Activate/De-Activate Trainee (PATCH method)
//    a. Request
//    I. Username (required)
//    II. Is Active (required)
//    b. Response
//    I. 200 OK
    @PatchMapping()
    public void ActivateDeactivateTrainee() {

    }
}

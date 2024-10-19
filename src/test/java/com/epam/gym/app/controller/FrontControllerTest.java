package com.epam.gym.app.controller;

import com.epam.gym.app.dto.TraineeDto;
import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.dto.TrainingDto;
import com.epam.gym.app.dto.TrainingTypeDto;
import com.epam.gym.app.service.TraineeService;
import com.epam.gym.app.service.TrainerService;
import com.epam.gym.app.service.TrainingService;
import com.epam.gym.app.service.TrainingTypeService;
import com.epam.gym.app.utils.UserUtil;
import com.epam.gym.app.view.ViewProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FrontController.class})
@DisplayName("FrontControllerTest")
class FrontControllerTest {

    @MockBean
    TraineeService traineeService;

    @MockBean
    TrainerService trainerService;

    @MockBean
    TrainingService trainingService;

    @MockBean
    TrainingTypeService typeService;

    @MockBean
    ViewProvider viewProvider;

    @Autowired
    FrontController frontController;

    @Test
    @DisplayName("run() should invoke createTrainee() when choice is 1")
    void run_shouldInvokeMethodCreateTrainee_whenChoiceIs1() {

        int userChoice = 1;
        int actChoice = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        LocalDate dateOfBirth = LocalDate.now();
        String address = "address";
        String username = "firstname.lastname";
        String password = "123456789";
        TraineeDto trainee = TraineeDto.builder()
                .firstname(firstname)
                .lastname(lastname)
                .dateOfBirth(dateOfBirth)
                .isActive(true)
                .address(address)
                .username(username)
                .password(password)
                .build();

        try (MockedStatic<UserUtil> utilClassMockedStatic = mockStatic(UserUtil.class)) {
            when(viewProvider.read())
                    .thenReturn(username)
                    .thenReturn(password)
                    .thenReturn(firstname)
                    .thenReturn(lastname)
                    .thenReturn(dateOfBirth.toString())
                    .thenReturn(address);
            utilClassMockedStatic.when(UserUtil::generateRandomPassword).thenReturn(password);
            utilClassMockedStatic.when(() -> UserUtil.generateUsername(
                    anyString(), anyString(), anyList(), anyList())).thenReturn(username);
            when(viewProvider.readInt())
                    .thenReturn(userChoice)
                    .thenReturn(actChoice)
                    .thenReturn(0);
            when(traineeService.login(anyString(), anyString())).thenReturn(true);
            when(traineeService.save(any(TraineeDto.class))).thenReturn(trainee);
            frontController.run();
        }

        verify(traineeService).save(trainee);
    }

    @Test
    @DisplayName("run() should invoke updateTrainee() when choice is 2")
    void run_shouldInvokeMethodUpdateTrainee_whenChoiceIs2() {

        int userChoice = 1;
        int actChoice = 2;
        long traineeId = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        LocalDate dateOfBirth = LocalDate.now();
        String address = "address";
        String username = "firstname.lastname";
        String password = "123456789";
        TraineeDto beforeUpd = TraineeDto.builder().id(traineeId).build();
        TraineeDto trainee = TraineeDto.builder()
                .id(traineeId)
                .firstname(firstname)
                .lastname(lastname)
                .dateOfBirth(dateOfBirth)
                .isActive(false)
                .address(address)
                .username(username)
                .password(password)
                .build();

        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password)
                .thenReturn(username)
                .thenReturn(firstname)
                .thenReturn(lastname)
                .thenReturn(dateOfBirth.toString())
                .thenReturn(address);
        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.find(anyString())).thenReturn(beforeUpd);
        when(traineeService.save(any(TraineeDto.class))).thenReturn(trainee);
        frontController.run();

        verify(traineeService).save(any(TraineeDto.class));
    }

    @Test
    @DisplayName("run() should invoke deleteTrainee() when choice is 3")
    void run_shouldInvokeMethodDeleteTrainee_whenChoiceIs3() {

        int userChoice = 1;
        int actChoice = 3;
        String username = "firstname.lastname";

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(viewProvider.read()).thenReturn(username);
        frontController.run();

        verify(traineeService).delete(username);
    }

    @Test
    @DisplayName("run() should invoke selectTrainee() when choice is 4")
    void run_shouldInvokeMethodSelectTrainee_whenChoiceIs4() {

        int userChoice = 1;
        int actChoice = 4;
        String username = "firstname.lastname";
        TraineeDto trainee = TraineeDto.builder().build();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read()).thenReturn(username);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.find(anyString())).thenReturn(trainee);
        frontController.run();

        verify(traineeService).find(username);
    }

    @Test
    @DisplayName("run() should invoke changeTraineePassword() when choice is 5")
    void run_shouldInvokeMethodChangeTraineePassword_whenChoiceIs5() {

        int userChoice = 1;
        int actChoice = 5;
        String username = "firstname.lastname";
        String password = "123456789";
        String repeatedPassword = password;
        TraineeDto trainee = TraineeDto.builder().build();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password)
                .thenReturn(repeatedPassword);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.find(anyString())).thenReturn(trainee);
        when(traineeService.save(any(TraineeDto.class))).thenReturn(trainee);
        frontController.run();

        verify(traineeService).save(any(TraineeDto.class));
    }

    @Test
    @DisplayName("run() should invoke activateDeactivateTrainee() when choice is 6")
    void run_shouldInvokeMethodActivateDeactivateTrainee_whenChoiceIs6() {

        int userChoice = 1;
        int actChoice = 6;
        boolean isActive = false;
        String username = "firstname.lastname";
        TraineeDto trainee = TraineeDto.builder().isActive(isActive).build();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read()).thenReturn(username);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.find(anyString())).thenReturn(trainee);
        when(traineeService.save(any(TraineeDto.class))).thenReturn(trainee);
        frontController.run();

        verify(traineeService).save(any(TraineeDto.class));
    }

    @Test
    @DisplayName("run() should invoke getTraineeTrainingsList() when choice is 7")
    void run_shouldInvokeMethodGetTraineeTrainingsList_whenChoiceIs7() {

        int userChoice = 1;
        int actChoice = 7;
        String password = "123456789";
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(10);
        String traineeUsername = "trainee.username";
        String trainerUsername = "trainer.username";
        TraineeDto trainee = TraineeDto.builder().build();
        List<TrainingDto> trainings = new ArrayList<>();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read())
                .thenReturn(traineeUsername)
                .thenReturn(password)
                .thenReturn(traineeUsername)
                .thenReturn(dateFrom.toString())
                .thenReturn(dateTo.toString())
                .thenReturn(trainerUsername);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.find(anyString())).thenReturn(trainee);
        when(trainerService.findAll()).thenReturn(new ArrayList<>());
        when(traineeService.getTrainingsList(
                anyString(),
                any(LocalDate.class),
                any(LocalDate.class),
                anyString())).thenReturn(trainings);
        frontController.run();

        verify(traineeService).getTrainingsList(traineeUsername, dateFrom, dateTo, trainerUsername);
    }

    @Test
    @DisplayName("run() should invoke getTrainersListNotAssignedOnTrainee() when choice is 8")
    void run_shouldInvokeMethodGetTrainersListNotAssignedOnTrainee8() {

        int userChoice = 1;
        int actChoice = 8;
        String password = "123456789";
        String traineeUsername = "trainee.username";
        TraineeDto trainee = TraineeDto.builder().build();
        List<TrainerDto> trainers = new ArrayList<>();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read())
                .thenReturn(traineeUsername)
                .thenReturn(password)
                .thenReturn(traineeUsername);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.find(anyString())).thenReturn(trainee);
        when(trainerService.getTrainersListNotAssignedOnTrainee(anyString())).thenReturn(trainers);
        frontController.run();

        verify(trainerService).getTrainersListNotAssignedOnTrainee(traineeUsername);
    }

    @Test
    @DisplayName("run() should invoke addTrainerToTraineeTrainersList() when choice is 9")
    void run_shouldInvokeMethodAddTrainerToTraineeTrainersListList9() {

        int userChoice = 1;
        int actChoice = 9;
        String password = "123456789";
        String traineeUsername = "trainee.username";
        String trainerUsername = "trainer.username";
        TraineeDto trainee = TraineeDto.builder().build();
        TrainerDto trainer = TrainerDto.builder().build();
        List<TrainerDto> trainers = new ArrayList<>();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read())
                .thenReturn(traineeUsername)
                .thenReturn(password)
                .thenReturn(traineeUsername)
                .thenReturn(trainerUsername);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.find(anyString())).thenReturn(trainee);
        when(trainerService.find(anyString())).thenReturn(trainer);
        when(traineeService.addTrainerToTraineeList(
                any(TraineeDto.class), any(TrainerDto.class))).thenReturn(trainers);
        frontController.run();

        verify(traineeService).addTrainerToTraineeList(trainee, trainer);
    }

    @Test
    @DisplayName("run() should invoke removeTrainerFromTraineeTrainersList() when choice is 10")
    void run_shouldInvokeMethodRemoveTrainerFromTraineeTrainersListList10() {

        int userChoice = 1;
        int actChoice = 10;
        String password = "123456789";
        String traineeUsername = "trainee.username";
        String trainerUsername = "trainer.username";
        TraineeDto trainee = TraineeDto.builder().build();
        TrainerDto trainer = TrainerDto.builder().build();
        List<TrainerDto> trainers = new ArrayList<>();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read())
                .thenReturn(traineeUsername)
                .thenReturn(password)
                .thenReturn(traineeUsername)
                .thenReturn(trainerUsername);
        when(traineeService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.find(anyString())).thenReturn(trainee);
        when(trainerService.find(anyString())).thenReturn(trainer);
        when(traineeService.removeTrainerToTraineeList(
                any(TraineeDto.class), any(TrainerDto.class))).thenReturn(trainers);
        frontController.run();

        verify(traineeService).removeTrainerToTraineeList(trainee, trainer);
    }

    @Test
    @DisplayName("run() should invoke createTrainer() when choice is 11")
    void run_shouldInvokeMethodCreateTrainer_whenChoiceIs11() {

        int userChoice = 2;
        int actChoice = 11;
        String firstname = "firstname";
        String lastname = "lastname";
        String typename = "trainingType";
        TrainingTypeDto specialization = TrainingTypeDto.builder().name(typename).build();
        String username = "firstname.lastname";
        String password = "123456789";
        TrainerDto trainer = TrainerDto.builder()
                .firstname(firstname)
                .lastname(lastname)
                .specialization(specialization)
                .isActive(true)
                .username(username)
                .password(password)
                .build();

        try (MockedStatic<UserUtil> utilClassMockedStatic = mockStatic(UserUtil.class)) {
            when(viewProvider.read())
                    .thenReturn(username)
                    .thenReturn(password)
                    .thenReturn(firstname)
                    .thenReturn(lastname)
                    .thenReturn(typename);
            utilClassMockedStatic.when(UserUtil::generateRandomPassword).thenReturn(password);
            utilClassMockedStatic.when(() -> UserUtil.generateUsername(
                    anyString(), anyString(), anyList(), anyList())).thenReturn(username);
            when(viewProvider.readInt())
                    .thenReturn(userChoice)
                    .thenReturn(actChoice)
                    .thenReturn(0);
            when(trainerService.login(anyString(), anyString())).thenReturn(true);
            when(typeService.findAll()).thenReturn(new ArrayList<>());
            when(typeService.find(anyString())).thenReturn(specialization);
            when(trainerService.save(any(TrainerDto.class))).thenReturn(trainer);
            frontController.run();
        }

        verify(trainerService).save(trainer);
    }

    @Test
    @DisplayName("run() should invoke updateTrainer() when choice is 12")
    void run_shouldInvokeMethodUpdateTrainer_whenChoiceIs12() {

        int userChoice = 2;
        int actChoice = 12;
        long trainerId = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        String typename = "trainingType";
        TrainingTypeDto specialization = TrainingTypeDto.builder().name(typename).build();
        String username = "firstname.lastname";
        String password = "123456789";
        TrainerDto beforeUpd = TrainerDto.builder().id(trainerId).build();
        TrainerDto trainer = TrainerDto.builder()
                .id(trainerId)
                .firstname(firstname)
                .lastname(lastname)
                .specialization(specialization)
                .isActive(true)
                .username(username)
                .password(password)
                .build();

        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password)
                .thenReturn(username)
                .thenReturn(firstname)
                .thenReturn(lastname)
                .thenReturn(typename);
        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(trainerService.find(anyString())).thenReturn(beforeUpd);
        when(typeService.findAll()).thenReturn(new ArrayList<>());
        when(typeService.find(anyString())).thenReturn(specialization);
        when(trainerService.save(any(TrainerDto.class))).thenReturn(trainer);
        frontController.run();

        verify(trainerService).save(any(TrainerDto.class));
    }


    @Test
    @DisplayName("run() should invoke selectTrainer() when choice is 13")
    void run_shouldInvokeMethodSelectTrainer_whenChoiceIs13() {

        int userChoice = 2;
        int actChoice = 13;
        String username = "firstname.lastname";
        TrainerDto trainer = TrainerDto.builder().build();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read()).thenReturn(username);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(trainerService.find(anyString())).thenReturn(trainer);
        frontController.run();

        verify(trainerService).find(username);
    }

    @Test
    @DisplayName("run() should invoke changeTrainerPassword() when choice is 14")
    void run_shouldInvokeMethodChangeTrainerPassword_whenChoiceIs14() {

        int userChoice = 2;
        int actChoice = 14;
        String username = "firstname.lastname";
        String password = "123456789";
        String repeatedPassword = password;
        TrainerDto trainer = TrainerDto.builder().build();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password)
                .thenReturn(repeatedPassword);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(trainerService.find(anyString())).thenReturn(trainer);
        when(trainerService.save(any(TrainerDto.class))).thenReturn(trainer);
        frontController.run();

        verify(trainerService).save(any(TrainerDto.class));
    }

    @Test
    @DisplayName("run() should invoke activateDeactivateTrainer() when choice is 15")
    void run_shouldInvokeMethodActivateDeactivateTrainer_whenChoiceIs15() {

        int userChoice = 2;
        int actChoice = 15;
        boolean isActive = false;
        String username = "firstname.lastname";
        TrainerDto trainer = TrainerDto.builder().isActive(isActive).build();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read()).thenReturn(username);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(trainerService.find(anyString())).thenReturn(trainer);
        when(trainerService.save(any(TrainerDto.class))).thenReturn(trainer);
        frontController.run();

        verify(trainerService).save(any(TrainerDto.class));
    }

    @Test
    @DisplayName("run() should invoke getTrainerTrainingsList() when choice is 16")
    void run_shouldInvokeMethodGetTrainerTrainingsList_whenChoiceIs16() {

        int userChoice = 2;
        int actChoice = 16;
        String password = "123456789";
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now().plusDays(10);
        String trainerUsername = "trainer.username";
        String traineeUsername = "trainee.username";
        TrainerDto trainer = TrainerDto.builder().build();
        List<TrainingDto> trainings = new ArrayList<>();

        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(0);
        when(viewProvider.read())
                .thenReturn(trainerUsername)
                .thenReturn(password)
                .thenReturn(trainerUsername)
                .thenReturn(dateFrom.toString())
                .thenReturn(dateTo.toString())
                .thenReturn(traineeUsername);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(trainerService.find(anyString())).thenReturn(trainer);
        when(trainerService.findAll()).thenReturn(new ArrayList<>());
        when(trainerService.getTrainingsList(
                anyString(),
                any(LocalDate.class),
                any(LocalDate.class),
                anyString())).thenReturn(trainings);
        frontController.run();

        verify(trainerService).getTrainingsList(trainerUsername, dateFrom, dateTo, traineeUsername);
    }

    @Test
    @DisplayName("run() should invoke createTraining() when choice is 17")
    void run_shouldInvokeMethodCreateTraining_whenChoiceIs17() {

        int userChoice = 2;
        int actChoice = 17;
        String username = "firstname.lastname";
        String password = "123456789";
        String trainingName = "training";
        String typename = "trainingType";
        String traineeUsername = "trainee.username";
        String trainerUsername = "trainer.username";
        LocalDate date = LocalDate.now();
        TrainingTypeDto trainingType = TrainingTypeDto.builder().name(typename).build();
        TrainerDto trainer = TrainerDto.builder().username(trainerUsername).build();
        TraineeDto trainee = TraineeDto.builder().username(traineeUsername).build();
        int duration = 30;

        TrainingDto training = TrainingDto.builder()
                .name(trainingName)
                .type(trainingType)
                .date(date)
                .trainer(trainer)
                .trainee(trainee)
                .duration(duration)
                .build();

        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password)
                .thenReturn(trainingName)
                .thenReturn(typename)
                .thenReturn(date.toString())
                .thenReturn(trainerUsername)
                .thenReturn(traineeUsername);
        when(viewProvider.readInt())
                .thenReturn(userChoice)
                .thenReturn(actChoice)
                .thenReturn(duration)
                .thenReturn(0);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(typeService.find(anyString())).thenReturn(trainingType);
        when(trainerService.find(anyString())).thenReturn(trainer);
        when(traineeService.find(anyString())).thenReturn(trainee);
        when(trainingService.save(training)).thenReturn(training);
        frontController.run();

        verify(trainingService).save(training);
    }


    @Test
    @DisplayName("run() should invoke selectTraining() when choice is 18")
    void run_shouldInvokeMethodSelectTraining_whenChoiceIs18() {

        int userChoice = 2;
        int actChoice = 18;
        long trainingId = 100;
        TrainingDto training = TrainingDto.builder()
                .id(trainingId)
                .date(LocalDate.now())
                .type(TrainingTypeDto.builder().build())
                .trainer(TrainerDto.builder().build())
                .trainee(TraineeDto.builder().build())
                .build();

        when(viewProvider.readInt()).thenReturn(userChoice).thenReturn(actChoice).thenReturn(0);
        when(viewProvider.readLong()).thenReturn(trainingId);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(trainingService.find(anyLong())).thenReturn(training);
        frontController.run();

        verify(trainingService).find(trainingId);
    }

    @Test
    @DisplayName("run() should invoke selectAllTrainee() when choice is 19")
    void run_shouldInvokeMethodSelectAllTrainee_whenChoiceIs19() {

        int userChoice = 2;
        int actChoice = 19;
        String username = "firstname.lastname";
        String password = "123456789";
        List<TraineeDto> trainees = new ArrayList<>();

        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password);
        when(viewProvider.readInt()).thenReturn(userChoice).thenReturn(actChoice).thenReturn(0);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(traineeService.findAll()).thenReturn(trainees);
        frontController.run();

        verify(traineeService).findAll();
    }

    @Test
    @DisplayName("run() should invoke selectAllTrainer() when choice is 20")
    void run_shouldInvokeMethodSelectAllTrainer_whenChoiceIs20() {

        int userChoice = 2;
        int actChoice = 20;
        String username = "firstname.lastname";
        String password = "123456789";
        List<TrainerDto> trainers = new ArrayList<>();

        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password);
        when(viewProvider.readInt()).thenReturn(userChoice).thenReturn(actChoice).thenReturn(0);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(trainerService.findAll()).thenReturn(trainers);
        frontController.run();

        verify(trainerService).findAll();
    }

    @Test
    @DisplayName("run() should invoke selectAllTraining() when choice is 21")
    void run_shouldInvokeMethodSelectAllTraining_whenChoiceIs21() {

        int userChoice = 2;
        int actChoice = 21;
        String username = "firstname.lastname";
        String password = "123456789";
        List<TrainingDto> trainings = new ArrayList<>();

        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password);
        when(viewProvider.readInt()).thenReturn(userChoice).thenReturn(actChoice).thenReturn(0);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(trainingService.findAll()).thenReturn(trainings);
        frontController.run();

        verify(trainingService).findAll();
    }

    @Test
    @DisplayName("run() should invoke selectAllTrainingTypes() when choice is 22")
    void run_shouldInvokeMethodSelectAllTrainingTypes_whenChoiceIs22() {

        int userChoice = 2;
        int actChoice = 22;
        String username = "firstname.lastname";
        String password = "123456789";
        List<TrainingTypeDto> types = new ArrayList<>();

        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password);
        when(viewProvider.readInt()).thenReturn(userChoice).thenReturn(actChoice).thenReturn(0);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        when(typeService.findAll()).thenReturn(types);
        frontController.run();

        verify(typeService).findAll();
    }

    @Test
    @DisplayName("run() should print message when choice is incorrect")
    void run_shouldPrintMessage_whenChoiceIsIncorrect() {

        int userChoice = 2;
        int actChoice = 1_000_000;
        String username = "firstname.lastname";
        String password = "123456789";

        when(viewProvider.read())
                .thenReturn(username)
                .thenReturn(password);
        when(viewProvider.readInt()).thenReturn(userChoice).thenReturn(actChoice).thenReturn(0);
        when(trainerService.login(anyString(), anyString())).thenReturn(true);
        frontController.run();

        verify(viewProvider).printMessage(FrontController.WRONG_CHOICE_MESSAGE);
    }
}

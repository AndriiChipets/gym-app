package com.epam.gym.app.controller;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.entity.TrainingType;
import com.epam.gym.app.service.TraineeService;
import com.epam.gym.app.service.TrainerService;
import com.epam.gym.app.service.TrainingService;
import com.epam.gym.app.utils.UserUtil;
import com.epam.gym.app.view.ViewProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    ViewProvider viewProvider;

    @Autowired
    FrontController frontController;

    @Test
    @DisplayName("run() should invoke createTrainee() when choice is 1")
    void run_shouldInvokeMethodCreateTrainee_whenChoiceIs1() {

        int choice = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        LocalDate dateOfBirth = LocalDate.now();
        String address = "address";
        String username = "firstname.lastname";
        String password = "123456789";
        Trainee trainee = Trainee.builder()
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
                    .thenReturn(firstname)
                    .thenReturn(lastname)
                    .thenReturn(dateOfBirth.toString())
                    .thenReturn(address);
            utilClassMockedStatic.when(UserUtil::generateRandomPassword).thenReturn(password);
            utilClassMockedStatic.when(() -> UserUtil.generateUsername(
                    anyString(), anyString(), anyList(), anyList())).thenReturn(username);
            when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
            when(traineeService.save(trainee)).thenReturn(trainee);
            frontController.run();
        }

        verify(traineeService).save(trainee);
    }

    @Test
    @DisplayName("run() should invoke updateTrainee() when choice is 2")
    void run_shouldInvokeMethodUpdateTrainee_whenChoiceIs2() {

        int choice = 2;
        long traineeId = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        LocalDate dateOfBirth = LocalDate.now();
        String address = "address";
        String username = "firstname.lastname";
        String password = "123456789";
        Trainee beforeUpd = Trainee.builder().id(traineeId).build();
        Trainee trainee = Trainee.builder()
                .id(traineeId)
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
                    .thenReturn(firstname)
                    .thenReturn(lastname)
                    .thenReturn(dateOfBirth.toString())
                    .thenReturn(address);
            when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
            when(viewProvider.readLong()).thenReturn(traineeId);
            utilClassMockedStatic.when(UserUtil::generateRandomPassword).thenReturn(password);
            utilClassMockedStatic.when(() -> UserUtil.generateUsername(
                    anyString(), anyString(), anyList(), anyList())).thenReturn(username);
            when(traineeService.find(anyLong())).thenReturn(beforeUpd);
            when(traineeService.update(trainee)).thenReturn(trainee);
            frontController.run();
        }

        verify(traineeService).update(trainee);
    }

    @Test
    @DisplayName("run() should invoke deleteTrainee() when choice is 3")
    void run_shouldInvokeMethodDeleteTrainee_whenChoiceIs3() {

        int choice = 3;
        long traineeId = 100;

        when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
        when(viewProvider.readLong()).thenReturn(traineeId);
        frontController.run();

        verify(traineeService, times(1)).delete(traineeId);
    }

    @Test
    @DisplayName("run() should invoke selectTrainee() when choice is 4")
    void run_shouldInvokeMethodSelectTrainee_whenChoiceIs4() {

        int choice = 4;
        long traineeId = 100;
        Trainee trainee = Trainee.builder().build();

        when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
        when(viewProvider.readLong()).thenReturn(traineeId);
        when(traineeService.find(anyLong())).thenReturn(trainee);
        frontController.run();

        verify(traineeService).find(traineeId);
    }

    @Test
    @DisplayName("run() should invoke createTrainer() when choice is 5")
    void run_shouldInvokeMethodCreateTrainer_whenChoiceIs5() {

        int choice = 5;
        String firstname = "firstname";
        String lastname = "lastname";
        TrainingType trainingType = TrainingType.FITNESS;
        String username = "firstname.lastname";
        String password = "123456789";
        Trainer trainer = Trainer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .trainingType(trainingType)
                .username(username)
                .password(password)
                .build();

        try (MockedStatic<UserUtil> utilClassMockedStatic = mockStatic(UserUtil.class)) {
            when(viewProvider.read())
                    .thenReturn(firstname)
                    .thenReturn(lastname)
                    .thenReturn(trainingType.toString());
            utilClassMockedStatic.when(UserUtil::generateRandomPassword).thenReturn(password);
            utilClassMockedStatic.when(() -> UserUtil.generateUsername(
                    anyString(), anyString(), anyList(), anyList())).thenReturn(username);
            when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
            when(trainerService.save(trainer)).thenReturn(trainer);
            frontController.run();
        }

        verify(trainerService).save(trainer);
    }

    @Test
    @DisplayName("run() should invoke updateTrainer() when choice is 6")
    void run_shouldInvokeMethodUpdateTrainer_whenChoiceIs6() {

        int choice = 6;
        long trainerId = 1;
        String firstname = "firstname";
        String lastname = "lastname";
        TrainingType trainingType = TrainingType.FITNESS;
        String username = "firstname.lastname";
        String password = "123456789";
        Trainer beforeUpd = Trainer.builder().id(trainerId).build();
        Trainer trainer = Trainer.builder()
                .id(trainerId)
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .trainingType(trainingType)
                .username(username)
                .password(password)
                .build();

        try (MockedStatic<UserUtil> utilClassMockedStatic = mockStatic(UserUtil.class)) {
            when(viewProvider.read())
                    .thenReturn(firstname)
                    .thenReturn(lastname)
                    .thenReturn(trainingType.toString());
            when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
            when(viewProvider.readLong()).thenReturn(trainerId);
            utilClassMockedStatic.when(UserUtil::generateRandomPassword).thenReturn(password);
            utilClassMockedStatic.when(() -> UserUtil.generateUsername(
                    anyString(), anyString(), anyList(), anyList())).thenReturn(username);
            when(trainerService.find(anyLong())).thenReturn(beforeUpd);
            when(trainerService.update(trainer)).thenReturn(trainer);
            frontController.run();

            verify(trainerService).update(trainer);
        }
    }

    @Test
    @DisplayName("run() should invoke selectTrainer() when choice is 7")
    void run_shouldInvokeMethodSelectTrainer_whenChoiceIs7() {

        int choice = 7;
        long trainerId = 100;
        Trainer trainer = Trainer.builder().build();

        when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
        when(viewProvider.readLong()).thenReturn(trainerId);
        when(trainerService.find(anyLong())).thenReturn(trainer);
        frontController.run();

        verify(trainerService).find(trainerId);
    }

    @Test
    @DisplayName("run() should invoke createTraining() when choice is 8")
    void run_shouldInvokeMethodCreateTraining_whenChoiceIs8() {

        int choice = 8;
        String name = "Training";
        String trainingType = "YOGA";
        String dateTime = "2024-12-12 12:12:12";
        long trainerId = 1L;
        long traineeId = 1L;
        int duration = 30;

        Trainer trainer = Trainer.builder().id(trainerId).build();
        Trainee trainee = Trainee.builder().id(traineeId).build();

        Training training = Training.builder()
                .name(name)
                .type(TrainingType.valueOf(trainingType))
                .date(LocalDateTime.parse(dateTime, UserUtil.FORMATTER))
                .trainer(trainer)
                .trainee(trainee)
                .duration(duration)
                .build();

        when(viewProvider.read())
                .thenReturn(name)
                .thenReturn(trainingType)
                .thenReturn(dateTime);
        when(viewProvider.readLong())
                .thenReturn(trainerId)
                .thenReturn(traineeId);
        when(viewProvider.readInt()).thenReturn(choice).thenReturn(duration).thenReturn(0);
        when(trainerService.find(anyLong())).thenReturn(trainer);
        when(traineeService.find(anyLong())).thenReturn(trainee);
        when(trainingService.save(training)).thenReturn(training);
        frontController.run();

        verify(trainingService).save(training);
    }

    @Test
    @DisplayName("run() should invoke selectTraining() when choice is 9")
    void run_shouldInvokeMethodSelectTraining_whenChoiceIs9() {

        int choice = 9;
        long trainingId = 100;
        Training training = Training.builder()
                .date(LocalDateTime.now())
                .type(TrainingType.FITNESS)
                .trainer(Trainer.builder().build())
                .trainee(Trainee.builder().build())
                .build();

        when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
        when(viewProvider.readLong()).thenReturn(trainingId);
        when(trainingService.find(anyLong())).thenReturn(training);
        frontController.run();

        verify(trainingService).find(trainingId);
    }

    @Test
    @DisplayName("run() should invoke selectAllTrainee() when choice is 10")
    void run_shouldInvokeMethodSelectAllTrainee_whenChoiceIs10() {

        int choice = 10;
        List<Trainee> trainees = new ArrayList<>();

        when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
        when(traineeService.findAll()).thenReturn(trainees);
        frontController.run();

        verify(traineeService).findAll();
    }

    @Test
    @DisplayName("run() should invoke selectAllTrainer() when choice is 11")
    void run_shouldInvokeMethodSelectAllTrainer_whenChoiceIs11() {

        int choice = 11;
        List<Trainer> trainers = new ArrayList<>();

        when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
        when(trainerService.findAll()).thenReturn(trainers);
        frontController.run();

        verify(trainerService).findAll();
    }

    @Test
    @DisplayName("run() should invoke selectAllTraining() when choice is 12")
    void run_shouldInvokeMethodSelectAllTraining_whenChoiceIs12() {

        int choice = 12;
        List<Training> trainings = new ArrayList<>();

        when(viewProvider.readInt()).thenReturn(choice).thenReturn(0);
        when(trainingService.findAll()).thenReturn(trainings);
        frontController.run();

        verify(trainingService).findAll();
    }

    @Test
    @DisplayName("run() should print message when choice is incorrect")
    void run_shouldPrintMessage_whenChoiceIsIncorrect() {

        long choice = 1_000_000;

        when(viewProvider.readInt()).thenReturn((int) choice).thenReturn(0);
        frontController.run();

        verify(viewProvider).printMessage(FrontController.WRONG_CHOICE_MESSAGE);
    }
}

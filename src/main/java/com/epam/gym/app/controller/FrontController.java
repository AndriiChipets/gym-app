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
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class FrontController {

    public static final String MENU = """
                        
            ============ Please, choose the operation ============
            1 -> Create Trainee
            2 -> Update Trainee
            3 -> Delete Trainee
            4 -> Select Trainee
            5 -> Create Trainer
            6 -> Update Trainer
            7 -> Select Trainer
            8 -> Create Training
            9 -> Select Training
            10 -> Select All Trainees
            11 -> Select All Trainers
            12 -> Select All Trainings
            0 -> To exit from the program""";
    public static final String WRONG_CHOICE_MESSAGE =
            "Please, choose from the MENU or enter \"0\" to exit from the application";
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final ViewProvider viewProvider;

    public void run() {
        boolean isWork = true;
        while (isWork) {
            viewProvider.printMessage(MENU);
            int choice = viewProvider.readInt();
            switch (choice) {
                case 0 -> isWork = false;
                case 1 -> createTrainee();
                case 2 -> updateTrainee();
                case 3 -> deleteTrainee();
                case 4 -> selectTrainee();
                case 5 -> createTrainer();
                case 6 -> updateTrainer();
                case 7 -> selectTrainer();
                case 8 -> createTraining();
                case 9 -> selectTraining();
                case 10 -> selectAllTrainee();
                case 11 -> selectAllTrainer();
                case 12 -> selectAllTraining();
                default -> viewProvider.printMessage(WRONG_CHOICE_MESSAGE);
            }
        }
    }

    private void createTrainee() {
        viewProvider.printMessage("Enter Trainee first name: ");
        String firstname = viewProvider.read();
        viewProvider.printMessage("Enter Trainee last name: ");
        String lastname = viewProvider.read();
        viewProvider.printMessage("Enter Trainee date of birth in format " + UserUtil.DATE_TEMPLATE);
        LocalDate dateOfBirth = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter Trainee address: ");
        String address = viewProvider.read();

        Trainee trainee = Trainee.builder()
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        String password = UserUtil.generateRandomPassword();
        String username = UserUtil.generateUsername(firstname, lastname,
                trainerService.findAll(), traineeService.findAll());

        trainee.setPassword(password);
        trainee.setUsername(username);

        Trainee savedTrainee = traineeService.save(trainee);
        viewProvider.printMessage(savedTrainee.toString());
    }

    private void updateTrainee() {
        viewProvider.printMessage("Enter Trainee id: ");
        long id = viewProvider.readLong();

        Trainee beforeUpd = traineeService.find(id);
        viewProvider.printMessage(beforeUpd.toString());

        viewProvider.printMessage("Enter Trainee first name: ");
        String firstname = viewProvider.read();
        viewProvider.printMessage("Enter Trainee last name: ");
        String lastname = viewProvider.read();
        viewProvider.printMessage("Enter Trainee date of birth in format " + UserUtil.DATE_TEMPLATE);
        LocalDate dateOfBirth = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter Trainee address: ");
        String address = viewProvider.read();

        Trainee trainee = Trainee.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        String password = UserUtil.generateRandomPassword();
        String username = UserUtil.generateUsername(firstname, lastname,
                trainerService.findAll(), traineeService.findAll());

        trainee.setPassword(password);
        trainee.setUsername(username);

        Trainee updatedTrainee = traineeService.update(trainee);
        viewProvider.printMessage(updatedTrainee.toString());
    }

    private void deleteTrainee() {
        viewProvider.printMessage("Enter Trainee id: ");
        long id = viewProvider.readLong();

        traineeService.delete(id);
    }

    private void selectTrainee() {
        viewProvider.printMessage("Enter Trainee id: ");
        long id = viewProvider.readLong();
        Trainee trainee = traineeService.find(id);

        viewProvider.printMessage(trainee.toString());
    }

    private void createTrainer() {
        viewProvider.printMessage("Enter Trainer first name: ");
        String firstname = viewProvider.read();
        viewProvider.printMessage("Enter Trainer last name: ");
        String lastname = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        viewProvider.printMessage(Arrays.asList(TrainingType.values()).toString());
        TrainingType trainingType = TrainingType.valueOf(viewProvider.read());

        Trainer trainer = Trainer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .trainingType(trainingType)
                .build();

        String password = UserUtil.generateRandomPassword();
        String username = UserUtil.generateUsername(firstname, lastname,
                trainerService.findAll(), traineeService.findAll());

        trainer.setPassword(password);
        trainer.setUsername(username);

        Trainer savedTrainer = trainerService.save(trainer);
        viewProvider.printMessage(savedTrainer.toString());
    }

    private void updateTrainer() {
        viewProvider.printMessage("Enter Trainer id: ");
        long id = viewProvider.readLong();

        Trainer beforeUpd = trainerService.find(id);
        viewProvider.printMessage(beforeUpd.toString());

        viewProvider.printMessage("Enter Trainer first name: ");
        String firstname = viewProvider.read();
        viewProvider.printMessage("Enter Trainer last name: ");
        String lastname = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        viewProvider.printMessage(Arrays.asList(TrainingType.values()).toString());
        TrainingType trainingType = TrainingType.valueOf(viewProvider.read());

        Trainer trainer = Trainer.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .trainingType(trainingType)
                .build();

        String password = UserUtil.generateRandomPassword();
        String username = UserUtil.generateUsername(firstname, lastname,
                trainerService.findAll(), traineeService.findAll());

        trainer.setPassword(password);
        trainer.setUsername(username);

        Trainer updatedTrainer = trainerService.update(trainer);
        viewProvider.printMessage(updatedTrainer.toString());
    }

    private void selectTrainer() {
        viewProvider.printMessage("Enter Trainer id: ");
        long id = viewProvider.readLong();
        Trainer trainer = trainerService.find(id);

        viewProvider.printMessage(trainer.toString());
    }

    private void createTraining() {
        viewProvider.printMessage("Enter Training name: ");
        String name = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        viewProvider.printMessage(Arrays.asList(TrainingType.values()).toString());
        TrainingType trainingType = TrainingType.valueOf(viewProvider.read());
        viewProvider.printMessage("Enter Training date and time in the format " + UserUtil.DATE_TIME_TEMPLATE);
        LocalDateTime dateTime = LocalDateTime.parse(viewProvider.read(), UserUtil.FORMATTER);
        viewProvider.printMessage("Enter Trainer id: ");
        long trainerId = viewProvider.readLong();
        Trainer trainer = trainerService.find(trainerId);
        viewProvider.printMessage("Enter Trainee id: ");
        long traineeId = viewProvider.readLong();
        Trainee trainee = traineeService.find(traineeId);
        viewProvider.printMessage("Enter Training duration in minutes: ");
        int duration = viewProvider.readInt();

        Training training = Training.builder()
                .name(name)
                .type(trainingType)
                .date(dateTime)
                .trainee(trainee)
                .trainer(trainer)
                .duration(duration)
                .build();

        Training savedTraining = trainingService.save(training);
        viewProvider.printMessage(savedTraining.toString());
    }

    private void selectTraining() {
        viewProvider.printMessage("Enter Training id: ");
        long id = viewProvider.readLong();
        Training training = trainingService.find(id);

        viewProvider.printMessage(training.toString());
    }

    private void selectAllTrainee() {
        List<Trainee> trainees = traineeService.findAll();
        viewProvider.printList(trainees);
    }

    private void selectAllTrainer() {
        List<Trainer> trainers = trainerService.findAll();
        viewProvider.printList(trainers);
    }

    private void selectAllTraining() {
        List<Training> trainings = trainingService.findAll();
        viewProvider.printList(trainings);
    }
}

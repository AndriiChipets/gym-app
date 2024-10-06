package com.epam.gym.app.controller;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.entity.TrainingType;
import com.epam.gym.app.service.TraineeService;
import com.epam.gym.app.service.TrainerService;
import com.epam.gym.app.service.TrainingService;
import com.epam.gym.app.view.ViewProvider;
import org.springframework.stereotype.Controller;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Controller
@AllArgsConstructor
public class FrontController {

    private static final String MENU = """
            
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
    private static final String WRONG_CHOICE_MESSAGE =
            "Please, choose from the MENU or enter \"0\" to exit from the application";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
        String firstName = viewProvider.read();
        viewProvider.printMessage("Enter Trainee last name: ");
        String lastName = viewProvider.read();
        viewProvider.printMessage("Enter Trainee date of birth in format YYYY-DD-MM: ");
        LocalDate dateOfBirth = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter Trainee address: ");
        String address = viewProvider.read();

        Trainee trainee = Trainee.builder()
                .firstname(firstName)
                .lastname(lastName)
                .isActive(true)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        traineeService.save(trainee);
    }

    private void updateTrainee() {
        viewProvider.printMessage("Enter Trainee id: ");
        long id = viewProvider.readLong();

        Trainee beforeUpd = traineeService.find(id);
        viewProvider.printMessage(beforeUpd.toString());

        viewProvider.printMessage("Enter Trainee first name: ");
        String firstName = viewProvider.read();
        viewProvider.printMessage("Enter Trainee last name: ");
        String lastName = viewProvider.read();
        viewProvider.printMessage("Enter Trainee date of birth in format yyyy-MM-dd: ");
        LocalDate dateOfBirth = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter Trainee address: ");
        String address = viewProvider.read();

        Trainee trainee = Trainee.builder()
                .id(id)
                .firstname(firstName)
                .lastname(lastName)
                .isActive(true)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        traineeService.update(trainee);
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
        String firstName = viewProvider.read();
        viewProvider.printMessage("Enter Trainer last name: ");
        String lastName = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        viewProvider.printMessage(Arrays.asList(TrainingType.values()).toString());
        TrainingType trainingType = TrainingType.valueOf(viewProvider.read());

        Trainer trainer = Trainer.builder()
                .firstname(firstName)
                .lastname(lastName)
                .isActive(true)
                .trainingType(trainingType)
                .build();

        trainerService.save(trainer);
    }

    private void updateTrainer() {
        viewProvider.printMessage("Enter Trainer id: ");
        long id = viewProvider.readLong();

        Trainer beforeUpd = trainerService.find(id);
        viewProvider.printMessage(beforeUpd.toString());

        viewProvider.printMessage("Enter Trainer first name: ");
        String firstName = viewProvider.read();
        viewProvider.printMessage("Enter Trainer last name: ");
        String lastName = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        viewProvider.printMessage(Arrays.asList(TrainingType.values()).toString());
        TrainingType trainingType = TrainingType.valueOf(viewProvider.read());

        Trainer trainer = Trainer.builder()
                .id(id)
                .firstname(firstName)
                .lastname(lastName)
                .isActive(true)
                .trainingType(trainingType)
                .build();

        trainerService.save(trainer);
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
        viewProvider.printMessage("Enter Training date and time in the format yyyy-MM-dd HH:mm:ss: ");
        LocalDateTime dateTime = LocalDateTime.parse(viewProvider.read(), FORMATTER);
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

        trainingService.save(training);
    }

    private void selectTraining() {
        viewProvider.printMessage("Enter Training id: ");
        long id = viewProvider.readLong();
        Training training = trainingService.find(id);

        viewProvider.printMessage(training.toString());
    }

    private void selectAllTrainee() {
        traineeService.findAll().forEach(System.out::println);
    }

    private void selectAllTrainer() {
        trainerService.findAll().forEach(System.out::println);
    }

    private void selectAllTraining() {
        trainingService.findAll().forEach(System.out::println);
    }
}

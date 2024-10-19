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
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
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
            5 -> Change Trainee password
            6 -> Activate/deactivate Trainee
            7 -> Get Trainee Trainings list
            8 -> Get Trainers List not assigned on Trainee
            9 -> Add Trainer To Trainee's Trainers List
            10 -> Remove Trainer From Trainee's Trainers List
            11 -> Create Trainer
            12 -> Update Trainer
            13 -> Select Trainer
            14 -> Change Trainer password
            15 -> Activate/Deactivate Trainer
            16 -> Get Trainer Trainings list
            17 -> Create Training
            18 -> Select Training
            19 -> Select All Trainee
            20 -> Select All Trainers
            21 -> Select All Trainings
            22 -> Select All TrainingTypes
            0 -> To exit from the program""";

    public static final String TRAINER_OR_TRAINEE_MENU = """
                        
            ============ Please, choose who are you ============
            1 -> Trainee
            2 -> Trainer """;

    public static final String ACTIVATE_OR_DEACTIVATE_MENU = """
                        
            ============ Please, choose activate or deactivate user ============
            1 -> activate
            2 -> deactivate""";

    public static final String SHOULD_ADD_TRAINER_TO_TRAINEE_LIST_MENU = """
                        
            ============ Please, choose how to update Trainee's Trainer List ============
            1 -> add Trainer
            2 -> remove Trainer""";

    public static final String WRONG_CHOICE_MESSAGE =
            "Please, choose from the MENU or enter \"0\" to exit from the application";
    public static final String WRONG_USER_CHOICE_MESSAGE =
            "Please, choose from the MENU";

    public static final String TRAINEE = "Trainee";
    public static final String TRAINER = "Trainer";
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;
    private final ViewProvider viewProvider;

    public void run() {
        String user = identifyUser();

        if (!login(user)) {
            viewProvider.printMessage("You are not registered, please register");
            addNewUser(user);
        }

        boolean isWork = true;
        while (isWork) {
            viewProvider.printMessage(MENU);
            int choice = viewProvider.readInt();
            switch (choice) {
                case 0 -> isWork = false;
                case 1 -> createTrainee();
                case 2 -> updateTrainee();
                case 3 -> deleteTraineeByUserName();
                case 4 -> selectTraineeByUserName();
                case 5 -> changeTraineePassword();
                case 6 -> activateDeactivateTrainee();
                case 7 -> getTraineeTrainingsList();
                case 8 -> getTrainersListNotAssignedOnTrainee();
                case 9 -> addTrainerToTraineeTrainersList();
                case 10 -> removeTrainerFromTraineeTrainersList();
                case 11 -> createTrainer();
                case 12 -> updateTrainer();
                case 13 -> selectTrainerByUserName();
                case 14 -> changeTrainerPassword();
                case 15 -> activateDeactivateTrainer();
                case 16 -> getTrainerTrainingsList();
                case 17 -> createTraining();
                case 18 -> selectTraining();
                case 19 -> selectAllTrainees();
                case 20 -> selectAllTrainers();
                case 21 -> selectAllTrainings();
                case 22 -> selectAllTrainingTypes();
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

        TraineeDto traineeDto = TraineeDto.builder()
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        String password = UserUtil.generateRandomPassword();
        String username = UserUtil.generateUsername(firstname, lastname,
                trainerService.findAll(), traineeService.findAll());

        traineeDto.setPassword(password);
        traineeDto.setUsername(username);

        traineeService.save(traineeDto);
    }

    private void updateTrainee() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();

        TraineeDto beforeUpd = traineeService.find(username);

        viewProvider.printMessage("Enter Trainee first name: ");
        String firstname = viewProvider.read();
        viewProvider.printMessage("Enter Trainee last name: ");
        String lastname = viewProvider.read();
        viewProvider.printMessage("Enter Trainee date of birth in format " + UserUtil.DATE_TEMPLATE);
        LocalDate dateOfBirth = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter Trainee address: ");
        String address = viewProvider.read();
        boolean isActive = changeToActive();
        long id = beforeUpd.getId();
        String password = beforeUpd.getPassword();

        TraineeDto trainee = TraineeDto.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .isActive(isActive)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        traineeService.save(trainee);
    }

    private void deleteTraineeByUserName() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();
        traineeService.delete(username);
    }

    private void selectTraineeByUserName() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();
        traineeService.find(username);
    }

    private void changeTraineePassword() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();

        TraineeDto trainee = traineeService.find(username);

        viewProvider.printMessage("Enter new password" + UserUtil.PASSWORD_LENGTH + " characters length:");
        String password = viewProvider.read();
        viewProvider.printMessage("Repeat new password: ");
        String repeatPassword = viewProvider.read();
        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("password and repeated password are not equal");
        }
        trainee.setPassword(password);
        traineeService.save(trainee);
    }

    private void activateDeactivateTrainee() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();
        TraineeDto trainee = traineeService.find(username);

        boolean isActive = changeToActive();
        trainee.setIsActive(isActive);

        traineeService.save(trainee);
    }

    private void getTraineeTrainingsList() {
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();

        traineeService.find(traineeUsername);

        viewProvider.printMessage("Enter START date from in format" + UserUtil.DATE_TEMPLATE);
        LocalDate dateFrom = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter END date in format" + UserUtil.DATE_TEMPLATE);
        LocalDate dateTo = LocalDate.parse(viewProvider.read());

        selectAllTrainers();

        viewProvider.printMessage("Enter Trainer name: ");
        String trainerUsername = viewProvider.read();

        List<TrainingDto> trainings = traineeService.getTrainingsList(traineeUsername, dateFrom, dateTo, trainerUsername);

        viewProvider.printList(trainings);
    }

    private void getTrainersListNotAssignedOnTrainee() {
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();

        traineeService.find(traineeUsername);

        List<TrainerDto> trainers =
                trainerService.getTrainersListNotAssignedOnTrainee(traineeUsername);

        viewProvider.printList(trainers);
    }

    private void addTrainerToTraineeTrainersList() {
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();
        TraineeDto traineeDto = traineeService.find(traineeUsername);

        viewProvider.printMessage("Enter Trainer username: ");
        String trainerUsername = viewProvider.read();
        TrainerDto trainerDto = trainerService.find(trainerUsername);

        traineeService.addTrainerToTraineeList(traineeDto, trainerDto);
    }

    private void removeTrainerFromTraineeTrainersList() {
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();
        TraineeDto traineeDto = traineeService.find(traineeUsername);

        viewProvider.printMessage("Enter Trainer username: ");
        String trainerUsername = viewProvider.read();
        TrainerDto trainerDto = trainerService.find(trainerUsername);

        traineeService.removeTrainerToTraineeList(traineeDto, trainerDto);
    }

    private void createTrainer() {
        viewProvider.printMessage("Enter Trainer first name: ");
        String firstname = viewProvider.read();
        viewProvider.printMessage("Enter Trainer last name: ");
        String lastname = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        selectAllTrainingTypes();
        viewProvider.printMessage("Enter TrainingType name: ");
        String trainingTypeName = viewProvider.read();
        TrainingTypeDto typeDto = trainingTypeService.find(trainingTypeName);

        TrainerDto trainer = TrainerDto.builder()
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .specialization(typeDto)
                .build();

        String password = UserUtil.generateRandomPassword();
        String username = UserUtil.generateUsername(firstname, lastname,
                trainerService.findAll(), traineeService.findAll());

        trainer.setPassword(password);
        trainer.setUsername(username);

        trainerService.save(trainer);
    }

    private void updateTrainer() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();
        TrainerDto beforeUpd = trainerService.find(username);

        viewProvider.printMessage("Enter Trainer first name: ");
        String firstname = viewProvider.read();
        viewProvider.printMessage("Enter Trainer last name: ");
        String lastname = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        selectAllTrainingTypes();
        viewProvider.printMessage("Enter TrainingType name: ");
        String trainingTypeName = viewProvider.read();
        TrainingTypeDto typeDto = trainingTypeService.find(trainingTypeName);
        boolean isActive = changeToActive();
        long id = beforeUpd.getId();
        String password = beforeUpd.getPassword();

        TrainerDto trainer = TrainerDto.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .isActive(isActive)
                .specialization(typeDto)
                .password(password)
                .build();

        trainerService.save(trainer);
    }

    private void selectTrainerByUserName() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();
        TrainerDto trainer = trainerService.find(username);
        viewProvider.printMessage(trainer.toString());
    }

    private void changeTrainerPassword() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();

        TrainerDto trainer = trainerService.find(username);
        viewProvider.printMessage(trainer.toString());

        viewProvider.printMessage("Enter new password" + UserUtil.PASSWORD_LENGTH + " characters length:");
        String password = viewProvider.read();
        viewProvider.printMessage("Repeat new password: ");
        String repeatPassword = viewProvider.read();

        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("password and repeated password are not equal");
        }

        trainer.setPassword(password);
        TrainerDto updatedTrainer = trainerService.save(trainer);

        viewProvider.printMessage(updatedTrainer.toString());
    }

    private void activateDeactivateTrainer() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();

        TrainerDto trainer = trainerService.find(username);
        viewProvider.printMessage(trainer.toString());

        boolean isActive = changeToActive();

        trainer.setIsActive(isActive);
        TrainerDto updatedTrainer = trainerService.save(trainer);

        viewProvider.printMessage(updatedTrainer.toString());
    }

    private void getTrainerTrainingsList() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();

        TrainerDto trainer = trainerService.find(username);
        viewProvider.printMessage(trainer.toString());

        viewProvider.printMessage("Enter START date from in format" + UserUtil.DATE_TEMPLATE);
        LocalDate dateFrom = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter END date in format" + UserUtil.DATE_TEMPLATE);
        LocalDate dateTo = LocalDate.parse(viewProvider.read());

        selectAllTrainees();

        viewProvider.printMessage("Enter Trainee name: ");
        String traineeName = viewProvider.read();

        List<TrainingDto> trainings = trainerService.getTrainingsList(username, dateFrom, dateTo, traineeName);

        viewProvider.printList(trainings);
    }

    private void createTraining() {
        viewProvider.printMessage("Enter Training name: ");
        String name = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        String trainingTypeName = viewProvider.read();
        TrainingTypeDto typeDto = trainingTypeService.find(trainingTypeName);
        viewProvider.printMessage("Enter Training date and time in the format " + UserUtil.DATE_TEMPLATE);
        LocalDate date = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter Trainer username: ");
        String trainerUsername = viewProvider.read();
        TrainerDto trainer = trainerService.find(trainerUsername);
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();
        TraineeDto trainee = traineeService.find(traineeUsername);
        viewProvider.printMessage("Enter Training duration in minutes: ");
        int duration = viewProvider.readInt();

        TrainingDto training = TrainingDto.builder()
                .name(name)
                .type(typeDto)
                .date(date)
                .trainee(trainee)
                .trainer(trainer)
                .duration(duration)
                .build();

        trainingService.save(training);
    }

    private void selectTraining() {
        viewProvider.printMessage("Enter Training id: ");
        long id = viewProvider.readLong();
        TrainingDto training = trainingService.find(id);
        viewProvider.printMessage(training.toString());
    }

    private void selectAllTrainees() {
        List<TraineeDto> trainees = traineeService.findAll();
        viewProvider.printList(trainees);
    }

    private void selectAllTrainers() {
        List<TrainerDto> trainers = trainerService.findAll();
        viewProvider.printList(trainers);
    }

    private void selectAllTrainings() {
        List<TrainingDto> trainings = trainingService.findAll();
        viewProvider.printList(trainings);
    }

    private void selectAllTrainingTypes() {
        List<TrainingTypeDto> types = trainingTypeService.findAll();
        viewProvider.printList(types);
    }

    private String identifyUser() {
        while (true) {
            viewProvider.printMessage(TRAINER_OR_TRAINEE_MENU);
            int choice = viewProvider.readInt();
            switch (choice) {
                case 1 -> {
                    return TRAINEE;
                }
                case 2 -> {
                    return TRAINER;
                }
                default -> viewProvider.printMessage(WRONG_USER_CHOICE_MESSAGE);
            }
        }
    }

    private boolean login(String user) {
        viewProvider.printMessage("To work with program, please login");
        viewProvider.printMessage("Enter username:");
        String username = viewProvider.read();
        viewProvider.printMessage("Enter password: ");
        String password = viewProvider.read();
        return user.equals(TRAINEE) ? traineeService.login(username, password) : trainerService.login(username, password);
    }

    private void addNewUser(String user) {
        switch (user) {
            case TRAINEE -> createTrainee();
            case TRAINER -> createTrainer();
            default -> identifyUser();
        }
    }

    private boolean changeToActive() {
        viewProvider.printMessage(ACTIVATE_OR_DEACTIVATE_MENU);
        int choice = viewProvider.readInt();
        return choice == 1;
    }
}

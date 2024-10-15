package com.epam.gym.app.controller;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.entity.TrainingType;
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
import java.util.Set;

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
            9 -> Update Trainee's Trainers list
            10 -> Create Trainer
            11 -> Update Trainer
            12 -> Select Trainer
            13 -> Change Trainer password
            14 -> Activate/Deactivate Trainer
            15 -> Get Trainer Trainings list
            16 -> Create Training
            17 -> Select Training
            18 -> Select All Trainee
            19 -> Select All Trainers
            20 -> Select All Trainings
            21 -> Select All TrainingTypes
            0 -> To exit from the program""";

    public static final String TRAINER_OR_TRAINEE_MENU = """
                        
            ============ Please, choose who are you ============
            1 -> Trainee
            2 -> Trainer
            0 -> To exit from the program""";

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
                case 9 -> updateTraineeTrainersList();
                case 10 -> createTrainer();
                case 11 -> updateTrainer();
                case 12 -> selectTrainerByUserName();
                case 13 -> changeTrainerPassword();
                case 14 -> activateDeactivateTrainer();
                case 15 -> getTrainerTrainingsByUserName();
                case 16 -> createTraining();
                case 17 -> selectTraining();
                case 18 -> selectAllTrainees();
                case 19 -> selectAllTrainers();
                case 20 -> selectAllTrainings();
                case 21 -> selectAllTrainingTypes();
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
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();

        Trainee beforeUpd = traineeService.find(username);
        viewProvider.printMessage(beforeUpd.toString());

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

        Trainee trainee = Trainee.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .password(password)
                .isActive(isActive)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .build();

        Trainee updatedTrainee = traineeService.save(trainee);
        viewProvider.printMessage(updatedTrainee.toString());
    }

    private void deleteTraineeByUserName() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();

        traineeService.delete(username);
    }

    private void selectTraineeByUserName() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();
        Trainee trainee = traineeService.find(username);

        viewProvider.printMessage(trainee.toString());
    }

    private void changeTraineePassword() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();

        Trainee trainee = traineeService.find(username);
        viewProvider.printMessage(trainee.toString());

        viewProvider.printMessage("Enter new password: ");
        String password = viewProvider.read();
        viewProvider.printMessage("Repeat new password: ");
        String repeatPassword = viewProvider.read();

        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("password and repeated password are not equal");
        }

        trainee.setPassword(password);
        Trainee updatedTrainee = traineeService.save(trainee);

        viewProvider.printMessage(updatedTrainee.toString());
    }

    private void activateDeactivateTrainee() {
        viewProvider.printMessage("Enter Trainee username: ");
        String username = viewProvider.read();

        Trainee trainee = traineeService.find(username);
        viewProvider.printMessage(trainee.toString());

        boolean isActive = changeToActive();

        trainee.setIsActive(isActive);
        Trainee updatedTrainee = traineeService.save(trainee);

        viewProvider.printMessage(updatedTrainee.toString());
    }

    private void getTraineeTrainingsList() {
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();

        Trainee trainee = traineeService.find(traineeUsername);
        viewProvider.printMessage(trainee.toString());

        viewProvider.printMessage("Enter START date from in format" + UserUtil.DATE_TEMPLATE);
        LocalDate dateFrom = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter END date in format" + UserUtil.DATE_TEMPLATE);
        LocalDate dateTo = LocalDate.parse(viewProvider.read());

        selectAllTrainers();

        viewProvider.printMessage("Enter Trainer name: ");
        String trainerUsername = viewProvider.read();

        List<Training> trainings = traineeService.getTrainingsList(traineeUsername, dateFrom, dateTo, trainerUsername);

        viewProvider.printList(trainings);

    }

    private void getTrainersListNotAssignedOnTrainee() {
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();

        Trainee trainee = traineeService.find(traineeUsername);
        viewProvider.printMessage(trainee.toString());

        List<Trainer> trainers =
                trainerService.getTrainersListNotAssignedOnTrainee(traineeUsername);

        viewProvider.printList(trainers);
    }

    private void updateTraineeTrainersList() {
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();

        Trainee trainee = traineeService.find(traineeUsername);
        viewProvider.printMessage(trainee.toString());

        Set<Trainer> trainers = trainee.getTrainers();
        viewProvider.printList(trainers.stream().toList());

        boolean shouldAddTrainer = shouldAddTrainerToTraineeList();

        viewProvider.printMessage("Enter Trainer username: ");
        String trainerUsername = viewProvider.read();

        Trainer trainer = trainerService.find(trainerUsername);
        viewProvider.printMessage(trainee.toString());

        if (shouldAddTrainer) {
            traineeService.addTrainerToTraineeList(trainee, trainer);
        } else {
            traineeService.removeTrainerToTraineeList(trainee, trainer);
        }

        viewProvider.printList(traineeService.find(traineeUsername).getTrainers().stream().toList());
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
        TrainingType trainingType = trainingTypeService.find(trainingTypeName);

        Trainer trainer = Trainer.builder()
                .firstname(firstname)
                .lastname(lastname)
                .isActive(true)
                .specialization(trainingType)
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
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();

        Trainer beforeUpd = trainerService.find(username);
        viewProvider.printMessage(beforeUpd.toString());

        viewProvider.printMessage("Enter Trainer first name: ");
        String firstname = viewProvider.read();
        viewProvider.printMessage("Enter Trainer last name: ");
        String lastname = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        selectAllTrainingTypes();
        viewProvider.printMessage("Enter TrainingType name: ");
        String trainingTypeName = viewProvider.read();
        TrainingType trainingType = trainingTypeService.find(trainingTypeName);
        boolean isActive = changeToActive();
        long id = beforeUpd.getId();
        String password = beforeUpd.getPassword();

        Trainer trainer = Trainer.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .isActive(isActive)
                .specialization(trainingType)
                .password(password)
                .build();

        Trainer updatedTrainer = trainerService.save(trainer);
        viewProvider.printMessage(updatedTrainer.toString());
    }

    private void selectTrainerByUserName() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();
        Trainer trainer = trainerService.find(username);

        viewProvider.printMessage(trainer.toString());
    }

    private void changeTrainerPassword() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();

        Trainer trainer = trainerService.find(username);
        viewProvider.printMessage(trainer.toString());

        viewProvider.printMessage("Enter new password: ");
        String password = viewProvider.read();
        viewProvider.printMessage("Repeat new password: ");
        String repeatPassword = viewProvider.read();

        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("password and repeated password are not equal");
        }

        trainer.setPassword(password);
        Trainer updatedTrainer = trainerService.save(trainer);

        viewProvider.printMessage(updatedTrainer.toString());
    }

    private void activateDeactivateTrainer() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();

        Trainer trainer = trainerService.find(username);
        viewProvider.printMessage(trainer.toString());

        boolean isActive = changeToActive();

        trainer.setIsActive(isActive);
        Trainer updatedTrainer = trainerService.save(trainer);

        viewProvider.printMessage(updatedTrainer.toString());
    }

    private void getTrainerTrainingsByUserName() {
        viewProvider.printMessage("Enter Trainer username: ");
        String username = viewProvider.read();

        Trainer trainer = trainerService.find(username);
        viewProvider.printMessage(trainer.toString());

        viewProvider.printMessage("Enter START date from in format" + UserUtil.DATE_TEMPLATE);
        LocalDate dateFrom = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter END date in format" + UserUtil.DATE_TEMPLATE);
        LocalDate dateTo = LocalDate.parse(viewProvider.read());

        selectAllTrainees();

        viewProvider.printMessage("Enter Trainee name: ");
        String traineeName = viewProvider.read();

        List<Training> trainings = trainerService.getTrainingsList(username, dateFrom, dateTo, traineeName);

        viewProvider.printList(trainings);
    }

    private void createTraining() {
        viewProvider.printMessage("Enter Training name: ");
        String name = viewProvider.read();
        viewProvider.printMessage("Choose Training Type from the list: ");
        String trainingTypeName = viewProvider.read();
        TrainingType trainingType = trainingTypeService.find(trainingTypeName);
        viewProvider.printMessage("Enter Training date and time in the format " + UserUtil.DATE_TEMPLATE);
        LocalDate date = LocalDate.parse(viewProvider.read());
        viewProvider.printMessage("Enter Trainer username: ");
        String trainerUsername = viewProvider.read();
        Trainer trainer = trainerService.find(trainerUsername);
        viewProvider.printMessage("Enter Trainee username: ");
        String traineeUsername = viewProvider.read();
        Trainee trainee = traineeService.find(traineeUsername);
        viewProvider.printMessage("Enter Training duration in minutes: ");
        int duration = viewProvider.readInt();

        Training training = Training.builder()
                .name(name)
                .type(trainingType)
                .date(date)
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

    private void selectAllTrainees() {
        List<Trainee> trainees = traineeService.findAll();
        viewProvider.printList(trainees);
    }

    private void selectAllTrainers() {
        List<Trainer> trainers = trainerService.findAll();
        viewProvider.printList(trainers);
    }

    private void selectAllTrainings() {
        List<Training> trainings = trainingService.findAll();
        viewProvider.printList(trainings);
    }

    private void selectAllTrainingTypes() {
        List<TrainingType> trainingTypes = trainingTypeService.findAll();
        viewProvider.printList(trainingTypes);
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
                default -> viewProvider.printMessage(WRONG_CHOICE_MESSAGE);
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

    private boolean shouldAddTrainerToTraineeList() {
        viewProvider.printMessage(SHOULD_ADD_TRAINER_TO_TRAINEE_LIST_MENU);
        int choice = viewProvider.readInt();
        return choice == 1;
    }
}

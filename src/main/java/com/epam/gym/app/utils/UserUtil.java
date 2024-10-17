package com.epam.gym.app.utils;

import com.epam.gym.app.dto.TraineeDto;
import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@UtilityClass
public class UserUtil {

    public static final String DATE_TEMPLATE = "yyyy-MM-dd";
    public static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
    public static final Random RANDOM = new Random();
    public static final int PASSWORD_LENGTH = 10;
    public static final String SEPARATOR = ".";

    public static String generateRandomPassword() {
        StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = RANDOM.nextInt(SYMBOLS.length());
            result.append(SYMBOLS.charAt(index));
        }
        return result.toString();
    }

    public static String generateUsername(String firstname,
                                          String lastname,
                                          List<TrainerDto> trainers,
                                          List<TraineeDto> trainees) {

        UserNameGenerator userNameGenerator = new UserNameGenerator(firstname, lastname, trainers, trainees);
        long serNum = userNameGenerator.generateSerNum();
        StringBuilder username = new StringBuilder();
        username.append(firstname).append(SEPARATOR).append(lastname);
        if (serNum != 0) {
            username.append(serNum);
        }
        return username.toString();
    }

    @Getter
    @RequiredArgsConstructor
    private class UserNameGenerator {

        private final String firstname;
        private final String lastname;
        private final List<TrainerDto> trainers;
        private final List<TraineeDto> trainees;
        private List<UserDto> users;
        private List<String> usernames;

        public int generateSerNum() {
            initializeUserList();
            if (users.isEmpty()) {
                return 0;
            }

            initializeUsernameList();
            return usernames.isEmpty() ? 0 : getNextSerialNumber();
        }

        private void initializeUserList() {
            users = new ArrayList<>();
            users.addAll(convertToUserList(trainers));
            users.addAll(convertToUserList(trainees));
        }

        private List<UserDto> convertToUserList(List<? extends UserDto> entities) {
            return entities == null || entities.isEmpty() ? new ArrayList<>()
                    : entities.stream().map(UserDto.class::cast).toList();
        }

        private void initializeUsernameList() {
            usernames = users.stream()
                    .filter(u -> firstname.equals(u.getFirstname()) && lastname.equals(u.getLastname()))
                    .map(UserDto::getUsername)
                    .filter(username -> username != null && !username.isEmpty())
                    .toList();
        }

        private int getNextSerialNumber() {
            return (int) usernames.stream()
                    .map(un -> un.substring(getUsernameSuffixStartIndex()))
                    .filter(suffix -> !suffix.isBlank())
                    .mapToLong(Long::parseLong)
                    .max()
                    .orElse(0) + 1;
        }

        private int getUsernameSuffixStartIndex() {
            return (firstname + SEPARATOR + lastname).length();
        }
    }
}

package com.epam.gym.app.utils;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UtilClass {

    public static final String DATE_TEMPLATE = "yyyy-MM-dd";
    public static final String DATE_TIME_TEMPLATE = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_TEMPLATE);
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
                                          List<Trainer> trainers,
                                          List<Trainee> trainees) {

        long serNum = generateSerNum(firstname, lastname, trainers, trainees);
        StringBuilder username = new StringBuilder();
        username.append(firstname).append(SEPARATOR).append(lastname);
        if (serNum != 0) {
            username.append(serNum);
        }
        return username.toString();
    }

    private static int generateSerNum(String firstname,
                                      String lastname,
                                      List<Trainer> trainers,
                                      List<Trainee> trainees) {
        int serNum;
        List<User> users = generateUserList(trainers, trainees);
        if (users.isEmpty()) {
            return 0;
        }

        List<String> usernames = getUsernameList(users, firstname, lastname);

        if (usernames.isEmpty()) {
            serNum = 0;
        } else if (usernames.size() == 1) {
            serNum = 1;
        } else {
            serNum = (int) usernames.stream()
                    .map(un -> un.substring(getSubStrStartIndex(firstname, lastname)))
                    .filter(s -> !s.isEmpty() && !s.isBlank())
                    .mapToLong(Long::parseLong)
                    .max()
                    .getAsLong() + 1;
        }
        return serNum;
    }

    private static List<User> generateUserList(List<Trainer> trainers, List<Trainee> trainees) {
        List<User> users = new ArrayList<>();
        users.addAll(castToUserList(trainers));
        users.addAll(castToUserList(trainees));
        return users;
    }

    private static List<User> castToUserList(List<? extends User> entities) {
        List<User> users = new ArrayList<>();
        if (entities != null && !entities.isEmpty()) {
            users.addAll(entities.stream()
                    .map(t -> (User) t)
                    .toList());
        }
        return users;
    }

    private static int getSubStrStartIndex(String firstname, String lastname) {
        return (firstname + SEPARATOR + lastname).length();
    }

    private static List<String> getUsernameList(List<User> users, String firstname, String lastname) {
        return users.stream()
                .filter(u -> u.getFirstname().equals(firstname) && u.getLastname().equals(lastname))
                .filter(u -> u.getUsername() != null && !u.getUsername().isEmpty())
                .map(User::getUsername).toList();
    }
}

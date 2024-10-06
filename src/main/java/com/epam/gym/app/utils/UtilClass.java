package com.epam.gym.app.utils;

import com.epam.gym.app.entity.User;

import java.util.List;
import java.util.Random;

public class UtilClass {

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

    public static String generateUserName(String firstName, String lastName) {
        StringBuilder userName = new StringBuilder();
        userName.append(firstName).append(SEPARATOR).append(lastName);
        return userName.toString();
    }
}

package com.epam.gym.app.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
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

    public static String generateUserName(String firstName, String lastName) {
        StringBuilder userName = new StringBuilder();
        userName.append(firstName).append(SEPARATOR).append(lastName);
        return userName.toString();
    }
}

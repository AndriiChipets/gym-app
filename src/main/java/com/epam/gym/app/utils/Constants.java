package com.epam.gym.app.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String REST_URL = "/api";
    public static final String AUTH_REST_URL = REST_URL + "/login";
    public static final String TRAINEE_REST_URL = REST_URL + "/trainee";
    public static final String TRAINEE_TRAINERS_REST_URL = "/trainee-trainers";
    public static final String TRAINER_REST_URL = REST_URL + "/trainer";
    public static final String TRAINERS_REST_URL = "/trainers";
    public static final String TRAINING_REST_URL = REST_URL + "/training";
    public static final String TRAININGS_REST_URL = "/trainings";
    public static final String TRAINING_TYPE_REST_URL = REST_URL + "/training-type";
    public static final int BRUTE_FORCE_MAX_ATTEMPT = 3;
    public static final int BRUTE_FORCE_BLOCKING_TIME = 5;
}

package com.epam.gym.app.entity;

public enum TrainingType {
    FITNESS("fitness"),
    YOGA("yoga"),
    ZUMBA("Zumba"),
    STRETCHING("stretching"),
    RESISTANCE("resistance");

    private String name;

    TrainingType(final String name) {
        this.name = name;
    }
}

package com.epam.gym.app.entity;

public class TrainingType {
    private Long id;
    private TrainingNames name;

    private enum TrainingNames {
        FITNESS("fitness"),
        YOGA("yoga"),
        ZUMBA("Zumba"),
        STRETCHING("stretching"),
        RESISTANCE("resistance");

        private String name;

        TrainingNames(final String name) {
            this.name = name;
        }
    }
}

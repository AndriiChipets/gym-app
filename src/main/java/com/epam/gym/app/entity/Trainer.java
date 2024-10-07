package com.epam.gym.app.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trainer extends User {
    TrainingType trainingType;

    @Override
    public String toString() {
        return "Trainer{" +
                super.toString() +
                ", trainingType=" + trainingType +
                "} ";
    }
}

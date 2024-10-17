package com.epam.gym.app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerDto extends UserDto {

    @NotNull
    TrainingTypeDto specialization;

    @Override
    public String toString() {
        return "Trainer{" +
                super.toString() +
                ", trainingType=" + specialization +
                "} ";
    }
}

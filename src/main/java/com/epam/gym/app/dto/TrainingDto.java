package com.epam.gym.app.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingDto {

    private Long id;

    @NotBlank(message = "Training name can't be blank")
    private String name;

    @NotNull(message = "Training must have Training Type")
    private TrainingTypeDto type;

    @NotNull(message = "Training must have scheduled date")
    @Future(message = "Training date can't from the past only in future")
    private LocalDate date;

    @Positive(message = "Training duration time accepts only positive numbers")
    private Integer duration;

    @NotNull(message = "Trainee must be assigned to Training")
    private TraineeDto trainee;

    @NotNull(message = "Trainer must be assigned to Training")
    private TrainerDto trainer;

    @Override
    public String toString() {
        return "Training{" +
                "date=" + date +
                ", id=" + id +
                ", name='" + name +
                ", type=" + type +
                ", trainee username=" + trainee.getUsername() +
                ", trainer username=" + trainer.getUsername() +
                ", duration=" + duration +
                '}';
    }
}

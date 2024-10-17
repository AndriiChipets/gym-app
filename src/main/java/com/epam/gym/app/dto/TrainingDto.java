package com.epam.gym.app.dto;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.TrainingType;
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

    @NotBlank
    private String name;

    @NotNull
    private TrainingTypeDto type;

    @NotNull
    @Future
    private LocalDate date;

    @Positive
    private Integer duration;

    @NotNull
    private TraineeDto trainee;

    @NotNull
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

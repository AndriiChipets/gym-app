package com.epam.gym.app.dto.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainingDTO {

    @NotBlank(message = "Training name can't be blank")
    private String name;

    @NotBlank(message = "Training must have Training Type")
    private String typeName;

    @NotBlank(message = "Training must have scheduled date")
    private String date;

    @Positive(message = "Training duration time accepts only positive numbers")
    private Integer duration;

    @NotBlank(message = "Trainee username can't be blank")
    private String traineeUsername;

    @NotBlank(message = "Trainer username can't be blank")
    private String trainerUsername;
}

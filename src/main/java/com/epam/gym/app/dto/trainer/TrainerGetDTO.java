package com.epam.gym.app.dto.trainer;

import com.epam.gym.app.dto.trainee.TraineeListDTO;
import com.epam.gym.app.dto.training_type.TrainingTypeDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerGetDTO {

    @NotNull
    @NotBlank(message = "firstname can't be blank")
    private String firstname;

    @NotNull
    @NotBlank(message = "lastname can't be blank")
    private String lastname;

    @NotNull
    @NotBlank(message = "Trainer must have specialization")
    TrainingTypeDTO specialization;

    @NotNull(message = "Active status can't be null")
    private Boolean isActive;

    @NotNull
    Set<TraineeListDTO> trainees;
}

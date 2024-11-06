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
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerUpdDTO {

    @NotBlank(message = "username can't be blank")
    private String username;

    @NotBlank(message = "firstname can't be blank")
    private String firstname;

    @NotBlank(message = "lastname can't be blank")
    private String lastname;

    TrainingTypeDTO specialization;

    @NotNull(message = "Active status can't be null")
    private Boolean isActive;

    Set<TraineeListDTO> trainees;
}

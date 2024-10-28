package com.epam.gym.app.dto.trainer;

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

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerListDTO {

    @NotNull
    @NotBlank(message = "firstname can't be blank")
    private String username;

    @NotNull
    @NotBlank(message = "firstname can't be blank")
    private String firstname;

    @NotNull
    @NotBlank(message = "lastname can't be blank")
    private String lastname;

    @NotNull(message = "Trainer must have specialization")
    TrainingTypeDTO specialization;
}

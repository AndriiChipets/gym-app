package com.epam.gym.app.dto.trainee;

import com.epam.gym.app.dto.trainer.TrainerListDTO;
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
public class TraineeUpdDTO {

    @NotBlank(message = "username can't be blank")
    private String username;

    @NotBlank(message = "firstname can't be blank")
    private String firstname;

    @NotBlank(message = "lastname can't be blank")
    private String lastname;

    @NotNull(message = "Active status can't be null")
    private Boolean isActive;

    private String dateOfBirth;
    private String address;

    Set<TrainerListDTO> trainers;
}

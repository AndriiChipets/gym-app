package com.epam.gym.app.dto.trainee;

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
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TraineeRegDTO {

    @NotNull
    @NotBlank(message = "firstname can't be blank")
    private String firstname;

    @NotNull
    @NotBlank(message = "lastname can't be blank")
    private String lastname;
    private String dateOfBirth;
    private String address;
}

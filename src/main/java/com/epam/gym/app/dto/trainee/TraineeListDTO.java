package com.epam.gym.app.dto.trainee;

import jakarta.validation.constraints.NotBlank;
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
public class TraineeListDTO {

    @NotBlank(message = "firstname can't be blank")
    private String username;

    @NotBlank(message = "firstname can't be blank")
    private String firstname;

    @NotBlank(message = "lastname can't be blank")
    private String lastname;
}

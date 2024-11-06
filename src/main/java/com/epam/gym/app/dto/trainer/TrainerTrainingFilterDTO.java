package com.epam.gym.app.dto.trainer;

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
@EqualsAndHashCode
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerTrainingFilterDTO {

    @NotBlank(message = "username can't be blank")
    private String username;
    private String dateFrom;
    private String dateTo;
    private String traineeUsername;
}

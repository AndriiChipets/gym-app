package com.epam.gym.app.dto;

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
public class TrainingTypeDTO {

    private Long id;

    @NotBlank(message = "Training Type name can't be blank")
    private String name;

    @Override
    public String toString() {
        return "TrainingType{" +
                "id=" + id +
                ", name=" + name +
                "}";
    }
}

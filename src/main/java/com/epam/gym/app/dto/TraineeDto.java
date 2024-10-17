package com.epam.gym.app.dto;

import jakarta.validation.constraints.Past;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = true)
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TraineeDto extends UserDto {

    @Past
    private LocalDate dateOfBirth;
    private String address;

    @Override
    public String toString() {
        return "Trainee{" +
                super.toString() +
                ", address=" + address +
                ", dateOfBirth=" + dateOfBirth +
                "} ";
    }
}

package com.epam.gym.app.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = true)
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trainee extends User {
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

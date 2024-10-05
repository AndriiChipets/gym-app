package com.epam.gym.app.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
public class Trainee extends User {
    private LocalDate dateOfBirth;
    private String address;
}

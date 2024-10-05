package com.epam.gym.app.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Boolean isActive;
}

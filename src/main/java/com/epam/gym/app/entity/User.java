package com.epam.gym.app.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class User {
    private Long id;
    private String firstname;
    private String lastname;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    private Boolean isActive;
}

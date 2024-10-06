package com.epam.gym.app.entity;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Training {
    private Long id;
    private String name;
    private TrainingType type;
    private LocalDateTime date;
    private Trainee trainee;
    private Trainer trainer;
    private Integer duration;
}

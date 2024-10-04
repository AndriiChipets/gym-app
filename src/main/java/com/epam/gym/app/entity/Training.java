package com.epam.gym.app.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode
@ToString
@Setter
public class Training {
    private Long id;
    private String name;
    private TrainingType type;
    private LocalDateTime date;
    private Trainee trainee;
    private Trainer trainer;
    private Integer duration;
}

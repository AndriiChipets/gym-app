package com.epam.gym.app.entity;

import lombok.*;

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

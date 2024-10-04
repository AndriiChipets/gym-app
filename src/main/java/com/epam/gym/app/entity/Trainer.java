package com.epam.gym.app.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Builder
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Setter
public class Trainer extends User {
    TrainingType trainingType;
}

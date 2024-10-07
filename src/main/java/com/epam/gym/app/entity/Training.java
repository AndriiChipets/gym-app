package com.epam.gym.app.entity;

import com.epam.gym.app.utils.UtilClass;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
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

    @Override
    public String toString() {
        return "Training{" +
                "date=" + date.format(UtilClass.FORMATTER) +
                ", id=" + id +
                ", name='" + name +
                ", type=" + type.name() +
                ", trainee id=" + trainee.getId() +
                ", trainer id=" + trainer.getId() +
                ", duration=" + duration +
                '}';
    }
}

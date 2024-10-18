package com.epam.gym.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trainees")
@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = true)
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trainee extends User {

    @Column(name = "data_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "trainee_trainer",
            joinColumns = {@JoinColumn(name = "trainee_id")},
            inverseJoinColumns = {@JoinColumn(name = "trainer_id")}
    )
    private final Set<Trainer> trainers = new HashSet<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "trainee_id")
    private final Set<Training> trainings = new HashSet<>();

    public void removeTrainer(Trainer trainer) {
        this.trainers.remove(trainer);
        trainer.getTrainees().remove(this);
    }

    public void addTrainer(Trainer trainer) {
        this.trainers.add(trainer);
        trainer.getTrainees().add(this);
    }

    @Override
    public String toString() {
        return "Trainee{" +
                super.toString() +
                ", address=" + address +
                ", dateOfBirth=" + dateOfBirth +
                "} ";
    }
}

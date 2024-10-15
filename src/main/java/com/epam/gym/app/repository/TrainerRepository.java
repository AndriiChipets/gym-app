package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

    @Query("SELECT t FROM Trainer t JOIN t.trainees ts WHERE ts.username <> :username")
    List<Trainer> findAllNotAssignedOnTrainee(@Param("username") String traineeUsername);
}

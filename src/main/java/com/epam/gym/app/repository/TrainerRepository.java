package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByUsername(String username);

    @Query("select tr from Trainer tr where tr not in " +
            "(select tr2 from Trainer tr2 join tr2.trainees ts where ts.username = :username)")
    List<Trainer> findAllNotAssignedOnTrainee(@Param("username") String username);

    List<Trainer> findAllByUsernameIn(Set<String> trainersUsernames);
}

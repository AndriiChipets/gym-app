package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Query("select tng from Training tng " +
            " join tng.trainer ter" +
            " join tng.trainee tee" +
            " where ter.username = :terUsername" +
            " and (:teeUsername is null or tee.username = :teeUsername)" +
            " and (:dateFrom is null or tng.date >= :dateFrom)" +
            " and (:dateTo is null or tng.date <= :dateTo)")
    List<Training> getFilteredTrainings(
            @Param("terUsername") String trainerUsername,
            @Param("teeUsername") String traineeUsername,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo);
}

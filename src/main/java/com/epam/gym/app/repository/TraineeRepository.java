package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Trainee;
import com.epam.gym.app.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    Optional<Trainee> findByUsername(String username);

    void deleteByUsername(String username);

    @Query("select tng from Training tng " +
            " join tng.trainee tee" +
            " join tng.trainer ter" +
            " join tng.type tp" +
            " where tee.username = :teeUsername" +
            " and (:terUsername is null or ter.username = :terUsername)" +
            " and (:dateFrom is null or tng.date >= :dateFrom)" +
            " and (:dateTo is null or tng.date <= :dateTo)" +
            " and (:type is null or tp.name = :type)")
    List<Training> getFilteredTrainings(
            @Param("teeUsername") String traineeUsername,
            @Param("terUsername") String trainerUsername,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            @Param("type") String typeName);
}

package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    void deleteByUsername(String username);

    Optional<Trainee> findByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);

}

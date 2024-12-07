package com.epam.gym.app.repository;

import com.epam.gym.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String userName);
}

package com.epam.gym.app.repository;

import com.epam.gym.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndPassword(String username, String password);

    User findByUsername(String userName);
}

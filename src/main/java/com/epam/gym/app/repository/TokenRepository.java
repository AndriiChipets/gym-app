package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("Select t from Token t inner join User u on t.user.id = u.id " +
            "where t.user.id = :userId and t.isLoggedOut = false")
    List<Token> findAllTokenByUserId(Long userId);

    Optional<Token> findByName(String tokenName);
}

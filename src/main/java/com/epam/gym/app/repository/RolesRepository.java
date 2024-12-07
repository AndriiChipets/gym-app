package com.epam.gym.app.repository;

import com.epam.gym.app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {
}

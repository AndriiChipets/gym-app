package com.epam.gym.app.service;

import com.epam.gym.app.dto.user.UserChangePasswordDTO;
import com.epam.gym.app.entity.User;
import com.epam.gym.app.exception.UserNotLoginException;
import com.epam.gym.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository authRepository;

    @Transactional
    public boolean changePassword(UserChangePasswordDTO changePasswordDTO) {
        String username = changePasswordDTO.getUsername();
        String oldPassword = changePasswordDTO.getOldPassword();
        String newPassword = changePasswordDTO.getNewPassword();

        if (!authRepository.existsByUsernameAndPassword(username, oldPassword)) {
            log.warn("Old password or username is incorrect");
            throw new UserNotLoginException("Old password or username is incorrect");
        }

        User user = authRepository.findByUsername(username).get();
        user.setPassword(newPassword);
        authRepository.save(user);

        return user.getPassword().equals(newPassword);
    }
}
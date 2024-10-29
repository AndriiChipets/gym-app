package com.epam.gym.app.dto.user;

import com.epam.gym.app.utils.UserUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserChangePasswordDTO {

    @NotBlank(message = "username can't be blank")
    private String username;

    @Size(min = UserUtil.PASSWORD_LENGTH, max = UserUtil.PASSWORD_LENGTH, message
            = "password must be exactly " + UserUtil.PASSWORD_LENGTH + " characters length")
    private String oldPassword;

    @Size(min = UserUtil.PASSWORD_LENGTH, max = UserUtil.PASSWORD_LENGTH, message
            = "password must be exactly " + UserUtil.PASSWORD_LENGTH + " characters length")
    private String newPassword;
}

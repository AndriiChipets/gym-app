package com.epam.gym.app.dto;

import com.epam.gym.app.utils.UserUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {

    private Long id;

    @NotBlank(message = "firstname can't be blank")
    private String firstname;

    @NotBlank(message = "lastname can't be blank")
    private String lastname;

    private String username;

    @Size(min = UserUtil.PASSWORD_LENGTH, max = UserUtil.PASSWORD_LENGTH, message
            = "password must be exactly " + UserUtil.PASSWORD_LENGTH + " characters length")
    private String password;

    @NotNull(message = "Active status can't be null")
    private Boolean isActive;

    @Override
    public String toString() {
        return "id=" + id +
                ", firstName=" + firstname +
                ", lastName=" + lastname +
                ", userName=" + username +
                ", isActive=" + isActive;
    }
}

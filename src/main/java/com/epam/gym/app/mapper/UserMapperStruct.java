package com.epam.gym.app.mapper;

import com.epam.gym.app.dto.UserDto;
import com.epam.gym.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapperStruct {

    UserDto maUserToUserDto(User user);

    User mapUserDtoToUser(UserDto userDto);
}

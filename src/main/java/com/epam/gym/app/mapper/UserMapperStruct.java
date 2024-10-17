package com.epam.gym.app.mapper;

import com.epam.gym.app.dto.UserDto;
import com.epam.gym.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapperStruct {

    @Mapping(target = "password", ignore = true)
    UserDto maUserToUserDto(User user);

    User mapUserDtoToUser(UserDto userDto);
}

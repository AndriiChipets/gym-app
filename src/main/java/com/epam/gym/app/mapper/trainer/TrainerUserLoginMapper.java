package com.epam.gym.app.mapper.trainer;

import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainerUserLoginMapper {

    UserLoginDTO mapTrainerToUserLoginDTO(Trainer trainer);
}

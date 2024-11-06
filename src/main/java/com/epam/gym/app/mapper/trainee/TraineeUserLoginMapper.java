package com.epam.gym.app.mapper.trainee;

import com.epam.gym.app.dto.user.UserLoginDTO;
import com.epam.gym.app.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TraineeUserLoginMapper {

    UserLoginDTO mapTraineeToUserLoginDTO(Trainee trainee);
}

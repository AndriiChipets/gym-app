package com.epam.gym.app.mapper.trainee;

import com.epam.gym.app.dto.trainee.TraineeGetDTO;
import com.epam.gym.app.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TraineeGetMapper {

    TraineeGetDTO mapTraineeToTraineeGetDTO(Trainee trainee);
}

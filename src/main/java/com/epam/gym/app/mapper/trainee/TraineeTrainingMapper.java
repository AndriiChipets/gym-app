package com.epam.gym.app.mapper.trainee;

import com.epam.gym.app.dto.trainee.TraineeTrainingDTO;
import com.epam.gym.app.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TraineeTrainingMapper {

    @Mapping(target = "typeName", source = "training.type.name")
    @Mapping(target = "trainerUsername", source = "training.trainer.username")
    TraineeTrainingDTO mapTrainingToTrainingDTO(Training training);
}

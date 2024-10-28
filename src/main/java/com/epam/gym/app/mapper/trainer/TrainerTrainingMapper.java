package com.epam.gym.app.mapper.trainer;

import com.epam.gym.app.dto.trainer.TrainerTrainingDTO;
import com.epam.gym.app.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainerTrainingMapper {

    @Mapping(target = "typeName", source = "training.type.name")
    @Mapping(target = "traineeUsername", source = "training.trainee.username")
    TrainerTrainingDTO mapTrainingToTrainingDTO(Training training);
}

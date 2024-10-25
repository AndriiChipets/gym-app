package com.epam.gym.app.mapper;

import com.epam.gym.app.dto.TrainingDto;
import com.epam.gym.app.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingMapperStruct {

    TrainingDto mapTrainingToTrainingDto(Training training);

    Training mapTrainingDtoToTraining(TrainingDto trainingDto);
}

package com.epam.gym.app.mapper;

import com.epam.gym.app.dto.TraineeDto;
import com.epam.gym.app.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TraineeMapperStruct {

    TraineeDto mapTraineeToTraineeDto(Trainee trainee);

    @Mapping(target = "trainers", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "password", ignore = true)
    Trainee mapTraineeDtoToTrainee(TraineeDto traineeDto);
}

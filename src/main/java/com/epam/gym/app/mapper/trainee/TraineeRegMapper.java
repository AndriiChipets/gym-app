package com.epam.gym.app.mapper.trainee;

import com.epam.gym.app.dto.trainee.TraineeRegDTO;
import com.epam.gym.app.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TraineeRegMapper {

    @Mapping(target = "trainers", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "dateOfBirth", source = "traineeDto.dateOfBirth", dateFormat = "yyyy-MM-dd")
    Trainee mapTraineeDtoToTrainee(TraineeRegDTO traineeDto);
}

package com.epam.gym.app.mapper.trainee;

import com.epam.gym.app.dto.trainee.TraineeRegDTO;
import com.epam.gym.app.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TraineeRegMapper {

    @Mapping(target = "dateOfBirth", source = "traineeDto.dateOfBirth", dateFormat = "yyyy-MM-dd")
    Trainee mapTraineeDtoToTrainee(TraineeRegDTO traineeDto);
}

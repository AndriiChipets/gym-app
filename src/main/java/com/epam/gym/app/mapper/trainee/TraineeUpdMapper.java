package com.epam.gym.app.mapper.trainee;

import com.epam.gym.app.dto.trainee.TraineeUpdDTO;
import com.epam.gym.app.entity.Trainee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TraineeUpdMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "trainers", ignore = true)
    void updateTraineeFromDTO(TraineeUpdDTO traineeDto, @MappingTarget Trainee trainee);

    TraineeUpdDTO mapTraineeToTraineeUpdDTO(Trainee trainee);
}

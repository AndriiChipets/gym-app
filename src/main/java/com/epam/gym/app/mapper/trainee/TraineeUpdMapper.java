package com.epam.gym.app.mapper.trainee;

import com.epam.gym.app.dto.trainee.TraineeUpdDTO;
import com.epam.gym.app.entity.Trainee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TraineeUpdMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTraineeFromDTO(TraineeUpdDTO traineeDto, @MappingTarget Trainee trainee);

    TraineeUpdDTO mapTraineeToTraineeUpdDTO(Trainee trainee);
}

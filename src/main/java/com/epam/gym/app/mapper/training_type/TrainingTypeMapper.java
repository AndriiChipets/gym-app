package com.epam.gym.app.mapper.training_type;

import com.epam.gym.app.dto.training_type.TrainingTypeDTO;
import com.epam.gym.app.entity.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingTypeMapper {

    TrainingTypeDTO mapTypeToTypeDto(TrainingType trainingType);

    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "trainers", ignore = true)
    TrainingType mapTypeDtoToType(TrainingTypeDTO trainingTypeDto);
}

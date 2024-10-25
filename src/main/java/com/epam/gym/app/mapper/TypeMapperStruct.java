package com.epam.gym.app.mapper;

import com.epam.gym.app.dto.TrainingTypeDto;
import com.epam.gym.app.entity.TrainingType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TypeMapperStruct {

    TrainingTypeDto mapTypeToTypeDto(TrainingType trainingType);

    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "trainers", ignore = true)
    TrainingType mapTypeDtoToType(TrainingTypeDto trainingTypeDto);
}

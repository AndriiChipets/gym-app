package com.epam.gym.app.mapper.trainer;

import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.entity.Trainer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainerUpdMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "trainees", ignore = true)
    void updateTrainerFromDTO(TrainerUpdDTO trainerDto, @MappingTarget Trainer trainer);

    TrainerUpdDTO mapTrainerToTrainerUpdDTO(Trainer trainer);
}

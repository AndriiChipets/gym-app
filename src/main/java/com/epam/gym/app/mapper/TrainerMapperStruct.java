package com.epam.gym.app.mapper;

import com.epam.gym.app.dto.TrainerDto;
import com.epam.gym.app.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainerMapperStruct {

    TrainerDto mapTrainerToTrainerDto(Trainer trainer);

    @Mapping(target = "trainees", ignore = true)
    @Mapping(target = "trainings", ignore = true)
    @Mapping(target = "password", ignore = true)
    Trainer mapTrainerDtoToTrainer(TrainerDto trainerDto);
}

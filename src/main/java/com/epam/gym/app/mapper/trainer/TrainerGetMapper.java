package com.epam.gym.app.mapper.trainer;

import com.epam.gym.app.dto.trainer.TrainerGetDTO;
import com.epam.gym.app.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainerGetMapper {

    TrainerGetDTO mapTrainerToTrainerGetDTO(Trainer trainer);
}

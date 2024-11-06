package com.epam.gym.app.mapper.trainer;

import com.epam.gym.app.dto.trainer.TrainerListDTO;
import com.epam.gym.app.entity.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainerListMapper {

    TrainerListDTO mapTrainerToTrainerListDTO(Trainer trainer);
}

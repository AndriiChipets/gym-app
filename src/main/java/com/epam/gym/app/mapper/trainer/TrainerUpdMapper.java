package com.epam.gym.app.mapper.trainer;

import com.epam.gym.app.dto.trainer.TrainerUpdDTO;
import com.epam.gym.app.entity.Trainer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TrainerUpdMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTrainerFromDTO(TrainerUpdDTO trainerDto, @MappingTarget Trainer trainer);

    TrainerUpdDTO mapTrainerToTrainerUpdDTO(Trainer trainer);
}

package com.epam.gym.app.mapper.trainer;

import com.epam.gym.app.dto.trainer.TrainerRegDTO;
import com.epam.gym.app.entity.Trainer;
import com.epam.gym.app.repository.TrainingTypeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TrainerRegMapper {

    @Autowired
    protected TrainingTypeRepository trainingTypeRepository;

    @Mapping(target = "specialization",
            expression = "java(trainingTypeRepository.findByName(trainerDto.getSpecialization()).get())")
    public abstract Trainer mapTrainerDtoToTrainer(TrainerRegDTO trainerDto);
}

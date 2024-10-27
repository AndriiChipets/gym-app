package com.epam.gym.app.mapper;

import com.epam.gym.app.dto.TrainingDTO;
import com.epam.gym.app.entity.Training;
import com.epam.gym.app.repository.TraineeRepository;
import com.epam.gym.app.repository.TrainerRepository;
import com.epam.gym.app.repository.TrainingTypeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class TrainingMapperStruct {

    @Autowired
    protected TrainerRepository trainerRepository;
    @Autowired
    protected TraineeRepository traineeRepository;
    @Autowired
    protected TrainingTypeRepository trainingTypeRepository;

    @Mapping(target = "traineeUsername", source = "training.trainee.username")
    @Mapping(target = "trainerUsername", source = "training.trainer.username")
    @Mapping(target = "typeName", source = "training.type.name")
    public abstract TrainingDTO mapTrainingToTrainingDto(Training training);

    @Mapping(target = "date", source = "trainingDto.date", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "trainee",
            expression = "java(traineeRepository.findByUsername(trainingDto.getTraineeUsername()).get())")
    @Mapping(target = "trainer",
            expression = "java(trainerRepository.findByUsername(trainingDto.getTrainerUsername()).get())")
    @Mapping(target = "type",
            expression = "java(trainingTypeRepository.findByName(trainingDto.getTypeName()).get())")
    public abstract Training mapTrainingDtoToTraining(TrainingDTO trainingDto);
}

package com.epam.gym.app.service;

import com.epam.gym.app.dto.TrainingTypeDTO;
import com.epam.gym.app.entity.TrainingType;
import com.epam.gym.app.mapper.TypeMapperStruct;
import com.epam.gym.app.repository.TrainingTypeRepository;
import com.epam.gym.app.service.exception.NoEntityPresentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TrainingTypeService.class})
@DisplayName("TrainingTypeServiceTest")
class TrainingTypeServiceTest {

    @MockBean
    TrainingTypeRepository typeRepository;

    @MockBean
    TypeMapperStruct typeMapper;

    @Autowired
    TrainingTypeService typeService;

    @Test
    @DisplayName("find() method should return TrainingType when TrainingType is present")
    void find_shouldReturnTrainingType_whenTrainingTypePresent() {

        String name = "TrainingType name";
        TrainingType trainingType = TrainingType.builder().build();
        TrainingTypeDTO expected = TrainingTypeDTO.builder().name(name).build();

        when(typeRepository.findByName(anyString())).thenReturn(Optional.of(trainingType));
        when(typeMapper.mapTypeToTypeDto(any(TrainingType.class))).thenReturn(expected);
        TrainingTypeDTO actual = typeService.find(name);

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(typeRepository).findByName(name);
    }

    @Test
    @DisplayName("find() method should throw NoEntityPresentException when TrainingType isn't present")
    void find_shouldThrowNoEntityPresentException_whenTrainingTypeIsNotPresent() {

        String name = "invalid name";

        when(typeRepository.findByName(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NoEntityPresentException.class,
                () -> typeService.find(name));

        assertEquals("There is no TrainingType with name: " + name, exception.getMessage());
        verify(typeRepository, times(1)).findByName(name);
    }

    @Test
    @DisplayName("findAll() method should return List of TrainingTypes when TrainingTypes present")
    void findAll_shouldReturnTrainingTypesList_whenTrainingTypesPresent() {

        List<TrainingType> types = List.of(
                TrainingType.builder().build(),
                TrainingType.builder().build(),
                TrainingType.builder().build());

        List<TrainingTypeDTO> expected = List.of(
                TrainingTypeDTO.builder().build(),
                TrainingTypeDTO.builder().build(),
                TrainingTypeDTO.builder().build());

        when(typeRepository.findAll()).thenReturn(types);
        when(typeMapper.mapTypeToTypeDto(any(TrainingType.class))).thenReturn(new TrainingTypeDTO());
        List<TrainingTypeDTO> actual = typeService.findAll();

        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(typeRepository).findAll();
    }
}

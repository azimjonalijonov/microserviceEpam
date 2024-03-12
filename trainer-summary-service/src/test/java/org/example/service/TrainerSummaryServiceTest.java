package org.example.service;

import org.example.dto.RequestDTO;
import org.example.dto.ResponseDto;
import org.example.entity.TrainerSummaryEntity;
import org.example.repository.TrainerSummaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainerSummaryServiceTest {

    @Mock
    private TrainerSummaryRepository trainerSummaryRepository;

    @InjectMocks

    private TrainerSummaryService trainerSummaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listener() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "testuser");
        map.put("firstname", "John");
        map.put("lastname", "Doe");
        map.put("date", "2024-03-11T12:00:00.000");
        map.put("active", "true");
        map.put("duration", "60");
        trainerSummaryService.listener(map);
        Mockito.verify(trainerSummaryRepository, Mockito.times(1)).save(Mockito.any(TrainerSummaryEntity.class));
    }

    @Test
    void save() {
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUsername("testuser");
        requestDTO.setFirstname("John");
        requestDTO.setLastname("Doe");
        requestDTO.setDATE(LocalDateTime.of(2024, 3, 11, 12, 0));
        requestDTO.setActive(true);
        requestDTO.setDuration(60);

        trainerSummaryService.save(requestDTO);

        Mockito.verify(trainerSummaryRepository, Mockito.times(1)).save(Mockito.any(TrainerSummaryEntity.class));
    }

    @Test
    void get() {
        List<TrainerSummaryEntity> list = new ArrayList<>();
        TrainerSummaryEntity entity = new TrainerSummaryEntity();
        entity.setUsername("testuser");
        entity.setFirstname("John");
        entity.setLastname("Doe");
        entity.setActive(true);
        entity.setDATE(LocalDateTime.of(2024, 3, 11, 12, 0));
        entity.setDuration(60);
        list.add(entity);

        Mockito.when(trainerSummaryRepository.findAllByUsername("testuser")).thenReturn(list);

        ResponseDto responseDto = trainerSummaryService.get("testuser");

        assertEquals("testuser", responseDto.getUsername());
        assertEquals("John", responseDto.getFirstname());
        assertEquals("Doe", responseDto.getLastname());
        assertEquals(true, responseDto.getTrainerStatus());
        assertEquals(1, responseDto.getMap().size());
    }
}

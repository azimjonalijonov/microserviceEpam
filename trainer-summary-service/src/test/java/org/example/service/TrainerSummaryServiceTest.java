package org.example.service;

import org.example.config.jwt.JwtService;
import org.example.dto.ResponseDto;
import org.example.entity.TrainerSummaryEntity;
import org.example.repository.TrainerSummaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TrainerSummaryServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private TrainerSummaryRepository trainerSummaryRepository;

    @InjectMocks
    private TrainerSummaryService trainerSummaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listener_shouldSaveNewTrainerSummary() {
        Map<String, String> map = Map.of(
                "username", "testUser",
                "firstname", "John",
                "lastname", "Doe",
                "date", "2023-03-12T10:15:30.123",
                "active", "true",
                "duration", "60"
        );

        when(trainerSummaryRepository.findByUsernameAndYearAndMonth(anyString(), anyString(), anyString())).thenReturn(null);

        trainerSummaryService.listener(map);

        verify(trainerSummaryRepository, times(1)).save(any(TrainerSummaryEntity.class));
    }

    @Test
    void get_shouldReturnResponseDto() throws Exception {
        String username = "testUser";

        List<TrainerSummaryEntity> mockEntities = new ArrayList<>();
        TrainerSummaryEntity entity = new TrainerSummaryEntity();
        entity.setUsername(username);
        entity.setFirstname("John");
        entity.setLastname("Doe");
        entity.setYear("2023");
        entity.setMonth("MARCH");
        entity.setActive(true);
        entity.setDuration(60);
        mockEntities.add(entity);

        when(trainerSummaryRepository.findAllByUsername(username)).thenReturn(mockEntities);

        ResponseDto responseDto = trainerSummaryService.get(username);

        assertEquals(username, responseDto.getUsername());
        assertEquals("John", responseDto.getFirstname());
        assertEquals("Doe", responseDto.getLastname());
        assertEquals(1, responseDto.getPartialDTOS().size());
        assertEquals("2023", responseDto.getPartialDTOS().get(0).getYear());
        assertEquals("MARCH", responseDto.getPartialDTOS().get(0).getMonth());
        assertEquals(60, responseDto.getPartialDTOS().get(0).getSummaryDuration());
    }
}

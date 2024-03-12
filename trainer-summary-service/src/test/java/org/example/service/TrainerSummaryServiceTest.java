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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrainerSummaryServiceTest {

    @Mock
    private TrainerSummaryRepository trainerSummaryRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TrainerSummaryService trainerSummaryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListener_NewTrainerSummary() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "testUser");
        map.put("firstname", "John");
        map.put("lastname", "Doe");
        map.put("date", "2024-03-12T12:00:00.000");
        map.put("duration", "60");
        map.put("active", "true");

        when(trainerSummaryRepository.findByUsername(any())).thenReturn(null);

        trainerSummaryService.listener(map);

        verify(trainerSummaryRepository, times(1)).save(any(TrainerSummaryEntity.class));
    }

    @Test
    public void testListener_ExistingTrainerSummary() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "testUser");
        map.put("firstname", "John");
        map.put("lastname", "Doe");
        map.put("date", "2024-03-12T12:00:00.000");
        map.put("duration", "60");
        map.put("active", "true");

        TrainerSummaryEntity existingSummary = new TrainerSummaryEntity();
        Map<String, Map<String, Number>> summaryList = new HashMap<>();
        Map<String, Number> monthMap = new HashMap<>();
        monthMap.put("MARCH", 30);
        summaryList.put("2024", monthMap);
        existingSummary.setSummaryList(summaryList);
        when(trainerSummaryRepository.findByUsername(any())).thenReturn(existingSummary);

        trainerSummaryService.listener(map);

        verify(trainerSummaryRepository, times(1)).save(any(TrainerSummaryEntity.class));
    }

    @Test
    public void testGetExistingTrainerSummary() throws Exception {
        TrainerSummaryEntity trainerSummaryEntity = new TrainerSummaryEntity();
        trainerSummaryEntity.setUsername("testUser");
        trainerSummaryEntity.setFirstname("John");
        trainerSummaryEntity.setLastname("Doe");
        trainerSummaryEntity.setActive(true);
        Map<String, Map<String, Number>> summaryList = new HashMap<>();
        trainerSummaryEntity.setSummaryList(summaryList);

        when(trainerSummaryRepository.findByUsername(any())).thenReturn(trainerSummaryEntity);

        ResponseDto responseDto = trainerSummaryService.get("testUser");

        assertEquals("testUser", responseDto.getUsername());
        assertEquals("John", responseDto.getFirstname());
        assertEquals("Doe", responseDto.getLastname());
        assertEquals(true, responseDto.getTrainerStatus());
        assertEquals(summaryList, responseDto.getMap());

        verify(trainerSummaryRepository, times(1)).findByUsername("testUser");
    }

    @Test
    public void testGetNonExistingTrainerSummary() {
        when(trainerSummaryRepository.findByUsername(any())).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> trainerSummaryService.get("nonExistingUser"));

        assertEquals("no  trainerSummary found for this username", exception.getMessage());

        verify(trainerSummaryRepository, times(1)).findByUsername("nonExistingUser");
    }
}

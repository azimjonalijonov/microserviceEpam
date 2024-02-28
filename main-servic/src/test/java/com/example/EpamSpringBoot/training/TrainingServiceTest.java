package com.example.EpamSpringBoot.training;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.EpamSpringBoot.util.validation.impl.TrainingErrorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainingServiceTest {

	@Mock
	private TrainingRepository trainingRepository;

	@Mock
	private TrainingErrorValidator trainingErrorValidator;

	@InjectMocks
	private TrainingService trainingService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void readAll_shouldReturnListOfTrainings() {
		List<Training> expectedTrainings = new ArrayList<>();
		when(trainingRepository.findAll()).thenReturn(expectedTrainings);

		List<Training> trainings = trainingService.readAll();
		assertNotNull(trainings);
		assertEquals(expectedTrainings, trainings);
	}

	@Test
	void readById_shouldReturnTraining_whenTrainingExists() {
		long trainingId = 1L;
		Training expectedTraining = new Training();
		when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(expectedTraining));

		Training training = trainingService.readById(trainingId);

		assertNotNull(training);
		assertEquals(expectedTraining, training);
	}

	@Test
	void readById_shouldThrowException_whenTrainingDoesNotExist() {
		long nonExistingTrainingId = 999L;
		when(trainingRepository.findById(nonExistingTrainingId)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> trainingService.readById(nonExistingTrainingId));
	}

}

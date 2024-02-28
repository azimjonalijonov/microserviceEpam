package com.example.EpamSpringBoot.trainingType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.impl.TrainingTypeErrorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrainingTypeServiceTest {

	@Mock
	private TrainingTypeRepository trainingTypeRepository;

	@Mock
	private TrainingTypeErrorValidator trainingTypeErrorValidator;

	@InjectMocks
	private TrainingTypeService trainingTypeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void readAll_shouldReturnListOfTrainingTypes() {
		List<TrainingType> expectedTrainingTypes = new ArrayList<>();
		when(trainingTypeRepository.findAll()).thenReturn(expectedTrainingTypes);

		List<TrainingType> trainingTypes = trainingTypeService.readAll();

		assertNotNull(trainingTypes);
		assertEquals(expectedTrainingTypes, trainingTypes);
	}

	@Test
	void readById_shouldReturnTrainingType_whenTrainingTypeExists() {
		long trainingTypeId = 1L;
		TrainingType expectedTrainingType = new TrainingType();
		when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.of(expectedTrainingType));

		TrainingType trainingType = trainingTypeService.readById(trainingTypeId);

		assertNotNull(trainingType);
		assertEquals(expectedTrainingType, trainingType);
	}

	@Test
	void readById_shouldThrowException_whenTrainingTypeDoesNotExist() {
		long nonExistingTrainingTypeId = 999L;
		when(trainingTypeRepository.findById(nonExistingTrainingTypeId)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> trainingTypeService.readById(nonExistingTrainingTypeId));
	}

	@Test
	void add_shouldReturnTrainingType_whenParamsAreValid() {
		TrainingType trainingType = new TrainingType();
		when(trainingTypeErrorValidator.isValidParamsForCreate(trainingType)).thenReturn(true);
		when(trainingTypeRepository.save(trainingType)).thenReturn(trainingType);

		TrainingType addedTrainingType = trainingTypeService.add(trainingType);

		assertNotNull(addedTrainingType);
		assertEquals(trainingType, addedTrainingType);
	}

	@Test
	void add_shouldThrowException_whenParamsAreInvalid() {
		TrainingType trainingType = new TrainingType();
		when(trainingTypeErrorValidator.isValidParamsForCreate(trainingType)).thenReturn(false);
		assertThrows(ValidatorException.class, () -> trainingTypeService.add(trainingType));
	}

}

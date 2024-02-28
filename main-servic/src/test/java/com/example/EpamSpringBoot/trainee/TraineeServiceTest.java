package com.example.EpamSpringBoot.trainee;

import com.example.EpamSpringBoot.training.Training;
import com.example.EpamSpringBoot.training.TrainingRepository;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.impl.TraineeErrorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceTest {

	@Mock
	TraineeRepository traineeRepository;

	@Mock
	private UserService userService;

	@Mock
	TrainingRepository trainingRepository;

	@Mock
	private TraineeErrorValidator traineeErrorValidator;

	@InjectMocks
	private TraineeService traineeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void readAll_shouldReturnListOfTrainees() {
        when(traineeRepository.findAll()).thenReturn(Collections.singletonList(new Trainee()));

        List<Trainee> trainees = traineeService.readAll();

        assertEquals(1, trainees.size());
    }

	@Test
	void readById_existingId_shouldReturnTrainee() {
		Long traineeId = 1L;
		when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(new Trainee()));

		Trainee trainee = traineeService.readById(traineeId);

		assertNotNull(trainee);
	}

	@Test
	void readById_nonExistingId_shouldThrowEntityNotFoundException() {
		Long traineeId = 1L;
		when(traineeRepository.findById(traineeId)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> traineeService.readById(traineeId));
	}

	@Test
	void deleteById_shouldCallRepositoryDeleteById() {
		Long traineeId = 1L;

		traineeService.deleteById(traineeId);

		verify(traineeRepository, times(1)).deleteById(traineeId);
	}

	@Test
	void create_validTrainee_shouldReturnCreatedTrainee() {
		Trainee traineeToCreate = new Trainee();
		when(traineeErrorValidator.isValidParamsForCreate(traineeToCreate)).thenReturn(true);

		Trainee createdTrainee = traineeService.create(traineeToCreate);

		assertNotNull(createdTrainee);
		verify(traineeRepository, times(1)).save(traineeToCreate);
	}

	@Test
	void update_validTrainee_shouldReturnUpdatedTrainee() {
		Trainee traineeToUpdate = new Trainee();
		when(traineeErrorValidator.isValidParamsForUpdate(traineeToUpdate)).thenReturn(true);
		when(traineeRepository.findById(traineeToUpdate.getId())).thenReturn(Optional.of(new Trainee()));

		Trainee updatedTrainee = traineeService.update(traineeToUpdate);

		assertNotNull(updatedTrainee);
		verify(traineeRepository, times(1)).save(any(Trainee.class));
	}

	@Test
	void update_invalidTrainee_shouldThrowValidatorException() {
		Trainee traineeToUpdate = new Trainee();
		when(traineeErrorValidator.isValidParamsForUpdate(traineeToUpdate)).thenReturn(false);

		assertThrows(ValidatorException.class, () -> traineeService.update(traineeToUpdate));
		verify(traineeRepository, never()).save(any(Trainee.class));
	}

	@Test
	void getTraineeTrainingList_shouldReturnTrainings() {
		String username = "testUser";
		User user = new User();
		Trainee trainee = new Trainee();
		when(userService.readByUsername(username)).thenReturn(user);
		when(traineeRepository.findTraineeByUser(user)).thenReturn(trainee);
		List<Training> expectedTrainings = new ArrayList<>();
		when(trainingRepository.findAllByTrainee(trainee)).thenReturn(expectedTrainings);

		List<Training> trainings = traineeService.getTraineeTrainingList(username, 5);

		assertNotNull(trainings);
		assertEquals(expectedTrainings, trainings);
	}

}

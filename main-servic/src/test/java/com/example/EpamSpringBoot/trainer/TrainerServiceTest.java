package com.example.EpamSpringBoot.trainer;

import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainer;
import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainersRepository;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.impl.TrainerErrorValidator;
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

class TrainerServiceTest {

	@Mock
	TrainerRepository trainerRepository;

	@Mock
	TraineeTrainersRepository traineeTrainersRepository;

	@Mock
	TrainerErrorValidator trainerErrorValidator;

	@Mock
	UserService userService;

	@InjectMocks
	TrainerService trainerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
    void readAll_shouldReturnListOfTrainers() {
        when(trainerRepository.findAll()).thenReturn(Collections.singletonList(new Trainer()));

        List<Trainer> trainers = trainerService.readAll();

        assertEquals(1, trainers.size());
    }

	@Test
	void readById_existingId_shouldReturnTrainer() {
		Long trainerId = 1L;
		when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(new Trainer()));

		Trainer trainer = trainerService.readById(trainerId);

		assertNotNull(trainer);
	}

	@Test
	void readById_nonExistingId_shouldThrowEntityNotFoundException() {
		Long trainerId = 1L;
		when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> trainerService.readById(trainerId));
	}

	@Test
	void deleteById_shouldCallRepositoryDeleteById() {
		Long trainerId = 1L;

		trainerService.deleteById(trainerId);

		verify(trainerRepository, times(1)).deleteById(trainerId);
	}

	@Test
	void create_validTrainer_shouldReturnCreatedTrainer() {
		Trainer trainerToCreate = new Trainer();

		when(trainerErrorValidator.isValidParamsForCreate(trainerToCreate)).thenReturn(true);
		when(trainerRepository.save(any())).thenReturn(trainerToCreate);
		Trainer createdTrainer = trainerService.create(trainerToCreate);

		assertNotNull(createdTrainer);
		verify(trainerRepository, times(1)).save(trainerToCreate);
	}

	@Test
	void update_validTrainer_shouldReturnUpdatedTrainer() {
		Trainer trainerToUpdate = new Trainer();
		when(trainerErrorValidator.isValidParamsForUpdate(trainerToUpdate)).thenReturn(true);
		when(trainerRepository.findById(trainerToUpdate.getId())).thenReturn(Optional.of(new Trainer()));
		when(trainerRepository.save(any())).thenReturn(trainerToUpdate);
		Trainer updatedTrainer = trainerService.update(trainerToUpdate);

		assertNotNull(updatedTrainer);
		verify(trainerRepository, times(1)).save(any(Trainer.class));
	}

	@Test
	void update_invalidTrainer_shouldThrowValidatorException() {
		Trainer trainerToUpdate = new Trainer();
		when(trainerErrorValidator.isValidParamsForUpdate(trainerToUpdate)).thenReturn(false);

		assertThrows(ValidatorException.class, () -> trainerService.update(trainerToUpdate));
		verify(trainerRepository, never()).save(any(Trainer.class));
	}

	@Test
	void getTraineeTrainingList_shouldReturnTrainings() {
		String username = "testUser";
		User user = new User();
		Trainer trainer = new Trainer();
		when(userService.readByUsername(username)).thenReturn(user);
		when(trainerRepository.findTrainerByUser(user)).thenReturn(trainer);
		List<TraineeTrainer> expectedTrainings = new ArrayList<>();
		when(traineeTrainersRepository.findAllByTrainer(trainer)).thenReturn(expectedTrainings);
		List<TraineeTrainer> trainings = trainerService.getTraineeTrainingList(username, 5);
		assertNotNull(trainings);
		assertEquals(expectedTrainings, trainings);
	}

}

package com.example.EpamSpringBoot.training;

import com.example.EpamSpringBoot.training.Training;
import com.example.EpamSpringBoot.training.TrainingController;
import com.example.EpamSpringBoot.training.TrainingService;
import com.example.EpamSpringBoot.training.dto.PostTrainingDTO;
import com.example.EpamSpringBoot.trainee.Trainee;
import com.example.EpamSpringBoot.trainee.TraineeService;
import com.example.EpamSpringBoot.trainer.Trainer;
import com.example.EpamSpringBoot.trainer.TrainerService;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private TraineeService traineeService;

	@Mock
	private TrainingService trainingService;

	@Mock
	private TrainerService trainerService;

	@InjectMocks
	private TrainingController trainingController;

	@Test
	void post_shouldCreateTrainingAndReturnResponseEntity() {
		String username = "Azimjon";
		String password = "password";
		PostTrainingDTO postTrainingDTO = new PostTrainingDTO();
		postTrainingDTO.setTrainerUsername("trainerUsername");
		postTrainingDTO.setTraineeUsername("traineeUsername");
		postTrainingDTO.setName("Training Name");
		postTrainingDTO.setDuration(12);
		postTrainingDTO.setDate(LocalDate.now().atStartOfDay());

		User mockUser = new User();
		mockUser.setPassword(password);
		when(userService.readByUsername(username)).thenReturn(mockUser);

		Trainee mockTrainee = new Trainee();
		when(traineeService.readByUsername(postTrainingDTO.getTraineeUsername())).thenReturn(mockTrainee);

		Trainer mockTrainer = new Trainer();
		when(trainerService.readByUsername(postTrainingDTO.getTrainerUsername())).thenReturn(mockTrainer);

		ResponseEntity responseEntity = trainingController.post( postTrainingDTO);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		verify(trainingService, times(1)).create(any(Training.class));
	}

}

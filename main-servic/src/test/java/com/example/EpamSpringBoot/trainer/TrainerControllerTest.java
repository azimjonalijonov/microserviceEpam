package com.example.EpamSpringBoot.trainer;

import com.example.EpamSpringBoot.trainer.dto.PostTrainerDTO;
import com.example.EpamSpringBoot.trainer.dto.UpdateTrainerDTO;
import com.example.EpamSpringBoot.trainingType.TrainingType;
import com.example.EpamSpringBoot.trainingType.dto.TrainingTypeDTO;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import com.example.EpamSpringBoot.trainingType.TrainingTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private TrainerService trainerService;

	@Mock
	private TrainingTypeService trainingTypeService;

	@InjectMocks
	private TrainerController trainerController;

	@Test
	void post_shouldReturnResponseEntity() {
		PostTrainerDTO postTrainerDTO = new PostTrainerDTO();
		postTrainerDTO.setFirstname("John");
		postTrainerDTO.setLastname("Doe");
		postTrainerDTO.setTrainingTypeDTO(new TrainingTypeDTO());
		User mockUser = new User();
		when(userService.create(any(User.class))).thenReturn(mockUser);
		TrainingType mockTrainingType = new TrainingType();
		when(trainingTypeService.readById(any())).thenReturn(mockTrainingType);
		ResponseEntity responseEntity = trainerController.post(postTrainerDTO);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void testGet() {
		String username = "john";
		String password = "password";
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		when(userService.readByUsername(username)).thenReturn(user);
		when(trainerService.readByUsername(username)).thenReturn(new Trainer());

		ResponseEntity responseEntity = trainerController.get(username);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testUpdate() {
		String username = "john";
		String password = "password";
		UpdateTrainerDTO updateTrainerDTO = new UpdateTrainerDTO();
		updateTrainerDTO.setUsername(username);
		updateTrainerDTO.setFirstname("John");
		updateTrainerDTO.setLastname("Doe");
		updateTrainerDTO.setActive(true);

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		when(userService.readByUsername(username)).thenReturn(user);
		when(trainerService.getTraineeTrainingList(username, 1)).thenReturn(new ArrayList<>());

		ResponseEntity responseEntity = trainerController.update(username, password, updateTrainerDTO);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testGetSpecial() {
		String username = "john";
		String password = "password";
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		when(userService.readByUsername(username)).thenReturn(user);
		when(trainerService.getSpecificTrainers()).thenReturn(new ArrayList<>());

		ResponseEntity responseEntity = trainerController.getSpecial();

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testChangeStatus() {
		String username = "john";
		String password = "password";
		boolean bool = true;
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		Trainer trainer = new Trainer();
		trainer.setId(1L);

		when(userService.readByUsername(username)).thenReturn(user);
		when(trainerService.readByUsername(username)).thenReturn(trainer);

		ResponseEntity responseEntity = trainerController.changeStatus(username, bool);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}

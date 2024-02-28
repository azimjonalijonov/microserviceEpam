package com.example.EpamSpringBoot.trainee;

import com.example.EpamSpringBoot.trainee.TraineeService;
import com.example.EpamSpringBoot.trainee.dto.PostTraineeDTO;
import com.example.EpamSpringBoot.trainee.dto.UpdateTraineeDTO;
import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainer;
import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainerService;
import com.example.EpamSpringBoot.trainer.Trainer;
import com.example.EpamSpringBoot.trainer.TrainerService;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeControllerTest {

	@Mock
	private TrainerService trainerService;

	@Mock
	private UserService userService;

	@Mock
	private TraineeService traineeService;

	@Mock
	private TraineeTrainerService trainerTraineeService;

	@InjectMocks
	private TraineeController traineeController;

	public TraineeControllerTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void post_shouldReturnResponseEntity() {
		PostTraineeDTO traineeDTO = new PostTraineeDTO();
		traineeDTO.setFirstname("Azimjon");
		traineeDTO.setLastname("A");
		traineeDTO.setAddress("123 Street");
		traineeDTO.setDob(LocalDate.of(1990, 1, 1));
		Trainee trainee = new Trainee();
		User user = new User();
		trainee.setUser(user);
		when(userService.create(any())).thenReturn(user);
		when(traineeService.create(any())).thenReturn(trainee);

		ResponseEntity responseEntity = traineeController.post(traineeDTO);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}

	@Test
	void get_shouldReturnResponseEntity() {
		String username = "testUser";
		String password = "testPassword";
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		Trainee trainee = new Trainee();
		trainee.setUser(user);
		when(userService.readByUsername(username)).thenReturn(user);
		when(traineeService.readByUsername(username)).thenReturn(trainee);
		ResponseEntity responseEntity = traineeController.get(username, password);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void update_shouldReturnResponseEntity() {
		String username = "testUser";
		String password = "testPassword";
		UpdateTraineeDTO updateTraineeDTO = new UpdateTraineeDTO();
		updateTraineeDTO.setFirstname("John");
		updateTraineeDTO.setLastname("Doe");
		updateTraineeDTO.setUsername("testUser");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		Trainee trainee = new Trainee();
		trainee.setUser(user);
		when(userService.readByUsername(username)).thenReturn(user);
		when(userService.update(any())).thenReturn(user);
		when(traineeService.readByUsername(username)).thenReturn(trainee);
		when(traineeService.update(any())).thenReturn(trainee);
		when(traineeService.getTraineeTrainingList(any(), any())).thenReturn(new ArrayList<>());
		ResponseEntity responseEntity = traineeController.update(username, password, updateTraineeDTO);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void delete_shouldReturnResponseEntity() {
		String username = "testUser";
		String password = "testPassword";
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		Trainee trainee = new Trainee();
		trainee.setUser(user);
		when(userService.readByUsername(username)).thenReturn(user);
		doNothing().when(traineeService).deleteByUsername(username);
		ResponseEntity responseEntity = traineeController.delete(username, password);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void updateTrainerList_shouldReturnResponseEntity() {
		String username = "testUser";
		String password = "testPassword";
		List<String> trainersList = new ArrayList<>();
		trainersList.add("trainer1");
		trainersList.add("trainer2");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		Trainee trainee = new Trainee();
		trainee.setUser(user);
		Trainer trainer = new Trainer();
		when(userService.readByUsername(username)).thenReturn(user);
		when(trainerService.readByUsername(any())).thenReturn(trainer);
		when(trainerTraineeService.add(any())).thenReturn(new TraineeTrainer());
		ResponseEntity responseEntity = traineeController.updateTrainerList(username, password, trainersList);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void getTraineeTrainings_shouldReturnResponseEntity() {
		String username = "testUser";
		String password = "testPassword";
		LocalDate from = LocalDate.now().minusDays(7);
		LocalDate to = LocalDate.now();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		Trainee trainee = new Trainee();
		trainee.setUser(user);
		when(userService.readByUsername(username)).thenReturn(user);
		when(traineeService.readByUsername(username)).thenReturn(new Trainee());
		when(traineeService.getTraineeTrainingList(any(), any())).thenReturn(new ArrayList<>());
		ResponseEntity responseEntity = traineeController.getTraineeTrainings(username, password, from, to,
				"trainerName", 1L);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void changeStatus_shouldReturnResponseEntity() {
		String username = "testUser";
		String password = "testPassword";
		Boolean bool = true;
		Trainee trainee = new Trainee();
		trainee.setId(1L);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		trainee.setUser(user);
		when(userService.readByUsername(username)).thenReturn(user);
		when(traineeService.readByUsername(username)).thenReturn(trainee);
		when(traineeService.changeActivation(bool, trainee.getId())).thenReturn(trainee);

		ResponseEntity responseEntity = traineeController.changeStatus(username, password, bool);

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

}

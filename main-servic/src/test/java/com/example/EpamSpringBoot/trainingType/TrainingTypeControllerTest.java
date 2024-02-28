package com.example.EpamSpringBoot.trainingType;

import com.example.EpamSpringBoot.trainingType.dto.TrainingTypeDTO;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingTypeControllerTest {

	@Mock
	private TrainingTypeService trainingTypeService;

	@Mock
	private UserService userService;

	@InjectMocks
	private TrainingTypeController trainingTypeController;

	@Test
	void get_shouldReturnTrainingTypes() {
		List<TrainingType> trainingTypes = new ArrayList<>();
		TrainingType trainingType1 = new TrainingType();
		TrainingType trainingType2 = new TrainingType();
		trainingTypes.add(trainingType1);
		trainingTypes.add(trainingType2);

		when(trainingTypeService.readAll()).thenReturn(trainingTypes);

		ResponseEntity responseEntity = trainingTypeController.get();

		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(trainingTypes, responseEntity.getBody());
	}

	@Test
	void post_shouldCreateTrainingTypeAndReturnResponseEntity() {
		String username = "Azimjon";
		String password = "password";
		TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
		trainingTypeDTO.setName("Training Type Name");
		User mockUser = new User();
		mockUser.setPassword(password);
		when(userService.readByUsername(username)).thenReturn(mockUser);
		ResponseEntity responseEntity = trainingTypeController.post(username, password, trainingTypeDTO);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		verify(trainingTypeService, times(1)).add(any(TrainingType.class));
	}

}

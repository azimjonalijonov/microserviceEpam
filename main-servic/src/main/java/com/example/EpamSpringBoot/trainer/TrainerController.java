package com.example.EpamSpringBoot.trainer;

import com.example.EpamSpringBoot.trainee.Trainee;
import com.example.EpamSpringBoot.trainee.TraineeService;
import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainer;
import com.example.EpamSpringBoot.trainer.dto.PostTrainerDTO;
import com.example.EpamSpringBoot.trainer.dto.UpdateTrainerDTO;
import com.example.EpamSpringBoot.trainingType.TrainingType;
import com.example.EpamSpringBoot.trainingType.TrainingTypeService;
import com.example.EpamSpringBoot.user.Role;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

	final TraineeService traineeService;

	final UserService userService;

	final TrainerService trainerService;

	final TrainingTypeService trainingTypeService;

	public TrainerController(TraineeService traineeService, UserService userService, TrainerService trainerService,
			TrainingTypeService trainingTypeService) {
		this.traineeService = traineeService;
		this.userService = userService;
		this.trainerService = trainerService;
		this.trainingTypeService = trainingTypeService;
	}

	@PostMapping("/post")
	public ResponseEntity post(@RequestBody PostTrainerDTO postTrainerDTO) {
		if (postTrainerDTO.getFirstname().equals(null) || postTrainerDTO.getLastname().equals(null)
				|| postTrainerDTO.getTrainingTypeDTO() == null) {
			throw new RuntimeException("Firstname and lastnmae required");
		}
		User user = new User();
		user.setFirstName(postTrainerDTO.getFirstname());
		user.setLastName(postTrainerDTO.getLastname());
		user.setRole(Role.ROLE_TRAINER);
		User user1 = userService.create(user);
		Long val = postTrainerDTO.getTrainingTypeDTO().getId();
		TrainingType trainingType = trainingTypeService.readById(val);
		Trainer trainer = new Trainer();
		trainer.setSpecialization(trainingType);
		trainer.setUser(user1);
		trainerService.create(trainer);
		String username = trainer.getUser().getUsername();
		String password = UserService.currentP;

		Map<String, Object> jsonResponse = new HashMap<>();

		jsonResponse.put("username", username);
		jsonResponse.put("password", password);
		return ResponseEntity.ok(jsonResponse);

	}

	@GetMapping("/get")
	public ResponseEntity get(@RequestParam String username, String password) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		String response = trainerService.readByUsername(username).toString();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity update(@RequestParam String username, String password,
			@RequestBody UpdateTrainerDTO updateTrainerDTO) {
		// if (userService.readByUsername(username) == null) {
		// throw new RuntimeException("user does not exist");
		// }
		// User user = userService.readByUsername(username);
		// if (!user.getPassword().equals(password)) {
		// throw new RuntimeException("wrong password");
		// }
		User user1 = userService.readByUsername(updateTrainerDTO.getUsername());
		user1.setActive(updateTrainerDTO.getActive());
		user1.setLastName(updateTrainerDTO.getLastname());
		user1.setFirstName(updateTrainerDTO.getFirstname());
		userService.update(user1);
		List<Trainee> trainees = new ArrayList<>();
		for (TraineeTrainer training : trainerService.getTraineeTrainingList(updateTrainerDTO.getUsername(), 1)) {
			trainees.add(training.getTrainee());
		}
		String response = updateTrainerDTO.toString();
		response = response + "trainees list: " + trainees;
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getspecial")
	public ResponseEntity getSpecial(@RequestParam String username, String password) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		List<Trainer> trainerList = trainerService.getSpecificTrainers();
		return ResponseEntity.ok(trainerList);
	}

	@PatchMapping("/activateDeacivate")
	public ResponseEntity changeStatus(@RequestParam String username, String password, Boolean bool) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		Long id = trainerService.readByUsername(username).getId();
		trainerService.changeActivation(bool, id);
		return ResponseEntity.ok("");
	}

}

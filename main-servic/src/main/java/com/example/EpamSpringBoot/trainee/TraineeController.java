package com.example.EpamSpringBoot.trainee;

import com.example.EpamSpringBoot.trainee.dto.PostTraineeDTO;
import com.example.EpamSpringBoot.trainee.dto.UpdateTraineeDTO;
import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainer;
import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainerService;
import com.example.EpamSpringBoot.trainer.Trainer;
import com.example.EpamSpringBoot.trainer.TrainerService;
import com.example.EpamSpringBoot.training.Training;
import com.example.EpamSpringBoot.user.Role;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trainee")

public class TraineeController {

	final TrainerService trainerService;

	private final UserService userService;

	final TraineeService traineeService;

	final TraineeTrainerService trainerTraineeService;

	public TraineeController(TrainerService trainerService, UserService userService, TraineeService traineeService,
			TraineeTrainerService trainerTraineeService) {
		this.trainerService = trainerService;
		this.userService = userService;
		this.traineeService = traineeService;
		this.trainerTraineeService = trainerTraineeService;
	}

	@PostMapping("/post")
	public ResponseEntity<?> post(@RequestBody PostTraineeDTO traineeDTO) {

		if (traineeDTO.getFirstname().equals(null) || traineeDTO.getLastname().equals(null)) {
			throw new RuntimeException("Firstname and lastnmae required");
		}
		User user = new User();
		user.setFirstName(traineeDTO.getFirstname());
		user.setLastName(traineeDTO.getLastname());
		user.setRole(Role.ROLE_TRAINEE);
		User user1 = userService.create(user);
		Trainee trainee = new Trainee();
		trainee.setUser(user1);
		trainee.setAddress(traineeDTO.getAddress());
		trainee.setDateOfBirth(traineeDTO.getDob());
		Trainee trainee1 = traineeService.create(trainee);
		String username = trainee1.getUser().getUsername();
		String password = UserService.currentP;
		Map<String, Object> jsonResponse = new HashMap<>();

		jsonResponse.put("username", username);
		jsonResponse.put("password", password);
		return ResponseEntity.ok(jsonResponse);
	}

	@GetMapping("/get")
	public ResponseEntity get(@RequestParam String username) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);
		Trainee trainee= traineeService.readByUsername(username);
		return new ResponseEntity<>(trainee, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestParam String username,
			@RequestBody UpdateTraineeDTO updateTraineeDTO) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);

		if (updateTraineeDTO.getFirstname() == null || updateTraineeDTO.getLastname() == null
				|| updateTraineeDTO.getUsername() == null) {
			throw new RuntimeException("required fields are empty");
		}

		User user1 = userService.readByUsername(updateTraineeDTO.getUsername());
		user1.setActive(Boolean.valueOf(updateTraineeDTO.getActive()));
		user1.setLastName(updateTraineeDTO.getLastname());
		user1.setFirstName(updateTraineeDTO.getFirstname());
		userService.update(user1);
		Trainee trainee = traineeService.readByUsername(updateTraineeDTO.getUsername());
		if (updateTraineeDTO.getDob() != null) {
			trainee.setDateOfBirth(updateTraineeDTO.getDob());

		}
		if (updateTraineeDTO.getAddress() != null) {
			trainee.setAddress(updateTraineeDTO.getAddress());
		}
		traineeService.update(trainee);
		UpdateTraineeDTO response = updateTraineeDTO;
		List<Trainer> trainers = new ArrayList<>();
		for (Training training : traineeService.getTraineeTrainingList(updateTraineeDTO.getUsername(), 1)) {
			trainers.add(training.getTrainer());
		}
		return ResponseEntity.ok(updateTraineeDTO);
	}

	@DeleteMapping("/delete")
	public ResponseEntity delete(@RequestParam String username) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);
		traineeService.deleteByUsername(username);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/updateTrainerList")
	public ResponseEntity updateTrainerList(@RequestParam String username, String password,
			@RequestBody List<String> trainersList) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		List<TraineeTrainer> traineeTrainers = new ArrayList<>();
		Trainee trainee = traineeService.readByUsername(username);
		for (String username1 : trainersList) {
			Trainer trainer = trainerService.readByUsername(username1);
			TraineeTrainer trainerTrainee = new TraineeTrainer();
			trainerTrainee.setTrainee(trainee);
			trainerTrainee.setTrainer(trainer);
			trainerTraineeService.add(trainerTrainee);
			traineeTrainers.add(trainerTrainee);
		}
		return ResponseEntity.ok(traineeTrainers);

	}

	@GetMapping("/getTraineeTrainings")

	public ResponseEntity getTraineeTrainings(@RequestParam String username, String password, LocalDate from,
			LocalDate to, String trainerName, Long trainingTypeId) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		Trainee trainee = traineeService.readByUsername(username);
		List<Training> trainings = trainee.getTrainings();

		return ResponseEntity.ok(trainings);
	}

	@PatchMapping("/activateDeacivate")
	public ResponseEntity<?> changeStatus(@RequestParam String username, String password, Boolean bool) {
		if (userService.readByUsername(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		Long id = traineeService.readByUsername(username).getId();
		traineeService.changeActivation(bool, id);
		Map<String, String> map = new HashMap<>();
		map.put("message", "successful");
		return ResponseEntity.ok(map);
	}

}

package com.example.EpamSpringBoot.trainee;

import com.example.EpamSpringBoot.training.Training;
import com.example.EpamSpringBoot.training.TrainingRepository;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.impl.TraineeErrorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TraineeService {

	private final TraineeRepository traineeRepository;

	private final UserService userService;

	private final TrainingRepository trainingRepository;

	private final TraineeErrorValidator traineeErrorValidator;

	public TraineeService(TraineeRepository traineeRepository, UserService userService,
			TrainingRepository trainingRepository, TraineeErrorValidator traineeErrorValidator) {
		this.traineeRepository = traineeRepository;
		this.userService = userService;
		this.trainingRepository = trainingRepository;
		this.traineeErrorValidator = traineeErrorValidator;
	}

	public List<Trainee> readAll() {
		List<Trainee> traineeMap = traineeRepository.findAll();
		return traineeMap;
	}

	public Trainee readById(Long id) {
		return traineeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("trainer is not found with this id : " + id));
	}

	public Trainee create(Trainee createRequest) {
		// if (traineeErrorValidator.isValidParamsForCreate(createRequest)) {
		traineeRepository.save(createRequest);
		return createRequest;
		// }
		// throw new ValidatorException("Something wrong with provided entity");
	}

	public Trainee update(Trainee updateRequest) {
		if (traineeErrorValidator.isValidParamsForUpdate(updateRequest)) {
			Trainee trainee = traineeRepository.findById(updateRequest.getId())
				.orElseThrow(() -> new EntityNotFoundException(
						"trainer is not found with this id : " + updateRequest.getId()));
			traineeRepository.save(trainee);
			return updateRequest;
		}
		throw new ValidatorException("Something wrong with provided entity");
	}

	public void deleteById(Long id) {
		traineeRepository.deleteById(id);
	}

	public Trainee readByUsername(String username) {
		User user = userService.readByUsername(username);
		Trainee trainee = traineeRepository.findTraineeByUser(user);

		return trainee;

	}

	public Trainee passwordChangeTrainee(String password, Long id) {
		Trainee trainee = traineeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("trainer is not found with this id : " + id));
		User user = trainee.getUser();
		user.setPassword(password);
		userService.update(user);
		return trainee;
	}

	public Trainee changeActivation(Boolean bool, Long id) {
		Trainee trainee = traineeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("trainer is not found with this id : " + id));
		User user = trainee.getUser();
		user.setActive(bool);
		userService.update(user);
		return trainee;
	}

	@Transactional

	public void deleteByUsername(String username) {
		User user = userService.readByUsername(username);
		traineeRepository.deleteByUser(user);

	}

	public List<Training> getTraineeTrainingList(String username, Number duration) {
		User user = userService.readByUsername(username);
		Trainee trainee = traineeRepository.findTraineeByUser(user);
		List<Training> trainings = trainingRepository.findAllByTrainee(trainee);
		return trainings;

	}

}

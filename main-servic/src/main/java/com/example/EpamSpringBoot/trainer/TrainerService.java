package com.example.EpamSpringBoot.trainer;

import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainer;
import com.example.EpamSpringBoot.traineeTrainers.TraineeTrainersRepository;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.impl.TrainerErrorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

	private final TrainerErrorValidator trainerErrorValidator;

	private final TrainerRepository trainerRepository;

	private final UserService userService;

	private final TraineeTrainersRepository traineeTrainersRepository;

	public TrainerService(TrainerErrorValidator trainerErrorValidator, TrainerRepository trainerRepository,
			UserService userService, TraineeTrainersRepository traineeTrainersRepository) {
		this.trainerErrorValidator = trainerErrorValidator;
		this.trainerRepository = trainerRepository;
		this.userService = userService;
		this.traineeTrainersRepository = traineeTrainersRepository;
	}

	public List<Trainer> readAll() {
		return trainerRepository.findAll();
	}

	public Trainer readById(Long id) {
		return (trainerRepository.findById(id))
			.orElseThrow(() -> new EntityNotFoundException("trainer is not found with this id : " + id));
	}

	public Trainer create(Trainer createRequest) {
		if (trainerErrorValidator.isValidParamsForCreate(createRequest)) {
			return trainerRepository.save(createRequest);
		}
		throw new RuntimeException("Some thing wrong validator");
	}

	public Trainer update(Trainer updateRequest) {
		if (trainerErrorValidator.isValidParamsForUpdate(updateRequest)) {
			Trainer trainer = (trainerRepository.findById(updateRequest.getId())).orElseThrow(
					() -> new EntityNotFoundException("trainer is not found with this id : " + updateRequest.getId()));

			if (trainer == null) {

				throw new RuntimeException("null pointer exception");
			}

			return trainerRepository.save(updateRequest);
		}
		throw new ValidatorException("SOme thing wrong with provided entity");
	}

	public void deleteById(Long id) {
		trainerRepository.deleteById(id);
	}

	public Trainer readByUsername(String username) {
		User user = userService.readByUsername(username);
		Trainer trainer = trainerRepository.findTrainerByUser(user);
		return trainer;

	}

	public String passwordChangeTrainer(String password, Long id) {
		Trainer trainer = trainerRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("trainer is not found with this id : " + id));
		;
		User user = trainer.getUser();
		user.setPassword(password);
		userService.update(user);
		return password;

	}

	public String changeActivation(Boolean bool, Long id) {
		Trainer trainer = trainerRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("trainer is not found with this id : " + id));
		;
		User user = trainer.getUser();
		user.setActive(bool);
		userService.update(user);
		return "activated to " + bool;
	}

	public List<Trainer> getSpecificTrainers() {

		return trainerRepository.findActiveTrainersWithoutTrainees();
	}

	public List<TraineeTrainer> getTraineeTrainingList(String username, Number duration) {
		User user = userService.readByUsername(username);
		Trainer trainer = trainerRepository.findTrainerByUser(user);
		List<TraineeTrainer> trainings = (List<TraineeTrainer>) traineeTrainersRepository.findAllByTrainer(trainer);
		return trainings;

	}

}
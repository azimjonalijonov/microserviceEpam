package com.example.EpamSpringBoot.traineeTrainers;

import org.springframework.stereotype.Service;

@Service
public class TraineeTrainerService {

	private final TraineeTrainersRepository traineeTrainersRepository;

	public TraineeTrainerService(TraineeTrainersRepository traineeTrainersRepository) {
		this.traineeTrainersRepository = traineeTrainersRepository;
	}

	public TraineeTrainer add(TraineeTrainer traineeTrainer) {
		return traineeTrainersRepository.save(traineeTrainer);
	}

}

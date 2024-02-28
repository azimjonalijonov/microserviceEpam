package com.example.EpamSpringBoot.util.exception;

public class TraineeNotFoundException extends RuntimeException {

	public TraineeNotFoundException(Long id) {
		super("Trainee not found with ID: " + id);
	}

}

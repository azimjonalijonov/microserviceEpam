package com.example.EpamSpringBoot.util.exception;

public class TrainingNotFoundException extends RuntimeException {

	public TrainingNotFoundException(Long id) {
		super("Training not found with ID: " + id);
	}

}

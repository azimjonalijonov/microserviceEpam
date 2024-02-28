package com.example.EpamSpringBoot.util.exception;

public class TrainerNotFoundException extends RuntimeException {

	public TrainerNotFoundException(Long id) {
		super("Trainer not found with ID: " + id);
	}

}

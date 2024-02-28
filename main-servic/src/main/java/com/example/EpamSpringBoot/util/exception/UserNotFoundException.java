package com.example.EpamSpringBoot.util.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Long userID) {
		super("User not found with ID: " + userID);
	}

}

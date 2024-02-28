package com.example.EpamSpringBoot.util.validation.impl;

import com.example.EpamSpringBoot.training.Training;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.Validator;
import org.springframework.stereotype.Component;

@Component

public class TrainingErrorValidator implements Validator<Training> {

	@Override
	public boolean isValidParamsForCreate(Training entity) {
		if (entity.getDuration() == null) {
			throw new ValidatorException("Duration cannot be null");
		}
		else if (entity.getTrainingDate() == null) {
			throw new ValidatorException("Date of training cannot be null");
		}
		else if (entity.getTrainingName() == null) {
			throw new ValidatorException("Training name cannot be null");
		}
		else if (entity.getTrainee() == null) {
			throw new ValidatorException("Training id cannot be null");
		}
		else if (entity.getTrainer() == null) {
			throw new ValidatorException("Trainee id cannot be null");
		}
		return true;
	}

	@Override
	public boolean isValidParamsForUpdate(Training entity) {
		if (entity.getId() == null) {
			throw new ValidatorException("ID can not be null");
		}
		else if (entity.getDuration() == null) {
			throw new ValidatorException("Duration cannot be null");
		}
		else if (entity.getTrainingDate() == null) {
			throw new ValidatorException("Date of training cannot be null");
		}
		else if (entity.getTrainingName() == null) {
			throw new ValidatorException("Training name cannot be null");
		}
		else if (entity.getTrainee() == null) {
			throw new ValidatorException("Training id cannot be null");
		}
		else if (entity.getTrainer() == null) {
			throw new ValidatorException("Trainee id cannot be null");
		}
		return true;
	}

}

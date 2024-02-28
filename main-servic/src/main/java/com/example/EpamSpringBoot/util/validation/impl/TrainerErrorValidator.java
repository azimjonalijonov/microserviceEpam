package com.example.EpamSpringBoot.util.validation.impl;

import com.example.EpamSpringBoot.trainer.Trainer;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import org.springframework.stereotype.Component;

@Component
public class TrainerErrorValidator {

	public boolean isValidParamsForCreate(Trainer entity) {
		if (entity.getUser() == null) {
			throw new ValidatorException("Some thing wrong with user_id");
		}
		else if (entity.getSpecialization() == null) {
			throw new ValidatorException("Some thing wrong with specialization");
		}
		return true;
	}

	public boolean isValidParamsForUpdate(Trainer entity) {
		if (entity.getId() == null) {
			throw new ValidatorException("Some thing wrong with id");
		}
		else if (entity.getUser() == null) {
			throw new ValidatorException("Some thing wrong with user_id");
		}
		else if (entity.getSpecialization() == null) {
			throw new ValidatorException("Some thing wrong with specialization");
		}
		return true;
	}

}

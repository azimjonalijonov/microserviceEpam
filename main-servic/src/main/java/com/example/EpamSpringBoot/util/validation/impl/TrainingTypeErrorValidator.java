package com.example.EpamSpringBoot.util.validation.impl;

import com.example.EpamSpringBoot.trainingType.TrainingType;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeErrorValidator implements Validator<TrainingType> {

	@Override
	public boolean isValidParamsForCreate(TrainingType entity) {

		if (entity.getName() == null) {
			throw new ValidatorException("The name of the type cannot be null");
		}
		return true;
	}

	@Override
	public boolean isValidParamsForUpdate(TrainingType entity) {

		if (entity.getId() == null) {
			throw new ValidatorException("ID cannot be null");
		}
		else if (entity.getName() == null) {
			throw new ValidatorException("The name of the type cannot be null");
		}
		return true;
	}

}

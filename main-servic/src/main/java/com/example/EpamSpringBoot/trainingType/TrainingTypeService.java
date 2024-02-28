package com.example.EpamSpringBoot.trainingType;

import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.impl.TrainingTypeErrorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TrainingTypeService {

	private final TrainingTypeRepository trainingTypeRepository;

	private final TrainingTypeErrorValidator trainingTypeErrorValidator;

	public TrainingTypeService(TrainingTypeRepository trainingTypeRepository,
			TrainingTypeErrorValidator trainingTypeErrorValidator) {
		this.trainingTypeRepository = trainingTypeRepository;
		this.trainingTypeErrorValidator = trainingTypeErrorValidator;
	}

	public List<TrainingType> readAll() {
		return trainingTypeRepository.findAll();
	}

	public TrainingType readById(Long id) {
		return trainingTypeRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("trainingType is not found with this id : " + id));
	}

	public TrainingType add(TrainingType trainingType) {
		if (trainingTypeErrorValidator.isValidParamsForCreate(trainingType)) {
			return trainingTypeRepository.save(trainingType);
		}
		throw new ValidatorException("Something wrong with validator");
	}

}

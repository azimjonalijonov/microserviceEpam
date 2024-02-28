package com.example.EpamSpringBoot.training;

import com.example.EpamSpringBoot.config.jwt.JwtService;
import com.example.EpamSpringBoot.training.dto.RequestDTO;
import com.example.EpamSpringBoot.util.validation.impl.TrainingErrorValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TrainingService {

	private final TrainingRepository trainingRepository;

	private final JwtService jwtService;

	public static final String USER_SERVICE = "trainer-service";

	private final RestTemplate restTemplate;

	private final TrainingErrorValidator trainingErrorValidator;

	public TrainingService(TrainingRepository trainingRepository, JwtService jwtService, RestTemplate restTemplate,
			TrainingErrorValidator trainingErrorValidator) {
		this.trainingRepository = trainingRepository;
		this.jwtService = jwtService;
		this.restTemplate = restTemplate;
		this.trainingErrorValidator = trainingErrorValidator;
	}

	public List<Training> readAll() {
		return trainingRepository.findAll();
	}

	public Training readById(Long id) {
		return trainingRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("training is not found with this id : " + id));
	}

	public Training create(Training createRequest) {
		// if (trainingErrorValidator.isValidParamsForCreate(createRequest)) {

		Training training = trainingRepository.save(createRequest);
		System.out.println(training.getTrainer().toString());
		sendToTrainerService(training, true, "post");
		return training;

	}

	public void sendToTrainerService(Training training, Boolean bool, String method) {
		String jwt = jwtService.generateToken2(null);
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setUsername(training.getTrainer().getUser().getUsername());
		requestDTO.setFirstname(training.getTrainer().getUser().getFirstName());
		requestDTO.setLastname(training.getTrainer().getUser().getLastName());
		requestDTO.setDATE(training.getTrainingDate());
		requestDTO.setActive(bool);
		requestDTO.setDuration(training.getDuration());
		requestDTO.setMethod(method);
		String apiUrl = "http://localhost:9090/api/post";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + jwt);

		HttpEntity<RequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			System.out.println("POST request successful!");
		}
		else {
			System.err.println("POST request failed with status code: " + responseEntity.getStatusCodeValue());
		}

	}

	public Training update(Training updateRequest) {
		if (trainingErrorValidator.isValidParamsForUpdate(updateRequest)) {

			return trainingRepository.save(updateRequest);
		}
		throw new RuntimeException("Some thing is wrong with provided entity");
	}

	public void deleteById(Long id) {
		Training training = trainingRepository.getById(id);
		trainingRepository.deleteById(id);
		sendToTrainerService(training, true, "delete");

	}

	// public Training addTraining(Training training) {
	// return trainingDAO.createOrUpdate(training);
	//
	// }

}

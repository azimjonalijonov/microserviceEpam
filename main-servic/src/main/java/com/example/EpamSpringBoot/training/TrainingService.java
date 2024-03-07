package com.example.EpamSpringBoot.training;

import com.example.EpamSpringBoot.config.jwt.JwtService;
import com.example.EpamSpringBoot.training.dto.RequestDTO;
import com.example.EpamSpringBoot.util.validation.impl.TrainingErrorValidator;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.jms.Destination;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.jms.Queue;

@Service
@Slf4j
public class TrainingService {
    private final JmsTemplate jmsTemplate;
    private final Queue queue;


    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private final TrainingRepository trainingRepository;

    private final JwtService jwtService;

    private final RestTemplate restTemplate;
    static String apiUrl = "http://localhost:9090/api/post";

    private final TrainingErrorValidator trainingErrorValidator;

    public TrainingService(JmsTemplate jmsTemplate, Queue queue, CircuitBreakerRegistry circuitBreakerRegistry, TrainingRepository trainingRepository,
                           JwtService jwtService, RestTemplate restTemplate, TrainingErrorValidator trainingErrorValidator) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
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
        Training training = trainingRepository.save(createRequest);
        return training;
    }
    @CircuitBreaker(name = "serviceA", fallbackMethod = "fallback")

    public ResponseEntity createTrainerSummary2(Training training, Boolean bool, String method){
        Map<String,String> map =new HashMap<>();
        map.put("username",training.getTrainer().getUser().getUsername());
        map.put("firstname",training.getTrainer().getUser().getFirstName());
        map.put("lastname",training.getTrainer().getUser().getLastName());
        map.put("date",training.getTrainingDate().toString());
        map.put("active",bool.toString());
        map.put("duration",training.getDuration().toString());
        map.put("method",method);
        jmsTemplate.convertAndSend(queue, map);
        return ResponseEntity.ok("success");

    }

//    @CircuitBreaker(name = "serviceA", fallbackMethod = "fallback")
    public ResponseEntity createTrainerSummary(Training training, Boolean bool, String method) {
        String jwt = jwtService.generateToken2(null);
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUsername(training.getTrainer().getUser().getUsername());
        requestDTO.setFirstname(training.getTrainer().getUser().getFirstName());
        requestDTO.setLastname(training.getTrainer().getUser().getLastName());
        requestDTO.setDATE(training.getTrainingDate());
        requestDTO.setActive(bool);
        requestDTO.setDuration(training.getDuration());
        requestDTO.setMethod(method);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + jwt);

        HttpEntity<RequestDTO> requestEntity = new HttpEntity<>(requestDTO, headers);
        jmsTemplate.convertAndSend(queue, requestDTO);

//		return restTemplate.postForEntity(apiUrl, requestEntity, String.class);
        return ResponseEntity.ok("SUCCESS");
    }

    // public ResponseEntity sendRequest(String apiUrl, HttpEntity<RequestDTO>
    // requestEntity) {
    // return restTemplate.postForEntity(apiUrl, requestEntity, String.class);
    // }

    public Training update(Training updateRequest) {
        if (trainingErrorValidator.isValidParamsForUpdate(updateRequest)) {

            return trainingRepository.save(updateRequest);
        }
        throw new RuntimeException("Some thing is wrong with provided entity");
    }

    public void deleteById(Long id) {
        trainingRepository.deleteById(id);

    }

    public ResponseEntity fallback(Throwable throwable) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "secondservice issue");
        return new ResponseEntity<>(map, HttpStatus.SERVICE_UNAVAILABLE);
    }

    ;

}

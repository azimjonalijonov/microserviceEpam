package com.example.EpamSpringBoot.cucumberTest;

import com.example.EpamSpringBoot.training.dto.PostTrainingDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

public class TrainingTest {

    private String traineeUsername;
    private String trainerUsername;
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhemltam9uLmFsaWpvbm92MSIsImlhdCI6MTcxMDg0MzA3MSwiZXhwIjoxNzIwODQzMDcxfQ.lrjBoPVPTqtyR1RDQ-c2jV6ZBbEZTePVsk6WormF0ok";
    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<?> response;
    private PostTrainingDTO postTrainingDTO;

    @Given("a trainee with username {string} exists")
    public void a_trainee_with_username_exists(String traineeUsername) {
        postTrainingDTO = new PostTrainingDTO();
        this.traineeUsername = traineeUsername;
        postTrainingDTO.setTraineeUsername(traineeUsername);
    }

    @Given("a trainer with username {string} exists")
    public void a_trainer_with_username_exists(String trainerUsername) {
        this.trainerUsername = trainerUsername;
        postTrainingDTO.setTrainerUsername(trainerUsername);

    }

    @When("the trainer {string} creates a training session for trainee {string}")
    public void the_trainer_creates_a_training_session_for_trainee(String trainerUsername, String traineeUsername) {
        postTrainingDTO.setName("any");
        postTrainingDTO.setDuration(2);
        postTrainingDTO.setDate(LocalDateTime.now());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "Application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<PostTrainingDTO> request = new HttpEntity<>(postTrainingDTO,headers);
        response = restTemplate.exchange("http://localhost:8087/api/training/post", HttpMethod.POST, request, String.class);



    }

    @When("the trainer requests to delete the training session with id {long}")
    public void the_trainer_requests_to_delete_the_training_session(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "Application/json");
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Long> httpEntity =new HttpEntity<>(id,headers);
        response = restTemplate.exchange("http://localhost:8087/api/training/delete", HttpMethod.DELETE, httpEntity, String.class);


    }

    @Then("the training session should be successfully created")
    public void the_training_session_should_be_successfully_created() {
        Assert.assertEquals(200,response.getStatusCodeValue());
    }

    @Then("the training session with ID {long} should be deleted")
    public void the_training_session_with_ID_should_be_deleted(long trainingSessionId) {
      Assert.assertEquals(200,response.getStatusCodeValue());
    }
}

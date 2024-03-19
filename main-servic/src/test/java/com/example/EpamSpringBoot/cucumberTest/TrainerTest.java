package com.example.EpamSpringBoot.cucumberTest;


import com.example.EpamSpringBoot.trainee.Trainee;
import com.example.EpamSpringBoot.trainee.dto.PostTraineeDTO;
import com.example.EpamSpringBoot.trainer.dto.PostTrainerDTO;
import com.example.EpamSpringBoot.trainingType.dto.TrainingTypeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class TrainerTest {
    private String token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhemltam9uLmFsaWpvbm92MSIsImlhdCI6MTcxMDg0MzA3MSwiZXhwIjoxNzIwODQzMDcxfQ.lrjBoPVPTqtyR1RDQ-c2jV6ZBbEZTePVsk6WormF0ok";
    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<?> response;
    private PostTrainerDTO postTrainerDTO;
    private TrainingTypeDTO trainingTypeDTO;
    private String username;
    private List list;

    @Given("I have the following trainer information: {string} {string} {string}")
    public void i_have_the_following_trainer_information(String firstName, String lastName, String trainingTypeID) {
        postTrainerDTO = new PostTrainerDTO();
        trainingTypeDTO=new TrainingTypeDTO();
        trainingTypeDTO.setId(Long.valueOf(trainingTypeID));
        postTrainerDTO.setFirstname(firstName);
        postTrainerDTO.setLastname(lastName);
        postTrainerDTO.setTrainingTypeDTO(trainingTypeDTO);
    }

    @When("I post the trainer information")
    public void i_post_the_trainer_information() {
        HttpHeaders headers =new HttpHeaders();
        headers.set("Content-type","Application/json");
        headers.set("Authorization","Bearer "+token);
        HttpEntity<PostTrainerDTO> request=new HttpEntity<>(postTrainerDTO,headers);
        response =restTemplate.exchange("http://localhost:8087/api/trainer/post", HttpMethod.POST,request,String.class);
    }

    @Then("the trainer is successfully created")
    public void the_trainer_is_successfully_created() {
        Assert.assertEquals(200,response.getStatusCodeValue());
    }

    @Given("I have the trainer username {string}")
    public void i_have_the_trainer_username(String username) {
          this.username=username;
     }

    @When("I get trainer information")
    public void i_get_trainer_information() {
        HttpHeaders headers =new HttpHeaders();
        headers.set("Content-type","Application/json");
        headers.set("Authorization","Bearer "+token);
        HttpEntity<String> request=new HttpEntity<>(headers);

        response =restTemplate.exchange("http://localhost:8087/api/trainer/get?username="+username, HttpMethod.GET,request,String.class);
    }

    @Then("I receive the trainer details")
    public void i_receive_the_trainer_details() {
        Assert.assertEquals(200,response.getStatusCodeValue());
    }

    @When("I get trainers with specializations")
    public void i_get_trainers_with_specializations() throws JsonProcessingException {
        HttpHeaders headers =new HttpHeaders();
        headers.set("Content-type","Application/json");
        headers.set("Authorization","Bearer "+token);
        HttpEntity<String> request=new HttpEntity<>(headers);
        response =restTemplate.exchange("http://localhost:8087/api/trainer/getspecial", HttpMethod.GET,request,String.class);
        ObjectMapper objectMapper =new ObjectMapper();
        list =objectMapper.readValue(Objects.requireNonNull(response.getBody().toString()),List.class);

    }
    @Then("I receive a list of trainers with specializations")
     public void i_receive_list_of_trainers_with_specialization() {
        Assert.assertNotEquals(0,list.size());
        Assert.assertEquals(200,response.getStatusCodeValue());


    }
    @When("I activate or deactivate the trainer")
    public  void i_activate_deactivate_trainer(){
        HttpHeaders headers =new HttpHeaders();
        headers.set("Content-type","Application/json");
        headers.set("Authorization","Bearer "+token);
        HttpEntity<String> request=new HttpEntity<>(headers);
        response =restTemplate.exchange("http://localhost:8087/api/trainer/activateDeacivate?username="+username+"&bool=false", HttpMethod.PUT,request,String.class);


    }
    @Then("the trainer status is successfully changed")
    public void trainer_status_changed(){
        Assert.assertEquals(200,response.getStatusCodeValue());
      }


}

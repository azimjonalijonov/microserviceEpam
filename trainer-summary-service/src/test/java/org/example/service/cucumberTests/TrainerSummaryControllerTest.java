package org.example.service.cucumberTests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TrainerSummaryControllerTest {
    private String username;
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhemltam9uLmFsaWpvbm92MSIsImlhdCI6MTcxMDg0MzA3MSwiZXhwIjoxNzIwODQzMDcxfQ.lrjBoPVPTqtyR1RDQ-c2jV6ZBbEZTePVsk6WormF0ok";
    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<?> response;

    @Given("a trainer with username {string} exists")
    public void a_trainer_with_username_exists(String trainerUsername) {
        this.username = trainerUsername;
    }

    @When("a request is made to retrieve the trainer summary")
    public void a_request_is_made_to_retrieve_the_trainer_summary_for_username() {
        HttpHeaders headers =new HttpHeaders();
        headers.set("Content-type","Application/json");
        headers.set("Authorization","Bearer "+token);
        HttpEntity<String> httpEntity =new HttpEntity<>(headers);
        response =restTemplate.exchange("http://localhost:9090/api/get?username="+username, HttpMethod.GET,httpEntity,String.class);
    }

    @Then("the trainer summary should be successfully retrieved")
    public void the_trainer_summary_should_be_successfully_retrieved() {
        Assert.assertEquals(200,response.getStatusCodeValue());
    }
}

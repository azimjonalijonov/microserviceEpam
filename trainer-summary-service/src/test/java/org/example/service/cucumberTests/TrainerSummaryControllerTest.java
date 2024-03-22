package org.example.service.cucumberTests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.TrainerSummary;
import org.example.controller.TrainerSummaryController;
import org.example.dto.ResponseDto;
import org.example.service.TrainerSummaryService;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class TrainerSummaryControllerTest {
    private String username;
    private String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhemltam9uLmFsaWpvbm92MSIsImlhdCI6MTcxMDg0MzA3MSwiZXhwIjoxNzIwODQzMDcxfQ.lrjBoPVPTqtyR1RDQ-c2jV6ZBbEZTePVsk6WormF0ok";
    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<?> response;
    @Mock
    TrainerSummaryService trainerSummaryService;
    @InjectMocks
    TrainerSummaryController trainerSummaryController;

    public TrainerSummaryControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("a trainer with username {string} exists")
    public void a_trainer_with_username_exists(String trainerUsername) {
        this.username = trainerUsername;
    }

    @When("a request is made to retrieve the trainer summary")
    public void a_request_is_made_to_retrieve_the_trainer_summary_for_username() throws Exception {
        ResponseDto responseDto =new ResponseDto();
        Mockito.when(trainerSummaryService.get(anyString())).thenReturn(responseDto);
        response=trainerSummaryController.get(username);
       }

    @Then("the trainer summary should be successfully retrieved")
    public void the_trainer_summary_should_be_successfully_retrieved() {
        Assert.assertEquals(200,response.getStatusCodeValue());
    }
}

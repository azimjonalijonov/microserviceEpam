package com.example.EpamSpringBoot.cucumberTest;

import com.example.EpamSpringBoot.trainee.Trainee;
import com.example.EpamSpringBoot.trainee.TraineeController;
import com.example.EpamSpringBoot.trainee.TraineeService;
import com.example.EpamSpringBoot.trainee.dto.PostTraineeDTO;
import com.example.EpamSpringBoot.trainee.dto.UpdateTraineeDTO;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class TraineeTest {

    private ResponseEntity<?> response;
    private PostTraineeDTO postTraineeDTO;
    private String username;
    private UpdateTraineeDTO updateTraineeDTO;


    @Mock
    private UserService userService;

    @Mock
    private TraineeService traineeService;


    @InjectMocks
    private TraineeController traineeController;

    public TraineeTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Given("I have the following trainee information firstname {string} lastname {string} address {string}")
    public void i_have_the_following_trainee_information(String firstname, String lastname, String adress) {
        postTraineeDTO = new PostTraineeDTO();
        postTraineeDTO.setFirstname(firstname);
        postTraineeDTO.setLastname(lastname);
        postTraineeDTO.setAddress(adress);
    }

    @When("I post the trainee information")
    public void i_post_the_trainee_information() {
        Trainee trainee = new Trainee();
        User user = new User();
        trainee.setUser(user);
        when(userService.create(any())).thenReturn(user);
        when(traineeService.create(any())).thenReturn(trainee);
        response = traineeController.post(postTraineeDTO);
    }

    @Then("the trainee is successfully created")
    public void the_trainee_is_successfully_created() {
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Given("I have the trainee username {string}")
    public void i_have_the_trainee_username(String username) {
        this.username = username;

    }

    @When("I get trainee information")
    public void i_get_trainee_information() {
        User user = new User();
        user.setUsername(username);
        Trainee trainee = new Trainee();
        trainee.setUser(user);
        when(userService.readByUsername(username)).thenReturn(user);
        when(traineeService.readByUsername(username)).thenReturn(trainee);
        response = traineeController.get(username);
    }

    @Then("I receive the trainee details")
    public void i_receive_the_trainee_details() throws JsonProcessingException {
        Assert.assertEquals(200, response.getStatusCodeValue());

    }

    @And("I update the trainee information with firstname {string}")
    public void update_the_trainee_information_with_firtname(String firstname) {
        updateTraineeDTO = new UpdateTraineeDTO();
        updateTraineeDTO.setUsername(username);
        updateTraineeDTO.setFirstname(firstname);
        updateTraineeDTO.setLastname("alijonov");
        updateTraineeDTO.setActive("true");
        updateTraineeDTO.setAddress("namangan");

    }

    @When("I update the trainee information")
    public void i_update_the_trainee_information() {
        User user = new User();
        user.setUsername(username);
        Trainee trainee = new Trainee();
        trainee.setUser(user);
        when(userService.readByUsername(username)).thenReturn(user);
        when(userService.update(any())).thenReturn(user);
        when(traineeService.readByUsername(username)).thenReturn(trainee);
        when(traineeService.update(any())).thenReturn(trainee);
        when(traineeService.getTraineeTrainingList(any(), any())).thenReturn(new ArrayList<>());
        response = traineeController.update(username, updateTraineeDTO);
    }

    @Then("the trainee information is successfully updated")
    public void the_trainee_information_is_succesfully_updaed() throws JsonProcessingException {
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @When("I delete the trainee")
    public void i_delete_the_trainee() {
        User user = new User();
        user.setUsername(username);
        Trainee trainee = new Trainee();
        trainee.setUser(user);
        when(userService.readByUsername(username)).thenReturn(user);
        doNothing().when(traineeService).deleteByUsername(username);
        response = traineeController.delete(username);
    }

    @Then("the trainee is successfully deleted")
    public void the_trainee_successfully_deleted() {
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

}

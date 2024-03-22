package com.example.EpamSpringBoot.cucumberTest;

import com.example.EpamSpringBoot.trainer.Trainer;
import com.example.EpamSpringBoot.trainer.TrainerController;
import com.example.EpamSpringBoot.trainer.TrainerService;
import com.example.EpamSpringBoot.trainer.dto.PostTrainerDTO;
import com.example.EpamSpringBoot.trainingType.TrainingType;
import com.example.EpamSpringBoot.trainingType.TrainingTypeService;
import com.example.EpamSpringBoot.trainingType.dto.TrainingTypeDTO;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TrainerTest {

    private ResponseEntity<?> response;
    private PostTrainerDTO postTrainerDTO;
    private TrainingTypeDTO trainingTypeDTO;
    private String username;
    @Mock
    private UserService userService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainerController trainerController;

    public TrainerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Given("I have the following trainer information: {string} {string} {string}")
    public void i_have_the_following_trainer_information(String firstName, String lastName, String trainingTypeID) {
        postTrainerDTO = new PostTrainerDTO();
        trainingTypeDTO = new TrainingTypeDTO();
        trainingTypeDTO.setId(Long.valueOf(trainingTypeID));
        postTrainerDTO.setFirstname(firstName);
        postTrainerDTO.setLastname(lastName);
        postTrainerDTO.setTrainingTypeDTO(trainingTypeDTO);
    }

    @When("I post the trainer information")
    public void i_post_the_trainer_information() {
        User mockUser = new User();
        when(userService.create(any(User.class))).thenReturn(mockUser);
        TrainingType mockTrainingType = new TrainingType();
        when(trainingTypeService.readById(any())).thenReturn(mockTrainingType);
        response = trainerController.post(postTrainerDTO);
    }

    @Then("the trainer is successfully created")
    public void the_trainer_is_successfully_created() {
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Given("I have the trainer username {string}")
    public void i_have_the_trainer_username(String username) {
        this.username = username;
    }

    @When("I get trainer information")
    public void i_get_trainer_information() {
        User user = new User();
        user.setUsername(username);

        when(userService.readByUsername(username)).thenReturn(user);
        when(trainerService.readByUsername(username)).thenReturn(new Trainer());

        response = trainerController.get(username);

    }

    @Then("I receive the trainer details")
    public void i_receive_the_trainer_details() {
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @When("I get trainers with specializations")
    public void i_get_trainers_with_specializations() throws JsonProcessingException {
        User user = new User();
        user.setUsername(username);

        when(userService.readByUsername(username)).thenReturn(user);
        when(trainerService.getSpecificTrainers()).thenReturn(new ArrayList<>());

        response = trainerController.getSpecial();

    }

    @Then("I receive a list of trainers with specializations")
    public void i_receive_list_of_trainers_with_specialization() {
        Assert.assertEquals(200, response.getStatusCodeValue());


    }

    @When("I activate or deactivate the trainer")
    public void i_activate_deactivate_trainer() {
        boolean bool = true;
        User user = new User();
        user.setUsername(username);

        Trainer trainer = new Trainer();
        trainer.setId(1L);

        when(userService.readByUsername(username)).thenReturn(user);
        when(trainerService.readByUsername(username)).thenReturn(trainer);

        response = trainerController.changeStatus(username, bool);

    }

    @Then("the trainer status is successfully changed")
    public void trainer_status_changed() {
        Assert.assertEquals(200, response.getStatusCodeValue());
    }


}

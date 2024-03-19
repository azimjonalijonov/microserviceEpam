package com.example.EpamSpringBoot.cucumberTest;

import com.example.EpamSpringBoot.trainee.Trainee;
import com.example.EpamSpringBoot.trainee.dto.PostTraineeDTO;
import com.example.EpamSpringBoot.trainee.dto.UpdateTraineeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class TraineeTest {
    private String token ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhemltam9uLmFsaWpvbm92MSIsImlhdCI6MTcxMDg0MzA3MSwiZXhwIjoxNzIwODQzMDcxfQ.lrjBoPVPTqtyR1RDQ-c2jV6ZBbEZTePVsk6WormF0ok";
    private RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<?> response;
   private PostTraineeDTO postTraineeDTO;
   private String username ;
    private  UpdateTraineeDTO updateTraineeDTO;


    @Given("I have the following trainee information firstname {string} lastname {string} address {string}")
    public void i_have_the_following_trainee_information(String firstname, String lastname, String adress) {
        postTraineeDTO =new PostTraineeDTO();
        postTraineeDTO.setFirstname(firstname);
        postTraineeDTO.setLastname(lastname);
        postTraineeDTO.setAddress(adress);
    }

    @When("I post the trainee information")
    public void i_post_the_trainee_information() {
        HttpHeaders headers =new HttpHeaders();
        headers.set("Content-type","Application/json");
        headers.set("Authorization","Bearer "+token);
        HttpEntity<PostTraineeDTO> request=new HttpEntity<>(postTraineeDTO,headers);
        response =restTemplate.exchange("http://localhost:8087/api/trainee/post", HttpMethod.POST,request,String.class);

    }

    @Then("the trainee is successfully created")
    public void the_trainee_is_successfully_created() {
        Assert.assertEquals(200,response.getStatusCodeValue());

    }

    @Given("I have the trainee username {string}")
    public void i_have_the_trainee_username(String username) {
        this.username =username;

    }

    @When("I get trainee information")
    public void i_get_trainee_information() {
        HttpHeaders headers =new HttpHeaders();
        headers.set("Content-type","Application/json");
        headers.set("Authorization","Bearer "+token);
        HttpEntity<String> httpEntity =new HttpEntity<>(headers);
        response =restTemplate.exchange("http://localhost:8087/api/trainee/get"+"?username="+username,HttpMethod.GET,httpEntity,String.class);
    }

    @Then("I receive the trainee details")
    public void i_receive_the_trainee_details() throws JsonProcessingException {
      Assert.assertEquals(200,response.getStatusCodeValue());

    }

    @And("I update the trainee information with firstname {string}")
    public void update_the_trainee_information_with_firtname(String firstname){
         updateTraineeDTO =new UpdateTraineeDTO();
         updateTraineeDTO.setUsername(username);
         updateTraineeDTO.setFirstname(firstname);
         updateTraineeDTO.setLastname("alijonov");
         updateTraineeDTO.setActive("true");
         updateTraineeDTO.setAddress("namangan");

    }

    @When("I update the trainee information")
    public  void i_update_the_trainee_information(){
        HttpHeaders headers =new HttpHeaders();
        headers.set("Content-type","Application/json");
        headers.set("Authorization","Bearer "+token);
        HttpEntity<UpdateTraineeDTO> request=new HttpEntity<>(updateTraineeDTO,headers);
        response =restTemplate.exchange("http://localhost:8087/api/trainee/update"+"?username="+username,HttpMethod.PUT,request,String.class);


    }

    @Then("the trainee information is successfully updated")
    public void the_trainee_information_is_succesfully_updaed() throws JsonProcessingException {
        Assert.assertEquals(200,response.getStatusCodeValue());
        ObjectMapper objectMapper =new ObjectMapper();

        UpdateTraineeDTO updateTraineeDTO1 =objectMapper.readValue(Objects.requireNonNull(response.getBody().toString()),UpdateTraineeDTO.class);
        Assert.assertEquals("john",updateTraineeDTO1.getFirstname());
    }

@When("I delete the trainee")
    public void i_delete_the_trainee(){
    HttpHeaders headers =new HttpHeaders();
    headers.set("Content-type","Application/json");
    headers.set("Authorization","Bearer "+token);
    HttpEntity<String> request=new HttpEntity<>(headers);

    response =restTemplate.exchange("http://localhost:8087/api/trainee/delete"+"?username="+username,HttpMethod.DELETE,request,String.class);
}
@Then("the trainee is successfully deleted")
    public void the_trainee_successfully_deleted(){
        Assert.assertEquals(200,response.getStatusCodeValue());
}

}

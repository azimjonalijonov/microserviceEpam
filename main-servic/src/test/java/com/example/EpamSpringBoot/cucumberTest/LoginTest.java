package com.example.EpamSpringBoot.cucumberTest;

import com.example.EpamSpringBoot.config.bruteforceprotect.DefaultBruteForceProtectorSrvice;
import com.example.EpamSpringBoot.config.jwt.JwtService;
import com.example.EpamSpringBoot.general.LoginController;
import com.example.EpamSpringBoot.general.dto.LoginDTO;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

public class LoginTest {
    private RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<?> response;
    Long badRequest = 1l;
    @Mock
    UserService userService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    DefaultBruteForceProtectorSrvice defaultBruteForceProtectorSrvice;

    @Mock
    JwtService jwtService;
    @InjectMocks
    LoginController loginController;

    public LoginTest() {
        MockitoAnnotations.openMocks(this);
    }


    LoginDTO loginDTO;

    @Given("^a user with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void a_user_with_username_and_password(String username, String password) {
        loginDTO = new LoginDTO();
        loginDTO.setUsername(username);
        loginDTO.setPassword(password);
    }

    @Given("^no user with username \"([^\"]*)\"$")
    public void no_user_with_username(String username) {
        loginDTO = new LoginDTO();
        loginDTO.setUsername(username);

    }

    @When("^the user tries to login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void the_user_tries_to_login_with_username_and_password(String username, String password) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword(password);
        loginDTO.setUsername(username);
        User user = new User();

        Mockito.when(userService.readByUsername(any())).thenReturn(user);
        Mockito.when(jwtService.generateToken(user)).thenReturn("jwt");
        Mockito.when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(user, null, null));
        Mockito.doNothing().when(defaultBruteForceProtectorSrvice).resetBruteForceAttack(any());
        try {
            response = loginController.login(loginDTO);
        } catch (Exception ex) {
            badRequest = 400l;
        }

    }

    @When("^the user tries to login with wrongusername \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void the_user_tries_to_login_with_wrongusername_and_password(String username, String password) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword(password);
        loginDTO.setUsername(username);


        Mockito.when(userService.readByUsername(any())).thenReturn(null);
            response = loginController.login(loginDTO);
            badRequest = 2l;


    }

    @Then("^the user should receive a JWT token$")
    public void the_user_should_receive_a_JWT_token() {
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());
    }

    @Then("^the user should receive an error message indicating the account does not exist$")
    public void the_user_should_receive_an_error_message_indicating_the_account_does_not_exist() {
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}














































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































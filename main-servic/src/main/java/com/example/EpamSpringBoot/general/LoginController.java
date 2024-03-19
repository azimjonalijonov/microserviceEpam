package com.example.EpamSpringBoot.general;

import com.example.EpamSpringBoot.config.bruteforceprotect.DefaultBruteForceProtectorSrvice;
import com.example.EpamSpringBoot.config.jwt.JwtService;
import com.example.EpamSpringBoot.general.dto.ChangeLoginDTO;
import com.example.EpamSpringBoot.general.dto.LoginDTO;
import com.example.EpamSpringBoot.trainee.TraineeService;
import com.example.EpamSpringBoot.trainer.TrainerService;
import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	TraineeService traineeService;

	@Autowired
	TrainerService trainerService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	DefaultBruteForceProtectorSrvice defaultBruteForceProtectorSrvice;

	@Autowired
	JwtService jwtService;

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
		User user = userService.readByUsername(loginDTO.getUsername());
		String jwt = null;
		if (user == null) {
			Map<String, String> map = new HashMap<>();
			map.put("message", "your account doesnot exist");
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
		else {
			jwt = jwtService.generateToken(user);

		}
		LocalDateTime blockTime = user.getLockTime();
		LocalDateTime dueTime = null;
		if (blockTime != null) {
			dueTime = blockTime.plusMinutes(1);
		}

		if (blockTime != null && dueTime != null && dueTime.isAfter(LocalDateTime.now())) {
			Map<String, String> map = new HashMap<>();
			map.put("error: ", "you can not login because of  too many wrong attempts ");
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
		if (blockTime != null && dueTime != null && dueTime.isBefore(LocalDateTime.now())) {
			defaultBruteForceProtectorSrvice.resetBruteForceAttack(loginDTO.getUsername());

		}

		try {

			authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

		}
		catch (Exception ex) {
			ex.printStackTrace();
			Map<String, String> map = new HashMap<>();
			map.put("message", "your credentials are incorrect");
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		defaultBruteForceProtectorSrvice.resetBruteForceAttack(loginDTO.getUsername());

		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("token", jwt);
		return ResponseEntity.ok(jsonResponse);
	}

	@PutMapping("/update")
	public ResponseEntity update(@RequestBody ChangeLoginDTO changeLoginDTO) {
		if (changeLoginDTO.getNewPassword().equals(null) || changeLoginDTO.getOldPassword().equals(null)
				|| changeLoginDTO.getUsername().equals(null)) {
			throw new RuntimeException("old, new passwords and username must be not null");
		}
		if (userService.readByUsername(changeLoginDTO.getUsername()) == null) {
			throw new RuntimeException("user not found");
		}
		User user = userService.readByUsername(changeLoginDTO.getUsername());
		if (user.getPassword().equals(changeLoginDTO.getOldPassword())) {
			user.setPassword(changeLoginDTO.getNewPassword());
		}
		else {
			throw new RuntimeException("password incorrect");
		}
		System.out.println("________++++++++++sssssssssssssssssssssssssssssssssssssssssssssssssssssssss++");
		userService.updatePassword(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

package com.example.EpamSpringBoot.user;

import com.example.EpamSpringBoot.config.jwt.JwtService;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.impl.UserErrorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final UserErrorValidator userErrorValidator;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final AuthenticationManager authenticationManager;

	public static String currentP;

	@Autowired
	public UserService(UserRepository userRepository, UserErrorValidator userErrorValidator,
			BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.userErrorValidator = userErrorValidator;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public List<User> readAll() {
		List<User> userList = userRepository.findAll();
		return userList;
	}

	public User readById(Long id) {

		User user = userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("user is not found with this id : " + id));
		return user;

	}

	public User create(User createRequest) {
		if (userErrorValidator.isValidParamsForCreate(createRequest)) {
			String username = generateUsername(createRequest.getFirstName(), createRequest.getLastName());
			String password = generatePassword();
			createRequest.setUsername(username);
			createRequest.setPassword(bCryptPasswordEncoder.encode(password));
			User user = userRepository.save(createRequest);
			currentP = password;

			return user;
		}
		else {
			throw new ValidatorException("Something wrong with parameters");
		}
	}

	public User update(User updateRequest) {
		if (userErrorValidator.isValidParamsForUpdate(updateRequest)) {
			userRepository.save(updateRequest);
			return updateRequest;
		}
		throw new ValidatorException("Something wrong with parameters");
	}

	public User updatePassword(User user) {

		return userRepository.save(user);

	}

	public User readByUsername(String username) {
		User user = userRepository.findUserByUsername(username);

		return user;
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	public String generateUsername(String firstName, String lastName) {
		String username = firstName + "." + lastName;
		List<User> users = readAll();

		for (User user : users) {
			if (user.getUsername().equals(username)) {
				username += users.size() + 1;
				return username;
			}
		}
		return username;

	}

	private String generatePassword() {

		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder password = new StringBuilder();
		Random random = new Random();
		for (int i = 1; i <= 10; i++) {
			password.append(characters.charAt(random.nextInt(characters.length())));
		}
		return password.toString();
	}

}

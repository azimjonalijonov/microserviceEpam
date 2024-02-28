package com.example.EpamSpringBoot.user;

import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.impl.UserErrorValidator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserErrorValidator userErrorValidator;

	@InjectMocks
	private UserService userService;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void readAll_shouldReturnListOfUsers() {
		List<User> expectedUsers = new ArrayList<>();
		when(userRepository.findAll()).thenReturn(expectedUsers);

		List<User> users = userService.readAll();
		assertNotNull(users);
		assertEquals(expectedUsers, users);
	}

	@Test
	void readById_shouldReturnUser_whenUserExists() {
		long userId = 1L;
		User expectedUser = new User();
		when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

		User user = userService.readById(userId);
		assertNotNull(user);
		assertEquals(expectedUser, user);
	}

	@Test
	void readById_shouldThrowException_whenUserDoesNotExist() {
		long nonExistingUserId = 999L;
		when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> userService.readById(nonExistingUserId));
	}

	@Test
	void create_shouldCreateUser_whenParamsAreValid() {
		User user = new User();
		when(userErrorValidator.isValidParamsForCreate(user)).thenReturn(true);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
		userService.create(user);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void create_shouldThrowException_whenParamsAreInvalid() {
		User user = new User();
		when(userErrorValidator.isValidParamsForCreate(user)).thenReturn(false);
		assertThrows(ValidatorException.class, () -> userService.create(user));
		verify(userRepository, never()).save(user);
	}

	@Test
	void update_shouldUpdateUser_whenParamsAreValid() {
		User user = new User();
		when(userErrorValidator.isValidParamsForUpdate(user)).thenReturn(true);
		User updatedUser = userService.update(user);
		assertNotNull(updatedUser);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void update_shouldThrowException_whenParamsAreInvalid() {
		User user = new User();
		when(userErrorValidator.isValidParamsForUpdate(user)).thenReturn(false);
		assertThrows(ValidatorException.class, () -> userService.update(user));
		verify(userRepository, never()).save(user);
	}

	@Test
	void updatePassword_shouldUpdatePassword() {
		User user = new User();
		when(userRepository.save(user)).thenReturn(new User());
		User updatedUser = userService.updatePassword(user);
		assertNotNull(updatedUser);
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void readByUsername_shouldReturnUser() {
		String username = "testUser";
		User expectedUser = new User();
		when(userRepository.findUserByUsername(username)).thenReturn(expectedUser);
		User user = userService.readByUsername(username);
		assertNotNull(user);
		assertEquals(expectedUser, user);
	}

	@Test
	void deleteById_shouldDeleteUser() {
		long userId = 1L;
		userService.deleteById(userId);
		verify(userRepository, times(1)).deleteById(userId);
	}

}

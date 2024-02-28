package com.example.EpamSpringBoot.util.validation.impl;

import com.example.EpamSpringBoot.user.User;
import com.example.EpamSpringBoot.util.exception.ValidatorException;
import com.example.EpamSpringBoot.util.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class UserErrorValidator implements Validator<User> {

	@Override
	public boolean isValidParamsForCreate(User entity) {
		if (entity.getFirstName() == null) {
			throw new ValidatorException("FirstName should not be null");
		}
		else if (entity.getLastName() == null) {
			throw new ValidatorException("LastName should not be null");

		}
		// else if (entity.getActive() == null) {
		// throw new ValidatorException("isActive should not be null");
		// }
		return true;
	}

	@Override
	public boolean isValidParamsForUpdate(User entity) {
		if (entity.getFirstName() == null) {
			throw new ValidatorException("FirstName should not be null");
		}
		else if (entity.getId() == null) {
			throw new ValidatorException("id should not be null");
		}
		else if (entity.getLastName() == null) {
			throw new ValidatorException("LastName should not be null");
		}
		else if (entity.getUsername() == null) {
			throw new ValidatorException("Username should not be null");
		}
		else if (entity.getPassword() == null) {
			throw new ValidatorException("Password should not be null");
		}
		// else if (entity.getActive() == null) {
		// throw new ValidatorException("isActive should not be null");
		// }
		return true;
	}

}

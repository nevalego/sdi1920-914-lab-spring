package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.User;
import com.uniovi.services.UsersService;

@Component
public class UserRegistrationValidator implements Validator {

	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");

		 char [] dnic=user.getDni().toCharArray();
		if (user.getDni().length() < 9 || user.getDni().length() > 9) {
			errors.rejectValue("dni", "Error.signup.dni.length");
		}
		if(   Character.isAlphabetic(dnic[8])) {
			errors.rejectValue("dni", "Error.signup.dni.char");
		}
		if (usersService.getUserByDni(user.getDni()) != null) {
			errors.rejectValue("dni", "Error.signup.dni.duplicate");
		}
		if (user.getName().length() < 5 || user.getName().length() >10) {
			errors.rejectValue("name", "Error.signup.name.length");
		}
		if (user.getLastName().length() < 4 || user.getLastName().length() > 24) {
			errors.rejectValue("lastName", "Error.signup.lastName.length");
		}

		if (user.getPassword().length() < 3 || user.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", "Error.signup.passwordConfirm.coincidence");
		}
	}
}
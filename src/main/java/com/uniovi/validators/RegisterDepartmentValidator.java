package com.uniovi.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Department;
import com.uniovi.services.DepartmentService;

@Component
public class RegisterDepartmentValidator implements Validator {

	@Autowired
	private DepartmentService departmentService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Department.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Department department = (Department) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");

			
			if (department.getDescription().length() < 6) {
				errors.rejectValue("description", "Error.registerDepartment.description.length");
			}
	}
}
package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Department;
import com.uniovi.repositories.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	public List<Department> getDepartments() {
		List<Department> departments = new ArrayList<Department>();
		departmentRepository.findAll().forEach(departments::add);
		return departments;
	}

	public Department getDepartment(Long id) {
		return departmentRepository.findById(id).get();
	}

	public void addDepartment(Department department) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		departmentRepository.save(department);
	}

	public void deleteDepartment(Long id) {
		departmentRepository.deleteById(id);
	}
	
}

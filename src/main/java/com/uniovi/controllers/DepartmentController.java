package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Department;
import com.uniovi.services.DepartmentService;
import com.uniovi.validators.RegisterDepartmentValidator;

@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentsService;

	
	@Autowired
	private RegisterDepartmentValidator registerDepartmentValidator;

	@RequestMapping("/department/list")
	public String getListado(Model model) {
		model.addAttribute("departmentsList", departmentsService.getDepartments());
		return "department/list";
	}
	
	@RequestMapping("/department/list/update")
	public String updateList(Model model) {
		model.addAttribute("departmentsList", departmentsService.getDepartments());
		return "department/list :: tableDepartments";
	}

	@RequestMapping(value = "/department/add")
	public String getDepartment(Model model) {
		model.addAttribute("departmentsList", departmentsService.getDepartments());
		return "department/add";
	}

	@RequestMapping(value = "/department/add", method = RequestMethod.POST)
	public String setDepartment(@ModelAttribute Department department,BindingResult result) {
		registerDepartmentValidator.validate(department, result);
		if( result.hasErrors()) {
			return "department/add";
		}
		departmentsService.addDepartment(department);
		return "redirect:/department/list";
	}

	@RequestMapping("/department/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("department", departmentsService.getDepartment(id));
		return "department/details";
	}

	@RequestMapping("/department/delete/{id}")
	public String delete(@PathVariable Long id) {
		departmentsService.deleteDepartment(id);
		return "redirect:/department/list";
	}

	@RequestMapping(value = "/department/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("department", departmentsService.getDepartment(id));
		model.addAttribute("departmentsList", departmentsService.getDepartments());
		return "department/edit";
	}

	@RequestMapping(value = "/department/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Department department) {
		Department original = departmentsService.getDepartment(id);
		original.setDescription(department.getDescription());
		departmentsService.addDepartment(original);
		return "redirect:/department/details/" + id;
	}
}

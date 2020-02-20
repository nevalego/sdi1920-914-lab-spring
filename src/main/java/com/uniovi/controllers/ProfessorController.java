package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Professor;
import com.uniovi.services.DepartmentService;
import com.uniovi.services.ProfessorService;
import com.uniovi.validators.AddProfessorValidator;

@Controller
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private AddProfessorValidator addProfessorValidator;
	
	@RequestMapping("/professor/list")
	public String getList(Model model) {
		model.addAttribute("professorList", professorService.getProfessors());
		return "/professor/list";
	}

	@RequestMapping(value = "/professor/add")
	public String getProfessor(Model model) {
		model.addAttribute("departmentsList",departmentService.getDepartments());
		return "professor/add";
	}

	@RequestMapping(value ="professor/add", method = RequestMethod.POST)
	public String setProfessor(@ModelAttribute Professor professor, BindingResult result) {
		addProfessorValidator.validate(professor, result);
		if( result.hasErrors()) {
			return "professor/add";
		}
		professorService.addProfessor(professor);
		return "redirect:/professor/list";
	}

	@RequestMapping("/professor/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("professor", professorService.getProfessor(id));
		return "professor/details";
	}

	@RequestMapping("/professor/delete/{id}")
	public String deleteProfessor(@PathVariable Long id) {
		professorService.deleteProfessor(id);
		return "redirect:/professor/list";
	}

	@RequestMapping(value = "/professor/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("professor", professorService.getProfessor(id));
		model.addAttribute("departmentsList", departmentService.getDepartments());
		return "professor/edit";
	}

	@RequestMapping(value = "/professor/edit/", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute Professor professor) {
		Professor original = professorService.getProfessor(id); 
		// modificar solo dni, nombre, apellidos y categoria
		original.setDni(professor.getDni());
		original.setNombre(professor.getNombre());
		original.setApellidos(professor.getApellidos());
		original.setCategoria(professor.getCategoria());
		professorService.addProfessor(original);
		return "redirect:/professor/details/" + id;
	}
}

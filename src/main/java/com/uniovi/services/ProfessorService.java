package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.Professor;
import com.uniovi.repositories.ProfessorRepository;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;

	public List<Professor> getProfessors() {
		List<Professor> professors = new ArrayList<Professor>();
		professorRepository.findAll().forEach(professors::add);
		return professors;
	}

	public Professor getProfessor(Long id) {
		return professorRepository.findById(id).get();
	}

	public void addProfessor(Professor professor) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		professorRepository.save(professor);
	}

	public void deleteProfessor(Long id) {
		professorRepository.deleteById(id);
	}

}

package com.uniovi.services;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.uniovi.entities.Professor;
import com.uniovi.repositories.ProfessorRepository;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;

	public Page<Professor> getProfessors(Pageable pageable) {
		Page<Professor> professors =new PageImpl<Professor>(new LinkedList<Professor>());
		professors = professorRepository.findAll(pageable);
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

	public Professor getProfessorByDni(String dni) {
		return professorRepository.findByDni(dni);
	}

}

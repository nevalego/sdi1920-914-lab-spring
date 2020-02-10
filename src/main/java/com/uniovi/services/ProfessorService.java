package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.uniovi.entities.Professor;

@Service
public class ProfessorService {

	private List<Professor> professors = new ArrayList<Professor>();

	public List<Professor> getProfessors() {
		return professors;
	}

	public Professor getProfessor(Long id) {
		for (Professor professor : professors) {
			if (professor.getId() == id)
				return professor;
		}
		return null;
	}

	public void addProfessor(Professor professor) {
		// Si en Id es null le asignamos el ultimo + 1 de la lista
		if (professors.size() > 0) {
			professor.setId(professors.get(professors.size() - 1).getId() + 1);
		} else {
			professor.setId(Long.valueOf(1));
		}
		professors.add(professor);
	}

	public void deleteProfessor(Long id) {
		for (Professor professor : professors) {
			if (professor.getId() == id)
				professors.remove(professor);
		}
	}

}

package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uniovi.entities.Professor;

public interface ProfessorRepository extends CrudRepository<Professor, Long>{

}

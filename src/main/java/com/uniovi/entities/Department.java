package com.uniovi.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {

	@Id
	@GeneratedValue
	private Long id;
	private String description;
	
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
	private Set<Professor> professors;

	public Department(String description) {
		this.description = description;
	}
	
	public Department() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Professor> getProfessors() {
		return professors;
	}

	public void setProfessors(Set<Professor> professors) {
		this.professors = professors;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", description=" + description + ", professors=" + professors + "]";
	}
	
	
}

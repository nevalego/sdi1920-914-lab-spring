package com.uniovi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Professor {

	@Id
	@GeneratedValue
	private Long id;
	private String dni;
	private String nombre;
	private String apellidos;
	private String categoria;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	public Professor(String dni, String nombre, String apellidos, String categoria) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.categoria = categoria;
	}

	public Professor(String dni, String nombre, String apellidos, String categoria, Department department) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.categoria = categoria;
		this.department = department;
	}

	public Professor() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", categoria=" + categoria + "]";
	}

}

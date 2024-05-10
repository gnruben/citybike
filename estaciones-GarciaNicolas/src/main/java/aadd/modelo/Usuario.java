package aadd.modelo;

import java.time.LocalDate;

public class Usuario {

	private String email;
	private LocalDate fechaNacimiento;
	private String telef;
	private String nombre;

	private RolUsuario rol;

	public Usuario() {

	}

	public Usuario(String email, String telef, LocalDate fechaNacimiento, String nombre, RolUsuario rol) {
		this.setEmail(email);
		this.fechaNacimiento = fechaNacimiento;
		this.setTelef(telef);
		this.nombre = nombre;
		this.rol = rol;
	}

	// Getters and setters

	public RolUsuario getRol() {
		return rol;
	}

	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getTelef() {
		return telef;
	}

	public void setTelef(String telef) {
		this.telef = telef;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}

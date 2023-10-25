package aadd.modelo;

import java.time.LocalDate;

public class Usuario {

	private String email;
	private LocalDate fechaNacimiento;
	private String telef;
	private String nombre;
	private boolean esAdministrador; 
	
	public Usuario(String email, String telef, LocalDate fechaNacimiento, String nombre, boolean esAdministrador) {
		this.setEmail(email);
		this.fechaNacimiento = fechaNacimiento;
		this.setTelef(telef);
		this.nombre = nombre;
		this.esAdministrador = esAdministrador;
		
	}
	
    // Getters and setters

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
//	public void setFechaNacimiento(LocalDate fechaNacimiento) {
//		this.fechaNacimiento = fechaNacimiento;
//	}
	public String getTelef() {
		return telef;
	}
	public void setTelef(String telef) {
		this.telef = telef;
	}
	public String getNombre() {
		return nombre;
	}
//	public void setNombre(String nombre) {
//		Nombre = nombre;
//	}
	
	public boolean esAdministrador() {
		return esAdministrador;
	}
	


		
	
	//Si es administrador puede:
		// Crear nuebos puntos de préstamo (estaciones)
		//dar alta las bicis
		//dar baja las bicis
	
	
	
	//Usuarios pueden:
		//crear incidencia -> para indicar un desperfecto en una bicileta
		//
	

}

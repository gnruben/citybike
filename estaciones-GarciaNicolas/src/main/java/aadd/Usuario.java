package aadd;

import java.util.Date;

public class Usuario {

	private String email;
	private Date fechaNacimiento;
	private String telef;
	private String Nombre;
	private Rol rol; 
    // Getters and setters

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getTelef() {
		return telef;
	}
	public void setTelef(String telef) {
		this.telef = telef;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	
	public Rol getRol() {
		return rol;
	}
	
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	
	//Si es administrador puede:
		// Crear nuebos puntos de préstamo (estaciones)
		//dar alta las bicis
		//dar baja las bicis
	
	
	
	//Usuarios pueden:
		//crear incidencia -> para indicar un desperfecto en una bicileta
		//
	

}

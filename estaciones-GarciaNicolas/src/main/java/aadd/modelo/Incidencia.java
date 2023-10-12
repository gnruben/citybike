package aadd.modelo;

import java.time.LocalDate;

public class Incidencia {

	private LocalDate fecha;
	private String descripcion;
	
	private enum estado{PENDIENTE,RESUELTO}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	};
	
	
}

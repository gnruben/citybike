package aadd.modelo;

import java.time.LocalDate;

public class Incidencia {

	private String id;
	private LocalDate fecha;
	private String descripcion;
	private EstadoIncidencia estado;
	private String operarioAsignado;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	}
	public EstadoIncidencia getEstado() {
		return estado;
	}
	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	};
	
	public String getOperarioAsignado() {
		return operarioAsignado;
	}
	public void setOperarioAsignado(String operarioAsignado) {
		this.operarioAsignado = operarioAsignado;
	}
	
	
}

package aadd.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="incidencia")
public class Incidencia {
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="fecha_incidencia", columnDefinition = "DATE")
	private LocalDate fecha;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Enumerated
	private EstadoIncidencia estado;
	
	@Column(name="operarioAsignado")
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

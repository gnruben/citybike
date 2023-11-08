package aadd.modelo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import repositorio.Identificable;

@Entity
@Table(name="incidencia")
public class Incidencia implements Identificable{
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="fecha_inicio", columnDefinition = "DATE")
	private LocalDate fechaInicio;
	
	@Column(name="fecha_fin", columnDefinition = "DATE")
	private LocalDate fechaFin;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Enumerated
	private EstadoIncidencia estado;
	
	@Column(name="operarioAsignado")
	private String operarioAsignado;
	
	@ManyToOne
	@Column(name="bicicleta")
	private Bicicleta bicicleta;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
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
	}
	
	public String getOperarioAsignado() {
		return operarioAsignado;
	}
	public void setOperarioAsignado(String operarioAsignado) {
		this.operarioAsignado = operarioAsignado;
	}
	
	public Bicicleta getBicicleta() {
		return bicicleta;
	}
	
	public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}
	
}

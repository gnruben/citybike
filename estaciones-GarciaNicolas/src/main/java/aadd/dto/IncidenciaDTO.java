package aadd.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import aadd.modelo.Bicicleta;
import aadd.modelo.EstadoIncidencia;

public class IncidenciaDTO implements Serializable {

	private String id;
	
	private LocalDate fechaInicio;
	
	private LocalDate fechaFin;
	
	private String descripcion;
	
	private String motivoCierre;
	
	private EstadoIncidencia estado;
	

	private String idOperarioAsignado;
	

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


	public String getMotivoCierre() {
		return motivoCierre;
	}


	public void setMotivoCierre(String motivoCierre) {
		this.motivoCierre = motivoCierre;
	}


	public EstadoIncidencia getEstado() {
		return estado;
	}


	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}


	public String getIdOperarioAsignado() {
		return idOperarioAsignado;
	}


	public void setIdOperarioAsignado(String idOperarioAsignado) {
		this.idOperarioAsignado = idOperarioAsignado;
	}


	public Bicicleta getBicicleta() {
		return bicicleta;
	}


	public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}
	
}

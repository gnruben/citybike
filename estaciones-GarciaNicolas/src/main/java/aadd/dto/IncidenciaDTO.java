package aadd.dto;

import java.io.Serializable;
import java.time.LocalDate;

import aadd.modelo.EstadoIncidencia;

public class IncidenciaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private LocalDate fechaInicio;
	
	private LocalDate fechaFin;
	
	private String descripcion;
	
	private String motivoCierre;
	
	private EstadoIncidencia estado;
	

	private String idOperarioAsignado;
	
	private String idBicicleta;
	//private BicicletaDTO bicicleta;

	

	public IncidenciaDTO(String id, LocalDate fechaInicio, LocalDate fechaFin, String descripcion, String motivoCierre,
			EstadoIncidencia estado, String idOperarioAsignado,String idBicicleta) {
		super();
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.descripcion = descripcion;
		this.motivoCierre = motivoCierre;
		this.estado = estado;
		this.idOperarioAsignado = idOperarioAsignado;
		this.idBicicleta=idBicicleta;
	}


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


	public String getIdBicicleta() {
		return idBicicleta;
	}


	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	/*public String getEstadoString() {
		return getEstadoString().toString().toUpperCase();
	}*/

/*	public BicicletaDTO getBicicleta() {
		return bicicleta;
	}


	public void setBicicleta(BicicletaDTO bicicleta) {
		this.bicicleta = bicicleta;
	}*/
	
}

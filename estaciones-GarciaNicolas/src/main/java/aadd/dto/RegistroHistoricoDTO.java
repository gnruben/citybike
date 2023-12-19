package aadd.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class RegistroHistoricoDTO implements Serializable{
	private String id;
	private String idBici;
	private String idEstacion;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdBici() {
		return idBici;
	}
	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}
	public String getIdEstacion() {
		return idEstacion;
	}
	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
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
	
	
	
}

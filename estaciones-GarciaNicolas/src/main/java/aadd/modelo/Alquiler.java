package aadd.modelo;

import java.time.LocalDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import repositorio.Identificable;

public class Alquiler implements Identificable{

	private String id;
	private Usuario usuario; // TODO
    private Estacion estacionInicio; 
    private LocalDate fechaInicio; 
    private Estacion estacionFin;  // TODO
    private LocalDate fechaFin;
    
    @ManyToOne
	@JoinColumn(name="bicicleta_fk")
    private Bicicleta bicicleta;
    
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}
    
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Estacion getEstacionInicio() {
		return estacionInicio;
	}
	public void setEstacionInicio(Estacion estacionInicio) {
		this.estacionInicio = estacionInicio;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Estacion getEstacionFin() {
		return estacionFin;
	}
	public void setEstacionFin(Estacion estacionFin) {
		this.estacionFin = estacionFin;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	} 
     public Bicicleta getBicicleta() {
		return bicicleta;
	}
     public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}

    

	
}

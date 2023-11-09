package aadd.modelo;

import java.time.LocalDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import repositorio.Identificable;

public class Alquiler implements Identificable{

	private String id;
	private Usuario usuario; 
    private Estacion estacionInicio; 
    private LocalDate horaInicio; 
    private Estacion estacionFin; 
    private LocalDate horaFin;
    @ManyToOne
	@JoinColumn(name="bicicleta_fk")
    private Bicicleta bicicleta;
    
    
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
	public LocalDate getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(LocalDate horaInicio) {
		this.horaInicio = horaInicio;
	}
	public Estacion getEstacionFin() {
		return estacionFin;
	}
	public void setEstacionFin(Estacion estacionFin) {
		this.estacionFin = estacionFin;
	}
	public LocalDate getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(LocalDate horaFin) {
		this.horaFin = horaFin;
	} 
     public Bicicleta getBicicleta() {
		return bicicleta;
	}
     public void setBicicleta(Bicicleta bicicleta) {
		this.bicicleta = bicicleta;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}
    
    

	
}

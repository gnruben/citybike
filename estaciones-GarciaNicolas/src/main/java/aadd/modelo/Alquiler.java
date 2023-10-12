package aadd.modelo;

import java.time.LocalDate;

public class Alquiler {

	private Usuario usuario; 
    private Estacion estacionInicio; 
    private LocalDate horaInicio; 
    private Estacion estacionFin; 
    private LocalDate horaFin;
    
    
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
    
     
    
    

	
}

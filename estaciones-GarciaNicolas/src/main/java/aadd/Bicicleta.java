package aadd;

import java.time.LocalDate;
import java.util.List;

public class Bicicleta {

	private String codigo;
    private String modelo;
    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
    private String motivo;
    private List<Alquiler> historial;
    
    // Getters and setters
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public LocalDate getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(LocalDate fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public List<Alquiler> getHistorial() {
		return historial;
	}
	public void setHistorial(List<Alquiler> historial) {
		this.historial = historial;
	}
    
    
}

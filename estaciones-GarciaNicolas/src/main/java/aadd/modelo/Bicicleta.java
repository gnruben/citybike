package aadd.modelo;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import repositorio.Identificable;

@Entity
@Table(name="bicicleta")
public class Bicicleta implements Identificable {

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="modelo")
    private String modelo;
    
    @Column(name ="fecha_Alta", columnDefinition = "DATE")
    private LocalDate fechaAlta;
    
    @Column(name ="fecha_Baja", columnDefinition = "DATE")
    private LocalDate fechaBaja;
    
	@Column(name="motivo")
    private String motivo;
    
    @OneToMany(mappedBy = "bicicleta",
    fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Alquiler> historial;
    
    @OneToMany(mappedBy = "bicicleta",
    fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Incidencia> incidencias;
    
    @Column(name="isDisponible")
    private boolean isDisponible;
    
    
    // Getters and setters
    
    @Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
		
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
	public List<Incidencia> getIncidencias() {
		return incidencias;
	}
	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}
    public boolean isDisponible() {
		return isDisponible;
	}
    public void setDisponible(boolean isDisponible) {
		this.isDisponible = isDisponible;
	}
    
    public void addIncidencia(Incidencia incidencia) { 
    	for(Incidencia i: this.incidencias) {
    		if (i.getId() == (incidencia.getId()))
    			return;
    	}
    	this.incidencias.add(incidencia);
    }
    
    public void removeIncidencia(Incidencia incidencia) { 
    	for(Incidencia i: this.incidencias) {
    		if(i.getId() == incidencia.getId())
    			this.incidencias.remove(i);
    	}
    }
    
    
	@Override
	public String toString() {
		return "Bicicleta [id=" + id + ", modelo=" + modelo + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja
				+ ", motivo=" + motivo + ", historial=" + historial + ", isDisponible=" + isDisponible + ", incidencias=" + incidencias + "]";
	}
	
}

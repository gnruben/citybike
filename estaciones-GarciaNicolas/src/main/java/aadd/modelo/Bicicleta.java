package aadd.modelo;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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
    
    @Enumerated
    private EstadoBicicleta estado;
    
    
    // Getters and setters
    @Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
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
    public EstadoBicicleta getEstado() {
		return estado;
	}
	public void setEstado(EstadoBicicleta estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return "Bicicleta [id=" + id + ", modelo=" + modelo + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja
				+ ", motivo=" + motivo + ", historial=" + historial + ", estado=" + estado + "]";
	}
	
}

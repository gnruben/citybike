package aadd.modelo;

import java.time.LocalDate;

import javax.persistence.*;

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
	
	@Lob
	@Column(name="descripcion")
	private String descripcion;
	
	@Lob
	@Column(name="motivoCierre")
	private String motivoCierre;
	
	@Enumerated
	@Column(name="estado")
	private EstadoIncidencia estado;
	
	@Column(name="idOperarioAsignado")
	private String idOperarioAsignado;
	
	@ManyToOne
	@JoinColumn(name="bicicleta_fk")
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
	
	@Override
	public String toString() {
		return "Incidencia [id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", descripcion="
				+ descripcion + ", motivoCierre="+ motivoCierre + ", estado=" + estado + ", idOperarioAsignado=" + idOperarioAsignado + ", bicicleta="
				+ bicicleta.getId() + "]";
	}
		
}

package aadd.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;



import com.mongodb.client.model.geojson.Point;

import aadd.modelo.SitioTuristico;

public class EstacionDTO implements Serializable{
	
	private String id;
	private String nombre;
	private int numeroPuestos;
	private String direccionPostal;
	private double latitud;
	private double longitud;
	private Point ubicacion;
	private LocalDateTime fechaAlta;
	private List<SitioTuristico> sitiosTuristicos;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumeroPuestos() {
		return numeroPuestos;
	}
	public void setNumeroPuestos(int numeroPuestos) {
		this.numeroPuestos = numeroPuestos;
	}
	public String getDireccionPostal() {
		return direccionPostal;
	}
	public void setDireccionPostal(String direccionPostal) {
		this.direccionPostal = direccionPostal;
	}
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	public Point getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(Point ubicacion) {
		this.ubicacion = ubicacion;
	}
	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public List<SitioTuristico> getSitiosTuristicos() {
		return sitiosTuristicos;
	}
	public void setSitiosTuristicos(List<SitioTuristico> sitiosTuristicos) {
		this.sitiosTuristicos = sitiosTuristicos;
	} 
	
	
}

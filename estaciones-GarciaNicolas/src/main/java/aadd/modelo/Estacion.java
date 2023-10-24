package aadd.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import repositorio.Identificable;

public class Estacion implements Identificable {
	private String id;
	private String nombre;
	private int numeroPuestos;
	private String direccionPostal;
	private double latitud;
	private double longitud;
	private LocalDateTime fechaAlta;
	private List<ResumenSitioTuristico> sitiosTuristicos=new LinkedList<ResumenSitioTuristico>();
	
//	public Estacion(String nombre, int numeroPuestos, String direccionPostal, double latitud, double longitud) {
//        this.id = UUID.randomUUID().toString(); // Generar un ID único
//        this.nombre = nombre;
//        this.numeroPuestos = numeroPuestos;
//        this.direccionPostal = direccionPostal;
//        this.latitud = latitud;
//        this.longitud = longitud;
//        this.fechaAlta = LocalDateTime.now();
//        this.sitiosTuristicos = new ArrayList<>();
//    }
	
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
	public LocalDateTime getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDateTime fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public List<ResumenSitioTuristico> getSitiosTuristicos() {
		return sitiosTuristicos;
	}
	public void setSitiosTuristicos(List<ResumenSitioTuristico> sitiosTuristicos) {
		this.sitiosTuristicos = sitiosTuristicos;
	}
	
	
	public void asociarSitioTuristico(ResumenSitioTuristico sitioTuristico) {
        sitiosTuristicos.add(sitioTuristico);
    }
	

	
	@Override
	public String toString() {
		String s= "Estacion: { "+"id:"+id+", fecha de alta:"+fechaAlta+", latitud:"+latitud+", longitud:"+longitud+", sitiosTuristicos: [";
		for(ResumenSitioTuristico t: sitiosTuristicos) {
			s.concat("nombre:"+t.getNombre());
		}
		
		s.concat("]");
		return s;
		}
	
}

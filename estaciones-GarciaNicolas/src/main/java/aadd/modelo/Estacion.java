package aadd.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import repositorio.Identificable;

public class Estacion implements Identificable {
	private String id;
	private String nombre;
	private int numeroPuestos;
	private String direccionPostal;
	private double latitud;
	private double longitud;
	private LocalDateTime fechaAlta;
	private List<SitioTuristico> sitiosTuristicos; //= new LinkedList<SitioTuristico>();
	
	public Estacion() {
		//this.id = UUID.randomUUID().toString();
	
	}
	
	public Estacion(String nombre, int numeroPuestos, String direccionPostal, double latitud, double longitud) {
      //  this.id = UUID.randomUUID().toString(); // Generar un ID único
        this.nombre = nombre;
        this.numeroPuestos = numeroPuestos;
        this.direccionPostal = direccionPostal;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaAlta = LocalDateTime.now();
        this.sitiosTuristicos = new ArrayList<SitioTuristico>();
    }
	
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
	public List<SitioTuristico> getSitiosTuristicos() {
		return sitiosTuristicos;
	}
	public void setSitiosTuristicos(List<SitioTuristico> sitiosTuristicos) {
		this.sitiosTuristicos = sitiosTuristicos;
	}
	

	@Override
	public String toString() {
	    StringBuilder s = new StringBuilder("Estacion: { ");
	    	s.append("id: ").append(id)
	    	.append(", nombre:").append(nombre)
			.append(", numero puesto:").append(numeroPuestos)
	        .append(", fecha de alta: ").append(fechaAlta)
	        .append(", latitud: ").append(latitud)
	        .append(", longitud: ").append(longitud)
	        .append(", sitiosTuristicos: [");
	    
	    for (SitioTuristico t : sitiosTuristicos) {
	        s.append("nombre: ").append(t.getNombre()).append(", ");
	    }
	    
	    // Elimina la última coma y espacio si hay sitios turísticos
	    if (!sitiosTuristicos.isEmpty()) {
	        s.delete(s.length() - 2, s.length());
	    }
	    
	    s.append("] }");
	    
	    return s.toString();
	}
	

	
}

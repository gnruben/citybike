package aadd.modelo;

import java.util.List;
import java.util.UUID;

import repositorio.Identificable;

public class SitioTuristico implements Identificable{
	
	private String id;
	private String nombre;
	private String resumen;
	private List<String> categorias;
	private List<String> enlaces;
	private double latitud;
	private double longitud;
	private String urlArticulo;
	
	public SitioTuristico() {
		
	}
	public SitioTuristico( String nombre, String resumen, double latitud, double longitud, String url) {
		this.id = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.resumen = resumen;
		this.latitud = latitud;
		this.longitud = longitud;
		this.urlArticulo = url;
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
	public String getResumen() {
		return resumen;
	}
	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	public List<String> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}
	public List<String> getEnlaces() {
		return enlaces;
	}
	public void setEnlaces(List<String> enlaces) {
		this.enlaces = enlaces;
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
	public String getUrlArticulo() {
		return urlArticulo;
	}
	public void setUrlArticulo(String urlArticulo) {
		this.urlArticulo = urlArticulo;
	}
	@Override
	public String toString() {
		return "SitioTuristico [id=" + id + ", nombre=" + nombre + ", resumen=" + resumen + ", categorias=" + categorias
				+ ", enlaces=" + enlaces + ", latitud=" + latitud + ", longitud=" + longitud + ", urlArticulo="
				+ urlArticulo + "]";
	}

	

}

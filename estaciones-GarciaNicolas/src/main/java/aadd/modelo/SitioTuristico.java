package aadd.modelo;

import java.util.List;

public class SitioTuristico {
	private String nombre;
	private String resumen;
	private List<String> categorias;
	private List<String> enlaces;
	private double latitud;
	private double longitud;
	private String urlArticulo;
	
	public SitioTuristico(String nombre, String resumen, double latitud, double longitud, String url) {
		this.nombre = nombre;
		this.resumen = resumen;
		this.latitud = latitud;
		this.longitud = longitud;
		this.urlArticulo = url;
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
	
	
	

}

package aadd.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import repositorio.Identificable;

public class SitioTuristico implements Identificable{
	
	private String id;
	private String nombre;
	private String resumen;
	private List<String> categorias;
	private List<String> enlaces;
	private String imagen;	
	private String urlArticulo;
	
	public SitioTuristico() {}
	
	public SitioTuristico( String nombre, String resumen, String url) { 
		this.id = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.resumen = resumen;
		this.urlArticulo = url;
		this.categorias = new ArrayList<>();
		this.enlaces = new ArrayList<>();

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
		return new ArrayList<>(categorias);
	}
	
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
		
	}
	public List<String> getEnlaces() {
		return new ArrayList<>(enlaces);
	}

	public void setEnlaces(List<String> enlaces) {
		this.enlaces = enlaces;
		
	}

	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
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
				+ ", enlaces=" + enlaces + ", urlArticulo=" + urlArticulo + "]";
	}

}

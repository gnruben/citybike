package aadd.modelo;

import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbNillable;
import javax.json.bind.annotation.JsonbProperty;

import repositorio.Identificable;

@JsonbNillable
public class SitioTuristico implements Identificable{
	@JsonbProperty("id")
	private String id;
	@JsonbProperty("nombre")
	private String nombre;
	
	@JsonbProperty( nillable = true)
	private String resumen;
	
	@JsonbProperty( nillable = true)
	private List<String> categorias;
	
	@JsonbProperty( nillable = true)
	private List<String> enlaces;
	
	@JsonbProperty( nillable = true)
	private String imagen;
	
	@JsonbProperty("urlArticulo")
	private String urlArticulo;
	
	public SitioTuristico() {}
	
	public SitioTuristico( String nombre, String resumen, String url) { 
		
		this.nombre = nombre;
		this.resumen = resumen;
		this.urlArticulo = url;
		
		this.categorias = new ArrayList<String>();
		this.enlaces = new ArrayList<String>();

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

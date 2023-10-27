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
	private double latitud;
	private double longitud;
	private String urlArticulo;
	
	public SitioTuristico() {}
	
	public SitioTuristico( String nombre, String resumen, double latitud, double longitud, String url) {
		this.id = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.resumen = resumen;
		this.latitud = latitud;
		this.longitud = longitud;
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
	
//	public void setCategorias(List<String> categorias) {
//		this.categorias = categorias;
//	}
	public void addCategorias(List<String> categoriass) {
		for(String c: categoriass)
			categorias.add(c);
		
	}
	public List<String> getEnlaces() {
		return new ArrayList<>(enlaces);
	}
//	public void setEnlaces(List<String> enlaces) {
//		//this.enlaces.removeAll();
//		this.enlaces = enlaces;
//	}
	public void addEnlaces(List<String> enlacess) {
		for(String e: enlacess)
			enlaces.add(e);
		
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categorias == null) ? 0 : categorias.hashCode());
		result = prime * result + ((enlaces == null) ? 0 : enlaces.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitud);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitud);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((resumen == null) ? 0 : resumen.hashCode());
		result = prime * result + ((urlArticulo == null) ? 0 : urlArticulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SitioTuristico other = (SitioTuristico) obj;
		if (categorias == null) {
			if (other.categorias != null)
				return false;
		} else if (!categorias.equals(other.categorias))
			return false;
		if (enlaces == null) {
			if (other.enlaces != null)
				return false;
		} else if (!enlaces.equals(other.enlaces))
			return false;
		if (Double.doubleToLongBits(latitud) != Double.doubleToLongBits(other.latitud))
			return false;
		if (Double.doubleToLongBits(longitud) != Double.doubleToLongBits(other.longitud))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (resumen == null) {
			if (other.resumen != null)
				return false;
		} else if (!resumen.equals(other.resumen))
			return false;
		if (urlArticulo == null) {
			if (other.urlArticulo != null)
				return false;
		} else if (!urlArticulo.equals(other.urlArticulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SitioTuristico [id=" + id + ", nombre=" + nombre + ", resumen=" + resumen + ", categorias= " + getCategorias()
				+ ", enlaces= " + getEnlaces() + ", latitud=" + latitud + ", longitud=" + longitud + ", urlArticulo="
				+ urlArticulo + "]";
	}
	
}

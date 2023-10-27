package aadd.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import repositorio.Identificable;

public class SitioTuristico implements Identificable{
	
	private String id;
	private String nombre;
	private ResumenSitioTuristico resumen;
	private List<String> categorias;
	private List<String> enlaces;
	private String imagen;
	//private String urlArticulo;
	
	
	public SitioTuristico( String nombre, ResumenSitioTuristico resumen) { //, double latitud, double longitud, String url) {
		this.id = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.resumen = resumen;
		
		this.categorias = new ArrayList<>();
		this.enlaces = new ArrayList<>();
		//this.urlArticulo = url;

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
	public ResumenSitioTuristico getResumen() {
		return resumen;
	}
	public void setResumen(ResumenSitioTuristico resumen) {
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

//	public String getUrlArticulo() {
//		return urlArticulo;
//	}
//	public void setUrlArticulo(String urlArticulo) {
//		this.urlArticulo = urlArticulo;
//	}
	
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categorias == null) ? 0 : categorias.hashCode());
		result = prime * result + ((enlaces == null) ? 0 : enlaces.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((resumen == null) ? 0 : resumen.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagen == null) {
			if (other.imagen != null)
				return false;
		} else if (!imagen.equals(other.imagen))
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
		return true;
	}

	@Override
	public String toString() {
		return "SitioTuristico [id=" + id + ", nombre=" + nombre + ", resumen=" + resumen + ", categorias=" + categorias
				+ ", enlaces=" + enlaces + ", imagen=" + imagen + "]";
	}

	
	
}

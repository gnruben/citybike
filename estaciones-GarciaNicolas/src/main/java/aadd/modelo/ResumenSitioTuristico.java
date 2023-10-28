package aadd.modelo;

public class ResumenSitioTuristico {

	private String nombre;
	private String descripcion;
	private double latitud;
	private double longitud;
	private String urlArticulo;

	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

//
//	@Override
//	public String toString() {
//		return "ResumenSitioTuristico [nombre=" + nombre + ", descripcion=" + descripcion + ", latitud=" + latitud
//				+ ", longitud=" + longitud + ", urlArticulo=" + urlArticulo + "]";
//	}
	
	
}

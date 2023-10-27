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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		long temp;
		temp = Double.doubleToLongBits(latitud);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitud);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		ResumenSitioTuristico other = (ResumenSitioTuristico) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
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
		if (urlArticulo == null) {
			if (other.urlArticulo != null)
				return false;
		} else if (!urlArticulo.equals(other.urlArticulo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ResumenSitioTuristico [nombre=" + nombre + ", descripcion=" + descripcion + ", latitud=" + latitud
				+ ", longitud=" + longitud + ", urlArticulo=" + urlArticulo + "]";
	}
	
	
}

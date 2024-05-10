package aadd.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import repositorio.Identificable;

public class Estacion implements Identificable {
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private String nombre;
	private int numeroPuestos;
	private String direccionPostal;
	private double latitud;
	private double longitud;
	private Point ubicacion;
	private LocalDateTime fechaAlta;
	private List<SitioTuristico> sitiosTuristicos;

	public Estacion() {
	}

	public Estacion(String nombre, int numeroPuestos, String direccionPostal, double latitud, double longitud) {
		this.nombre = nombre;
		this.numeroPuestos = numeroPuestos;
		this.direccionPostal = direccionPostal;
		this.latitud = latitud;
		this.longitud = longitud;
		this.ubicacion = new Point(new Position(longitud, latitud));

		this.sitiosTuristicos = new ArrayList<SitioTuristico>();
	}

	public Point getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Point ubicacion) {
		this.ubicacion = ubicacion;
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
		s.append("id: ").append(id).append(", nombre:").append(nombre).append(", numero puesto:").append(numeroPuestos)
				.append(", fecha de alta: ").append(fechaAlta).append(", latitud: ").append(latitud)
				.append(", longitud: ").append(longitud).append(", sitiosTuristicos: [");

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

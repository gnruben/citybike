package aadd.modelo;

import java.time.LocalDate;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

import repositorio.Identificable;

public class RegistroHistoricoEstacionamiento implements Identificable{
	@BsonId
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	private String idBici;
	private String idEstacion;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}
	public String getIdBici() {
		return idBici;
	}
	public void setIdBici(String idBici) {
		this.idBici = idBici;
	}
	public String getIdEstacion() {
		return idEstacion;
	}
	public void setIdEstacion(String idEstacion) {
		this.idEstacion = idEstacion;
	}
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	

}

package aadd.repositorio;

import java.util.List;

import aadd.modelo.Estacion;
import repositorio.RepositorioString;

public interface IRepositorioEstacionesAdHoc extends RepositorioString<Estacion> {
	public List<Estacion> getEstacionesTuristicas();
	public List<Estacion> getEstacionesCercanasA(double lat,double lng);

}

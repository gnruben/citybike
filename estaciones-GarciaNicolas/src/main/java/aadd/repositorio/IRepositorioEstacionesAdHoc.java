package aadd.repositorio;

import java.util.List;

import aadd.modelo.Estacion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface IRepositorioEstacionesAdHoc extends RepositorioString<Estacion> {
	public List<Estacion> getEstacionesTuristicas() throws RepositorioException, EntidadNoEncontrada;
	public List<Estacion> getEstacionesCercanasA(double lat,double lng);

}

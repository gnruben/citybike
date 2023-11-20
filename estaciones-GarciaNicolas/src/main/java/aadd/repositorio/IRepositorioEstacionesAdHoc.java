package aadd.repositorio;

import java.util.List;

import aadd.modelo.Estacion;

public interface IRepositorioEstacionesAdHoc {
	public List<Estacion> getEstacionesTuristicas();
	public List<Estacion> getEstacionesCercanasA(double lat,double lng);

}

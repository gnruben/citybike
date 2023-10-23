package aadd.servicios;

import aadd.modelo.Estacion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioEstaciones {

	String crear(String nombre,int numeroPuestos,double lat,double lng) throws RepositorioException;
	Estacion getEstacion(String id)throws RepositorioException, EntidadNoEncontrada;
	void eliminar(String id) throws RepositorioException, EntidadNoEncontrada;
	
}

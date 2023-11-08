package aadd.servicios;

import java.util.List;

import aadd.modelo.Estacion;
import aadd.modelo.SitioTuristico;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioEstaciones {

	String crear(String nombre,int numeroPuestos,String postalcode,double lat,double lng) throws RepositorioException;
	Estacion getEstacion(String id)throws RepositorioException, EntidadNoEncontrada;
	void eliminar(String id) throws RepositorioException, EntidadNoEncontrada;
	List<SitioTuristico> getSitiosTuristicos(String id) throws RepositorioException, EntidadNoEncontrada;
	void setSitiosTuristicos(String id, List<SitioTuristico> sitios)throws RepositorioException, EntidadNoEncontrada;
	
	void altaBicicleta(String modelo, Estacion estacion);
	void estacionarBicicleta(String idBicicleta, String idEstacion);
	void retirarBicicleta(String idBicicleta);
	void darBajaBicicleta(String idBicicleta, String motivoBaja);
}

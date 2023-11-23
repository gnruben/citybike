package aadd.servicios;

import java.util.List;

import aadd.modelo.Bicicleta;
import aadd.modelo.Estacion;
import aadd.modelo.SitioTuristico;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioEstaciones {

	String crear(String nombre,int numeroPuestos,String postalcode,double lat,double lng) throws ServicioEstacionesException;
	Estacion getEstacion(String id) throws ServicioEstacionesException;
	void eliminar(String id) throws ServicioEstacionesException;
	List<SitioTuristico> getSitiosTuristicos(String id) throws ServicioEstacionesException;
	void setSitiosTuristicos(String id, List<SitioTuristico> sitios)throws RepositorioException, EntidadNoEncontrada;
	
//##################### Bicicletas ########################
	String altaBicicleta(String modelo, Estacion e) throws ServicioEstacionesException;
	void estacionarBicicleta(String idBicicleta) throws ServicioEstacionesException;
	void estacionarBicicleta(String idBicicleta, String idEstacion) throws ServicioEstacionesException;
	void retirarBicicleta(String idBicicleta) throws ServicioEstacionesException;
	void darBajaBicicleta(String idBicicleta, String motivoBaja) throws ServicioEstacionesException;

	List<Bicicleta> getBicisEstacionadasCerca(double lat,double lng);
	List<Estacion> getEstacionesTuristicas();


}

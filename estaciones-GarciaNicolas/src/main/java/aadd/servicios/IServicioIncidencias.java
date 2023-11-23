package aadd.servicios;

import java.util.List;

import aadd.modelo.Incidencia;
import aadd.modelo.Usuario;

public interface IServicioIncidencias {
	String crearIncidencia(String idbicicleta, String descripcion) throws ServicioIncidenciasException;
	//void gestionIncidencias(Usuario usuario, String motivoCierre, String operarioAsignado, boolean isReparada) throws ServicioIncidenciasException;
	List<Incidencia> getIncidenciasAbiertas();
	void cancelarIncidencia(Incidencia incidencia, String motivo) throws ServicioIncidenciasException;
	void asignarIncidencia(Incidencia incidencia, String idOperarioAsignado) throws ServicioIncidenciasException;
	void resolverIncidencia(Incidencia incidencia, String motivo, boolean isReparada) throws ServicioIncidenciasException;


}

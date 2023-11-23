package aadd.servicios;

import java.util.List;

import aadd.modelo.Incidencia;
import aadd.modelo.Usuario;

public interface IServicioIncidencias {
	String crearIncidencia(String idbicicleta, String descripcion) throws BicicletaNotFoundException;
	void gestionIncidencias(Usuario usuario, String motivoCierre, String operarioAsignado, boolean isReparada);
	List<Incidencia> getIncidenciasAbiertas();


}

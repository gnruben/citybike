package aadd.servicios;

import java.util.List;

import aadd.modelo.Incidencia;
import aadd.modelo.Usuario;

public interface IServicioIncidencias {
	void crearIncidencia(String idbicicleta, String descripcion);
	void gestionIncidencias(Usuario usuario, String motivoCierre, String operarioAsignado, boolean isReparada);
	List<Incidencia> getIncidenciasAbiertas();


}

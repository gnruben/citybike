package aadd.servicios;

import java.util.List;

import aadd.modelo.Incidencia;
import aadd.modelo.Usuario;

public interface IServicioIncidencias {
	void crearIncidencia(String idbicicleta, String descripcion);
	List<Incidencia> getIncidenciasAbiertas();
	void gestionIncidencias(Usuario usuario, String motivoCierre, String operarioAsignado, boolean isReparada);


}

package aadd.servicios;

import java.util.List;

import aadd.modelo.Incidencia;

public interface IServicioIncidencias {
	void crearIncidencia(String idbicicleta, String descripcion);
	void gestionIncidencias();
	List<Incidencia> getIncidenciasAbiertas();


}

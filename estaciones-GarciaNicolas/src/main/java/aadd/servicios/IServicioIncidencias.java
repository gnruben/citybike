package aadd.servicios;

import java.util.List;

import aadd.dto.BicicletaDTO;
import aadd.dto.IncidenciaDTO;
import aadd.modelo.Incidencia;

public interface IServicioIncidencias {
	
	String crearIncidencia(String idbicicleta, String descripcion) throws ServicioIncidenciasException;
	List<Incidencia> getIncidenciasAbiertas();
	void cancelarIncidencia(Incidencia incidencia, String motivo) throws ServicioIncidenciasException;
	void asignarIncidencia(Incidencia incidencia, String idOperarioAsignado) throws ServicioIncidenciasException;
	void resolverIncidencia(Incidencia incidencia, String motivo, boolean isReparada) throws ServicioIncidenciasException;
	IncidenciaDTO transformToDTO(Incidencia incidencia);
	List<IncidenciaDTO> incidenciasAbiertasLazy(int start, int max) throws ServicioIncidenciasException;
	int countIncidenciasAbiertas() throws ServicioIncidenciasException;
	Incidencia getIncidenciaByID(String idIncidencia);
}

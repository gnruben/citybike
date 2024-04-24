package aadd.servicios;

import java.util.List;

import aadd.dto.BicicletaDTO;
import aadd.dto.IncidenciaDTO;
import aadd.modelo.Incidencia;

public interface IServicioIncidencias {
	
	String crearIncidencia(String idbicicleta, String descripcion) throws ServicioIncidenciasException;
	List<Incidencia> getIncidenciasAbiertas();
	List<Incidencia> getIncidenciasResueltas();
	List<Incidencia> getIncidenciasAsignadas();
	List<Incidencia> getIncidenciasPendientes();
	List<Incidencia> getIncidenciasCanceladas();
	void cancelarIncidencia(Incidencia incidencia, String motivo) throws ServicioIncidenciasException;
	void asignarIncidencia(Incidencia incidencia, String idOperarioAsignado) throws ServicioIncidenciasException;
	void resolverIncidencia(Incidencia incidencia, String motivo, boolean isReparada) throws ServicioIncidenciasException;
	
	IncidenciaDTO transformToDTO(Incidencia incidencia);
	List<IncidenciaDTO> incidenciasAbiertasLazy(int start, int max) throws ServicioIncidenciasException;
	List<IncidenciaDTO> incidenciasResueltasLazy(int start, int max) throws ServicioIncidenciasException;
	List<IncidenciaDTO> incidenciasAsignadasLazy(int start, int max) throws ServicioIncidenciasException;
	List<IncidenciaDTO> incidenciasPendientesLazy(int start, int max) throws ServicioIncidenciasException;
	List<IncidenciaDTO> incidenciasCanceladasLazy(int start, int max) throws ServicioIncidenciasException;
	int countIncidenciasAbiertas() throws ServicioIncidenciasException;
	int countIncidenciasResueltas() throws ServicioIncidenciasException;
	int countIncidenciasAsignadas() throws ServicioIncidenciasException;
	int countIncidenciasPendientes() throws ServicioIncidenciasException;
	int countIncidenciasCanceladas() throws ServicioIncidenciasException;
	Incidencia getIncidenciaByID(String idIncidencia);
	
	void resolverIncidencia(String idIncidencia, String motivoCierre, boolean isReparada) throws ServicioIncidenciasException;
	void asignarIncidencia(String idIncidencia, String idOperarioAsignado) throws ServicioIncidenciasException;
	void cancelarIncidencia(String idIncidencia, String motivo) throws ServicioIncidenciasException;
}

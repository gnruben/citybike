package aadd.repositorio;

import java.util.List;

import aadd.modelo.Bicicleta;
import aadd.modelo.Incidencia;
import repositorio.RepositorioString;

public interface IRepositorioBicicletasAdHoc extends RepositorioString<Bicicleta>{

	public List<Bicicleta> obtenerBicicletasPorModelo(String modelo);
	public List<Incidencia> getIncidenciasAbiertas();
	public List<Incidencia> getIncidenciasPendientes();
	public List<Incidencia> getIncidenciasAsignadas();
	public List<Incidencia> getIncidenciasResueltas();
	public List<Incidencia> getIncidenciasCanceladas();
	Incidencia getIncidenciaById(String idIncidencia);
	
}

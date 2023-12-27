package aadd.repositorio;

import java.util.List;

import aadd.modelo.Bicicleta;
import aadd.modelo.Incidencia;
import repositorio.RepositorioString;

public interface IRepositorioBicicletasAdHoc extends RepositorioString<Bicicleta>{

	public List<Bicicleta> obtenerBicicletasPorModelo(String modelo);
	public List<Incidencia> getIncidenciasAbiertas();
	Incidencia getIncidenciaById(String idIncidencia);
	
}

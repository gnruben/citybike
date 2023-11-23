package aadd.repositorio;

import java.util.List;

import aadd.modelo.RegistroHistoricoEstacionamiento;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public interface IRepositorioHistorialEstacionamientoAdHoc extends RepositorioString<RegistroHistoricoEstacionamiento>{

	public List<RegistroHistoricoEstacionamiento> getHistorialByIdBici(String idBici);
	public List<String> getIdBicisByIdEstacion(String idEstacion);
	public int getNumeroBicisEnEstacion(String idEstacion );
	public RegistroHistoricoEstacionamiento getUltimoRegistroByIdBici(String idBici) throws RepositorioException;
}

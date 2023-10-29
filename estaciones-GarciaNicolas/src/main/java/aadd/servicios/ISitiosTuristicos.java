package aadd.servicios;

import java.util.List;

import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;
import repositorio.RepositorioException;

public interface ISitiosTuristicos {
	
	public List<ResumenSitioTuristico> getResumenesCercanos(double lat, double lon);
	public SitioTuristico getSitioTuristico(String id) throws RepositorioException;


}

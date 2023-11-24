package aadd.servicios;

import java.util.List;

import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;

public interface ISitiosTuristicos {
	
	public List<ResumenSitioTuristico> getResumenesCercanos(double lat, double lon) throws ServicioSitiosTuristicosException;
	public SitioTuristico getSitioTuristico(String id) throws ServicioSitiosTuristicosException;
}

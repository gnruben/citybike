package aadd.servicios;

import java.util.List;

import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;
import repositorio.RepositorioException;

public interface ISitiosTuristicos {
	
	public List<ResumenSitioTuristico> getResumenesCercanos(double lat, double lon,double radius);
	
	public SitioTuristico getSitioTuristico(String id) throws RepositorioException;
	//public List<SitioTuristico> getByPostalCode(String pcode,double radius);
	//public List<String> getURLWikiByPosicion(double lat, double lon);
	//public List<String> getURLWikiByPostalCode(String pcode);
	//public SitioTuristico getSitioTuristicoByWikiURL(String WikiUrl);
	
	

}

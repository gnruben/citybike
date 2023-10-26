package aadd.servicios;

import java.util.List;

import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;

public interface ISitiosTuristicos {
	
	public List<ResumenSitioTuristico> getResumenesCercanos(double lat, double lon,double radius);
	
	public SitioTuristico getSitioTuristico(String id);
	//public List<SitioTuristico> getByPostalCode(String pcode,double radius);
	//public List<String> getURLWikiByPosicion(double lat, double lon);
	//public List<String> getURLWikiByPostalCode(String pcode);
	//public SitioTuristico getSitioTuristicoByWikiURL(String WikiUrl);
	
	

}

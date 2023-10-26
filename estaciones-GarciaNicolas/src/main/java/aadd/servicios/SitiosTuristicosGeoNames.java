package aadd.servicios;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;

public class SitiosTuristicosGeoNames implements ISitiosTuristicos {

	private final static String raizUrlgeonames = "http://api.geonames.org/findNearbyWikipedia?username=ruben.garcia3&lang=es&country=ES";
	private DocumentBuilder analizador;
	private Repositorio<SitioTuristico,String> repositorio=FactoriaRepositorios.getRepositorio(SitioTuristico.class);
	
	public SitiosTuristicosGeoNames() throws ParserConfigurationException {
		DocumentBuilderFactory factoria =
				DocumentBuilderFactory.newInstance();
				// 2. Pedir a la factoría la construcción del analizador
				 analizador = factoria.newDocumentBuilder();
				// 3. Analizar el documento
				 
	}
	
	@Override
	public List<ResumenSitioTuristico> getResumenesCercanos(double lat, double lon,double radius) {
		// TODO Auto-generated method stub
		List<ResumenSitioTuristico> lista=new LinkedList<ResumenSitioTuristico>();
		try {
			Document documento = analizador.parse(raizUrlgeonames+"&lat="+lat+"&lng="+lon+"radius="+radius);
			//TODO: lista de resumenes 
			
			
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public SitioTuristico getSitioTuristico(String id) {
		//TODO: getSitioTuristico
		
		return null;
	}



	
}

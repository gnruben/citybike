package aadd.servicios;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import aadd.modelo.SitioTuristico;

public class SitiosTuristicosGeoNames implements ISitiosTuristicos {

	private final static String raizUrlgeonames = "http://api.geonames.org/findNearbyWikipedia?username=ruben.garcia3&lang=es&country=ES";
	private DocumentBuilder analizador;
	
	
	public SitiosTuristicosGeoNames() throws ParserConfigurationException {
		DocumentBuilderFactory factoria =
				DocumentBuilderFactory.newInstance();
				// 2. Pedir a la factoría la construcción del analizador
				 analizador = factoria.newDocumentBuilder();
				// 3. Analizar el documento
				 
	}
	
	@Override
	public List<SitioTuristico> getByPosicion(double lat, double lon,double radius) {
		// TODO Auto-generated method stub
		List<SitioTuristico> lista=new LinkedList<SitioTuristico>();
		try {
			Document documento = analizador.parse(raizUrlgeonames+"&lat="+lat+"&lng="+lon+"radius="+radius);
			
			
			
			
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
	public List<SitioTuristico> getByPostalCode(String pcode,double radius) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getURLWikiByPosicion(double lat, double lon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getURLWikiByPostalCode(String pcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SitioTuristico getSitioTuristicoByWikiURL(String WikiUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

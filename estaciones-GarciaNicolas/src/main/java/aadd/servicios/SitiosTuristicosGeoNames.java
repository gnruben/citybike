package aadd.servicios;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class SitiosTuristicosGeoNames implements ISitiosTuristicos {

	private final String raizUrlgeonames = "http://api.geonames.org/findNearbyWikipedia?username=ruben.garcia3&lang=es&country=ES";
	private final String raizUrlDBPedia="https://es.dbpedia.org/data/";
	
	private final String propiedadNombre = "http://xmlns.com/foaf/0.1/name";
	private final String propiedadResumen = "http://dbpedia.org/ontology/abstract";
	private final String propiedadEnlaces = "http://dbpedia.org/ontology/wikiPageExternalLink";
	private final String propiedadImagenes = "http://es.dbpedia.org/property/imagen";
	private final String propiedadCategorias = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
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
			ResumenSitioTuristico r;
			Element e;
			NodeList entries=documento.getElementsByTagName("entry");
			for(int i=0;i<entries.getLength();i++) {
				e=(Element)entries.item(i);
				r=new ResumenSitioTuristico();
				r.setDescripcion(
						e.getElementsByTagName("summary").item(0).getTextContent());
				r.setLatitud(Double.parseDouble(
						e.getElementsByTagName("lat").item(0).getTextContent()
						));
				r.setLongitud(Double.parseDouble(
						e.getElementsByTagName("lng").item(0).getTextContent()
						));
				r.setNombre(e.getElementsByTagName("title").item(0).getTextContent());
				r.setUrlArticulo(
						e.getElementsByTagName("wikipediaUrl").item(0).getTextContent());
				
				lista.add(r);
				
			}
			
			
			
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
	public SitioTuristico getSitioTuristico(String id) throws RepositorioException {
		//TODO: getSitioTuristico
		SitioTuristico sitio;
		try {
			sitio=repositorio.getById(id);
			return sitio;
		} catch (EntidadNoEncontrada e) {
			//Si no se encuentra en la caché
			
			//Obtener el sitio turístico de dbpedia
			sitio=getSitioTuristicoDBPedia(id);
			repositorio.add(sitio);
			return sitio;
			
		}
		
		
	}

	private SitioTuristico getSitioTuristicoDBPedia(String id) {
		SitioTuristico sitio=new SitioTuristico();
		//TODO:
		String urlString=raizUrlDBPedia+id+".json";
		
		
		return sitio;
		
	}


	
}

package aadd.servicios;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
	
	private final String propiedadNombre = "http://www.w3.org/2000/01/rdf-schema#label";
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
	public List<ResumenSitioTuristico> getResumenesCercanos(double lat, double lon) {
		// TODO Auto-generated method stub
		List<ResumenSitioTuristico> lista=new LinkedList<ResumenSitioTuristico>();
		try {
			String ruta = raizUrlgeonames+"&lat="+lat+"&lng="+lon;
			Document documento = analizador.parse(ruta);
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
			return lista;
			
			
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
		
		String urlString=raizUrlDBPedia+id+".json";
		try {
			URL url=new URL(urlString);
			InputStreamReader fuente=new InputStreamReader(url.openStream(),"UTF-8");
			
			JsonReader reader=Json.createReader(fuente);
			JsonObject obj=reader.readObject();
			String decoded=URLDecoder.decode(id,StandardCharsets.UTF_8.toString());
			
			JsonObject propiedades=obj.getJsonObject("http://es.dbpedia.org/resource/"+decoded);
			
			//TODO: construir el sitio turístico a partir de las funciones
			sitio.setNombre(getNombre(propiedades));
			sitio.setCategorias(getCategorias(propiedades));
			sitio.setEnlaces(getEnlacesExternos(propiedades));
			sitio.setResumen(getResumen(propiedades));
			sitio.setId(id);
			sitio.setImagen(getImagen(propiedades));
			sitio.setUrlArticulo("https://es.wikipedia.org/wiki/"+id);
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return sitio;
		
	}
	
	private String getNombre(JsonObject object) {
		//TODO:
		JsonArray nombre=object.getJsonArray(propiedadNombre);
		if(nombre==null) {
			return null;
		}
		String stringNombre="";
		for(JsonObject valor: nombre.getValuesAs(JsonObject.class)) {
			if(valor.containsKey("value")) {
				stringNombre=valor.getString("value");
			}
		}
		return stringNombre;
	}
	private String getResumen(JsonObject object) {
		
		JsonArray resumen=object.getJsonArray(propiedadResumen);
		String resumenString = "";
		

		if (resumen == null)
			return null;
		for (JsonObject valor : resumen.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				resumenString = valor.getString("value");
		}

		return resumenString;
	}
	private List<String> getCategorias(JsonObject object){
		List<String> categorias=new LinkedList<String>();
		JsonArray cat = object.getJsonArray(propiedadCategorias);

		if (cat == null)
			return null;
		for (JsonObject valor : cat.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				categorias.add(valor.getString("value"));
		}

		return categorias;
	}
	
	private List<String> getEnlacesExternos(JsonObject object){
		List<String> enlaces=new LinkedList<String>();
		JsonArray links = object.getJsonArray(propiedadEnlaces);

		if (links == null)
			return null;
		for (JsonObject valor : links.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				enlaces.add(valor.getString("value"));
		}

		return enlaces;
	}
	private String getImagen(JsonObject object) {
		JsonArray img = object.getJsonArray(propiedadImagenes);

		if (img == null)
			return null;
		for (JsonObject valor : img.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				return valor.getString("value");
		}

		return null;

	}


	
}

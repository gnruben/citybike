package aadd.servicios;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

	private static final String raizUrlgeonames = "http://api.geonames.org/findNearbyWikipedia?username=ruben.garcia3&lang=es&country=ES";
	private static final String raizUrlDBPedia = "https://es.dbpedia.org/data/";

	private static final String propiedadNombre = "http://www.w3.org/2000/01/rdf-schema#label";
	private static final String propiedadResumen = "http://dbpedia.org/ontology/abstract";
	private static final String propiedadEnlaces = "http://dbpedia.org/ontology/wikiPageExternalLink";
	private static final String propiedadImagenes = "http://es.dbpedia.org/property/imagen";
	private static final String propiedadCategorias = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	
	private DocumentBuilder analizador;
	private Repositorio<SitioTuristico, String> repositorio = FactoriaRepositorios.getRepositorio(SitioTuristico.class);

	public SitiosTuristicosGeoNames() throws ParserConfigurationException {
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		analizador = factoria.newDocumentBuilder();
	}

	@Override
	public List<ResumenSitioTuristico> getResumenesCercanos(double lat, double lon) throws ServicioSitiosTuristicosException {
		List<ResumenSitioTuristico> lista = new LinkedList<ResumenSitioTuristico>();
		try {
			String ruta = raizUrlgeonames + "&lat=" + lat + "&lng=" + lon;
			Document documento = analizador.parse(ruta);
			ResumenSitioTuristico r;
			Element e;
			NodeList entries = documento.getElementsByTagName("entry");
			for (int i = 0; i < entries.getLength(); i++) {
				e = (Element) entries.item(i);
				r = new ResumenSitioTuristico();
				r.setDescripcion(e.getElementsByTagName("summary").item(0).getTextContent());
				r.setLatitud(Double.parseDouble(e.getElementsByTagName("lat").item(0).getTextContent()));
				r.setLongitud(Double.parseDouble(e.getElementsByTagName("lng").item(0).getTextContent()));
				r.setNombre(e.getElementsByTagName("title").item(0).getTextContent());
				r.setUrlArticulo(e.getElementsByTagName("wikipediaUrl").item(0).getTextContent());

				lista.add(r);

			}
			return lista;

		} catch (SAXException e) {
			
			throw new ServicioSitiosTuristicosException("Error al parsear el XML");
		} catch (IOException e) {
			throw new ServicioSitiosTuristicosException("Error de entrada salida");
		}
		
	}

	@Override
	public SitioTuristico getSitioTuristico(String id) throws ServicioSitiosTuristicosException  {
		SitioTuristico sitio;
		try {
			sitio = repositorio.getById(id);
			return sitio;
		} catch (EntidadNoEncontrada e) {
			try {
				sitio = getSitioTuristicoDBPedia(id);
				repositorio.add(sitio);
			} catch (UnsupportedEncodingException e1) {

				throw new ServicioSitiosTuristicosException("Error al decodificar");
			} catch (IOException e1) {
				throw new ServicioSitiosTuristicosException("Error de entrada salida");
			} catch (RepositorioException e1) {
				throw new ServicioSitiosTuristicosException("Error en el repositorio al añadir el sitio turístico");
			}
			
			return sitio;

		} catch (RepositorioException e) {
			throw new ServicioSitiosTuristicosException("Error en el repositorio al obtener el sitio");
		}

	}

	private SitioTuristico getSitioTuristicoDBPedia(String id) throws UnsupportedEncodingException, IOException {
		SitioTuristico sitio = new SitioTuristico();

		String urlString = raizUrlDBPedia + id + ".json";

		URL url = new URL(urlString);
		InputStreamReader fuente = new InputStreamReader(url.openStream(), "UTF-8");

		JsonReader reader = Json.createReader(fuente);
		JsonObject obj = reader.readObject();
		String decoded = URLDecoder.decode(id, StandardCharsets.UTF_8.toString());

		JsonObject propiedades = obj.getJsonObject("http://es.dbpedia.org/resource/" + decoded);

		sitio.setNombre(getNombre(propiedades));
		sitio.setCategorias(getCategorias(propiedades));
		sitio.setEnlaces(getEnlacesExternos(propiedades));
		sitio.setResumen(getResumen(propiedades));
		sitio.setId(id);
		sitio.setImagen(getImagen(propiedades));
		sitio.setUrlArticulo("https://es.wikipedia.org/wiki/" + id);

		return sitio;
	}

	private String getNombre(JsonObject object) {
		
		JsonArray nombre = object.getJsonArray(propiedadNombre);
		if (nombre == null) {
			return null;
		}
		String stringNombre = "";
		for (JsonObject valor : nombre.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value")) {
				stringNombre = valor.getString("value");
			}
		}
		return stringNombre;
	}

	private String getResumen(JsonObject object) {

		JsonArray resumen = object.getJsonArray(propiedadResumen);
		String resumenString = "";

		if (resumen == null)
			return null;
		for (JsonObject valor : resumen.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				resumenString = valor.getString("value");
		}

		return resumenString;
	}

	private List<String> getCategorias(JsonObject object) {
		List<String> categorias = new LinkedList<String>();
		JsonArray cat = object.getJsonArray(propiedadCategorias);

		if (cat == null)
			return null;
		for (JsonObject valor : cat.getValuesAs(JsonObject.class)) {
			if (valor.containsKey("value"))
				categorias.add(valor.getString("value"));
		}

		return categorias;
	}

	private List<String> getEnlacesExternos(JsonObject object) {
		List<String> enlaces = new LinkedList<String>();
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


package aadd.repositorio;

import aadd.modelo.SitioTuristico;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;


public class RepositorioSitiosTuristicosJSON implements Repositorio<SitioTuristico, String> {
    private List<SitioTuristico> sitios; // = new ArrayList<>();
    private String rutaArchivo;

    public RepositorioSitiosTuristicosJSON() {
    	this.rutaArchivo = "sitiosturisticos.json";
    	this.sitios = new ArrayList<>();
    	cargarDesdeJSON();
       
    }

    @Override
    public String add(SitioTuristico entity) throws RepositorioException {
        sitios.add(entity);
        guardarEnJSON();
        return entity.getId();
    }

    @Override
    public void update(SitioTuristico entity) throws RepositorioException, EntidadNoEncontrada {
        for (int i = 0; i < sitios.size(); i++) {
            SitioTuristico sitio = sitios.get(i);
            if (sitio.getId().equals(entity.getId())) {
                sitios.set(i, entity);
                guardarEnJSON();
                return;
            }
        }
        throw new EntidadNoEncontrada("Sitio turístico no encontrado: " + entity.getNombre());
    }

    @Override
	public void delete(SitioTuristico entity) throws RepositorioException, EntidadNoEncontrada {
		 if (!sitios.contains(entity)) {
	            throw new EntidadNoEncontrada("Sitio turístico no encontrado en el repositorio.");
	        }
	        sitios.remove(entity);
	        guardarEnJSON();
		
	}

	@Override
	public SitioTuristico getById(String id) throws RepositorioException, EntidadNoEncontrada {
		for(SitioTuristico s : sitios) {
			if (s.getId().equals(id))
				return s;
		}
		
            throw new EntidadNoEncontrada("Sitio turístico no encontrado en el repositorio.");
        
        
	}

	@Override
	public List<SitioTuristico> getAll() throws RepositorioException {
		return new ArrayList<>(sitios);
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		List<String> ids = new ArrayList<>();
		
		for (SitioTuristico s: sitios) {
			ids.add(s.getId());
		}
		return ids;
	}
	
    // Otros métodos de la interfaz Repositorio

    private void cargarDesdeJSON() {
        try (JsonReader reader = Json.createReader(new FileReader(rutaArchivo))) {
            JsonArray jsonArray = reader.readArray();
         
            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                SitioTuristico sitio = deserializarSitioTuristico(jsonObject);
                sitios.add(sitio);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarEnJSON() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (SitioTuristico sitio : sitios) {
            JsonObject jsonObject = serializarSitioTuristico(sitio);
            arrayBuilder.add(jsonObject);
        }

        try (JsonWriter writer = Json.createWriter(new FileWriter(rutaArchivo))) {
            writer.writeArray(arrayBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject serializarSitioTuristico(SitioTuristico sitio) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
        		.add("id", sitio.getId())
                .add("nombre", sitio.getNombre())
                .add("resumen", sitio.getResumen())
                //TODO:adaptar a la nueva versión de SitioTurístico
                *
                .add("urlArticulo", sitio.getUrlArticulo());

        JsonArrayBuilder categoriasBuilder = Json.createArrayBuilder();
        if (sitio.getCategorias() != null) {
	        for (String categoria : sitio.getCategorias()) {
	            categoriasBuilder.add(categoria);
	        }
        }
        builder.add("categorias", categoriasBuilder);

        JsonArrayBuilder enlacesBuilder = Json.createArrayBuilder();
        if (sitio.getCategorias() != null) {
	        for (String enlace : sitio.getEnlaces()) {
	            enlacesBuilder.add(enlace);
	        }
        }
        builder.add("enlaces", enlacesBuilder);

        return builder.build();
    }

    private SitioTuristico deserializarSitioTuristico(JsonObject jsonObject) {
    	String id = jsonObject.getString("id");
    	String nombre = jsonObject.getString("nombre");
        String descripcion = jsonObject.getString("resumen");
        double latitud = jsonObject.getJsonNumber("latitud").doubleValue();
        double longitud = jsonObject.getJsonNumber("longitud").doubleValue();
        String urlWikipedia = jsonObject.getString("urlArticulo");

        JsonArray categoriasArray = jsonObject.getJsonArray("categorias");
        List<String> categorias = new ArrayList<>();
        if (categoriasArray != null) {
	        for (JsonValue c : categoriasArray) {
	           categorias.add(c.toString());
	        }
        }

        JsonArray enlacesArray = jsonObject.getJsonArray("enlaces");
        List<String> enlaces = new ArrayList<>();
        if (enlacesArray != null) {
	        for (JsonValue enlaceValue : enlacesArray) {
	          enlaces.add(enlaceValue.toString());
	        }
        }

        SitioTuristico sitio = new SitioTuristico(nombre, descripcion, latitud, longitud, urlWikipedia);
        sitio.setId(id);
        sitio.setCategorias(categorias);
        sitio.setEnlaces(enlaces);

        return sitio;
    }


	
}

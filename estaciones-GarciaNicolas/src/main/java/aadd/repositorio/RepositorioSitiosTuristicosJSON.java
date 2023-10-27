
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


public class RepositorioSitiosTuristicosJSON extends RepositorioJSON<SitioTuristico> implements Repositorio<SitioTuristico, String> {
    private List<SitioTuristico> sitios; // = new ArrayList<>();
    private final static String rutaArchivo = "./sitiosturisticos.json";

    public RepositorioSitiosTuristicosJSON() {
    	super(rutaArchivo);
    	//this.rutaArchivo = ;
    	this.sitios = new ArrayList<>();
    	cargarDesdeJSON();
       
    }

    @Override
    public String add(SitioTuristico entity) throws RepositorioException {
        for(SitioTuristico s: sitios) {
        	if(s.equals(entity))
        		return s.getId();
        }
    	
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
                SitioTuristico sitio = deserializar(jsonObject);
                sitios.add(sitio);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarEnJSON() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (SitioTuristico sitio : sitios) {
            JsonObject jsonObject = serializar(sitio);
            arrayBuilder.add(jsonObject);
        }

        try (JsonWriter writer = Json.createWriter(new FileWriter(rutaArchivo))) {
            writer.writeArray(arrayBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected JsonObject serializar(SitioTuristico sitio) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
        		.add("id", sitio.getId())
                .add("nombre", sitio.getNombre())
                .add("resumen", sitio.getResumen())
                .add("latitud", sitio.getLatitud())
                .add("longitud", sitio.getLongitud())
                .add("urlArticulo", sitio.getUrlArticulo());

        JsonArrayBuilder categoriasBuilder = Json.createArrayBuilder(sitio.getCategorias());//Json.createArrayBuilder();
//        if (sitio.getCategorias() != null) {
//	        for (String categoria : sitio.getCategorias()) {
//	            categoriasBuilder.add(categoria);
//	        }
//        }
        builder.add("categorias", categoriasBuilder);

        JsonArrayBuilder enlacesBuilder = Json.createArrayBuilder(sitio.getEnlaces()); //Json.createArrayBuilder();
//        if (sitio.getCategorias() != null) {
//	        for (String enlace : sitio.getEnlaces()) {
//	            enlacesBuilder.add(enlace);
//	        }
//        }
        builder.add("enlaces", enlacesBuilder);

        return builder.build();
    }


    protected SitioTuristico deserializar(JsonObject jsonObject) {
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
        sitio.addCategorias(categorias);
        sitio.addEnlaces(enlaces);

        return sitio;
    }

}

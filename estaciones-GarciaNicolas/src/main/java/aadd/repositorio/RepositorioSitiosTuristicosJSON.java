package aadd.repositorio;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;

import aadd.modelo.SitioTuristico;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class RepositorioSitiosTuristicosJSON implements Repositorio<SitioTuristico, String>{

	private List<SitioTuristico> sitiosTuristicos;
    private String rutaArchivo;
	
	public RepositorioSitiosTuristicosJSON() {
		 this.rutaArchivo = "sitiosTuristicos.json";
	     cargarDesdeJSON();
	}
	
	@Override
    public String add(SitioTuristico entity) throws RepositorioException {
        sitiosTuristicos.add(entity);
        guardarEnJSON();
        return entity.getNombre();
    }

	@Override
    public void update(SitioTuristico entity) throws RepositorioException, EntidadNoEncontrada {
        for (int i = 0; i < sitiosTuristicos.size(); i++) {
            SitioTuristico sitio = sitiosTuristicos.get(i);
            if (sitio.getNombre().equals(entity.getNombre())) {
                sitiosTuristicos.set(i, entity);
                guardarEnJSON();
                return;
            }
        }
        throw new EntidadNoEncontrada("Sitio turístico no encontrado: " + entity.getNombre());
    }
	
	@Override
    public void delete(SitioTuristico entity) throws RepositorioException, EntidadNoEncontrada {
        if (!sitiosTuristicos.remove(entity)) {
            throw new EntidadNoEncontrada("Sitio turístico no encontrado: " + entity.getNombre());
        }
        guardarEnJSON();
    }
	
    @Override
    public SitioTuristico getById(String id) throws RepositorioException, EntidadNoEncontrada {
        for (SitioTuristico sitio : sitiosTuristicos) {
            if (sitio.getNombre().equals(id)) {
                return sitio;
            }
        }
        throw new EntidadNoEncontrada("El sitio turístico no se encontró en el repositorio.");
    }


    @Override
    public List<SitioTuristico> getAll() throws RepositorioException {
        return sitiosTuristicos;
    }

    @Override
    public List<String> getIds() throws RepositorioException {
        List<String> ids = new ArrayList<>();
        for (SitioTuristico sitio : sitiosTuristicos) {
            ids.add(sitio.getNombre());
        }
        return ids;
    }

    private void cargarDesdeJSON() {
        try (JsonReader reader = Json.createReader(new FileReader(rutaArchivo))) {
            JsonArray jsonArray = reader.readArray();
            sitiosTuristicos = new ArrayList<>();

            for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
                SitioTuristico sitio = deserializarSitioTuristico(jsonObject);
                sitiosTuristicos.add(sitio);
            }
        } catch (IOException e) {
            sitiosTuristicos = new ArrayList<>();
        }
    }

    private void guardarEnJSON() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (SitioTuristico sitio : sitiosTuristicos) {
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
                .add("nombre", sitio.getNombre())
                .add("descripcion", sitio.getResumen())
                .add("longitud", sitio.getLongitud())
                .add("latitud", sitio.getLatitud())
                .add("urlWikipedia", sitio.getUrlArticulo());

        return builder.build();
    }

    private SitioTuristico deserializarSitioTuristico(JsonObject jsonObject) {
        String nombre = jsonObject.getString("nombre");
        String descripcion = jsonObject.getString("descripcion");
        double longitud = jsonObject.getJsonNumber("longitud").doubleValue();
        double latitud = jsonObject.getJsonNumber("latitud").doubleValue();
        String urlWikipedia = jsonObject.getString("urlWikipedia");

        return new SitioTuristico(nombre, descripcion, longitud, latitud, urlWikipedia);
    }

}

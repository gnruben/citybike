package aadd.repositorio;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;

import repositorio.Identificable;

public abstract class RepositorioJSON<T extends Identificable> {
    private List<T> elementos;
    private String rutaArchivo;

    public RepositorioJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.elementos = new ArrayList<>();
        cargarDesdeJSON();
    }

    
    public void agregar(T elemento) {
        elementos.add(elemento);
        guardarEnJSON();
    }

    public T obtenerPorId(String id) {
    	for (T elemento : elementos)
    		if (elemento.getId().equals(id))
    			return elemento;
    	
    	return null;
    }

    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }

    
    private void cargarDesdeJSON() {
        try (JsonReader reader = Json.createReader(new FileReader(rutaArchivo))) {
            JsonArray jsonArray = reader.readArray();
            for (JsonValue jsonValue : jsonArray) {                
                JsonObject jsonObject = (JsonObject) jsonValue;
                T elemento = deserializar(jsonObject);
                elementos.add(elemento);
            }
        } catch (IOException e) {
            elementos = new ArrayList<>();
        }
    }



	private void guardarEnJSON() {

    	JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (T elemento : elementos) {
            // Implementa la lógica para serializar el elemento en JsonObject
            JsonObject jsonObject = serializar(elemento);
            arrayBuilder.add(jsonObject);
        }

        JsonArray jsonArray = arrayBuilder.build();
     
        try (JsonWriter writer = Json.createWriter(new FileWriter(rutaArchivo))) {
            writer.writeArray(jsonArray);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	// Métodos abstractos para serializar y deserializar elementos
    protected abstract JsonObject serializar(T elemento);
    protected abstract T deserializar(JsonObject jsonObject);

	
}

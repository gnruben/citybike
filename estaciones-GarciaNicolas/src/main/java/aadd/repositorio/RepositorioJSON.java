package aadd.repositorio;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;

public class RepositorioJSON<T> {
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
    	//TODO
    	return null;
    }

    public List<T> obtenerTodos() {
        return elementos;
    }

    public void cargarDesdeJSON() {
        try (JsonReader reader = Json.createReader(new FileReader(rutaArchivo))) {
            JsonArray jsonArray = reader.readArray();
            // Implementa la lógica para convertir el JsonArray en elementos de tipo T.
        } catch (IOException e) {
            elementos = new ArrayList<>();
        }
    }

    public void guardarEnJSON() {
        try (JsonWriter writer = Json.createWriter(new FileWriter(rutaArchivo))) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            // Implementa la lógica para agregar los elementos a arrayBuilder en formato JsonObject.
            writer.writeArray(arrayBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

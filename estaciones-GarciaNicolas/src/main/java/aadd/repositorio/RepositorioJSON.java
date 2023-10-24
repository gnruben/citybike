package aadd.repositorio;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositorioJSON<T> {
    private List<T> elementos;
    private String rutaArchivo;

    public RepositorioJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        cargarDesdeJSON();
    }

    public void agregar(T elemento) {
        elementos.add(elemento);
        guardarEnJSON();
    }

    public T obtenerPorId(String id) {
        // Implementa la lógica para obtener un elemento por su identificador.
        return null;
    }

    public List<T> obtenerTodos() {
        return elementos;
    }

    public void cargarDesdeJSON() {
        try (JsonReader reader = Json.createReader(new FileReader(rutaArchivo))) {
            JsonArray jsonArray = reader.readArray();
            // Aquí debes implementar la lógica para convertir el JsonArray en elementos de tipo T.
        } catch (IOException e) {
            elementos = new ArrayList<>();
        }
    }

    public void guardarEnJSON() {
        try (JsonWriter writer = Json.createWriter(new FileWriter(rutaArchivo))) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            // Aquí debes agregar los elementos a arrayBuilder en formato JsonObject.
            writer.writeArray(arrayBuilder.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

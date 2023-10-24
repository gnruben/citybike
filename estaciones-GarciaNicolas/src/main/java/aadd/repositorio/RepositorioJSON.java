package aadd.repositorio;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioJSON<T> {

    private final String rutaArchivo;
    private final Jsonb jsonb;

    public RepositorioJSON(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
        this.jsonb = JsonbBuilder.create();
    }

    public String add(T entity) throws RepositorioException {
        try {
            List<T> entidades  = cargarDesdeJSON();
            entidades.add(entity);
            guardarAJSON(entidades);
            return null; //entity.;   //TODO
        } catch (IOException e) {
            throw new RepositorioException("Error al agregar entidad al repositorio JSON.", e);
        }
    }

    public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
        try {
            List<T> entidades = cargarDesdeJSON();
            boolean actualizado = false;
            for (int i = 0; i < entidades.size(); i++) {
                if (entidades.get(i).equals(entity)) {
                    entidades.set(i, entity);
                    actualizado = true;
                    break;
                }
            }
            if (actualizado) {
                guardarAJSON(entidades);
            } else {
                throw new EntidadNoEncontrada("La entidad no existe en el repositorio JSON.");
            }
        } catch (IOException e) {
            throw new RepositorioException("Error al actualizar entidad en el repositorio JSON.", e);
        }
    }

    public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
        try {
            List<T> entidades = cargarDesdeJSON();
            if (entidades.removeIf(e -> e.equals(entity))) {
                guardarAJSON(entidades);
            } else {
                throw new EntidadNoEncontrada("La entidad no existe en el repositorio JSON.");
            }
        } catch (IOException e) {
            throw new RepositorioException("Error al eliminar entidad del repositorio JSON.", e);
        }
    }

    public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
        try {
            List<T> entidades = cargarDesdeJSON();
            for (T entity : entidades) {
                if (entity.equals(id)) {  //TODO
                    return entity;
                }
            }
            throw new EntidadNoEncontrada("No se ha encontrado la entidad con el identificador especificado.");
        } catch (IOException e) {
            throw new RepositorioException("Error al obtener entidad por ID desde el repositorio JSON.", e);
        }
    }

    public List<T> getAll() throws RepositorioException {
        try {
            return cargarDesdeJSON();
        } catch (IOException e) {
            throw new RepositorioException("Error al obtener todas las entidades desde el repositorio JSON.", e);
        }
    }

    private List<T> cargarDesdeJSON() throws IOException {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(archivo)) {
            T[] entidades = jsonb.fromJson(reader, new ArrayList<T>().getClass().getGenericSuperclass());
            return new ArrayList<>(List.of(entidades));
        } catch (JsonbException e) {
            throw new IOException(e);
        }
    }

    private void guardarAJSON(List<T> entity) throws IOException {
        try (Writer writer = new FileWriter(rutaArchivo)) {
            jsonb.toJson(entity.toArray(), writer);
        } catch (JsonbException e) {
            throw new IOException(e);
        }
    }

 
}

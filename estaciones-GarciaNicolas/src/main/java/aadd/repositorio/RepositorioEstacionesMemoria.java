package aadd.repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import aadd.modelo.Estacion;
import repositorio.RepositorioMemoria;
/*
 * Repositorio preparado con datos de prueba
 */
public class RepositorioEstacionesMemoria extends RepositorioMemoria<Estacion> {

	private Map<String, Estacion> estaciones;

    public RepositorioEstacionesMemoria() {
        estaciones = new HashMap<>();
        // Datos iniciales para pruebas
        Estacion estacion = new Estacion();
        String id = add(estacion);
    }

    public String add(Estacion estacion) {
        String id = UUID.randomUUID().toString();
        estacion.setId(id);
        estaciones.put(id, estacion);
        return id;
    }

    public Estacion getEstacion(String id) {
        return estaciones.get(id);
    }

    public List<Estacion> getEstaciones() {
        return new ArrayList<>(estaciones.values());
    }
}

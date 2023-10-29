package aadd.repositorio;

import java.time.LocalDateTime;
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
    }

}

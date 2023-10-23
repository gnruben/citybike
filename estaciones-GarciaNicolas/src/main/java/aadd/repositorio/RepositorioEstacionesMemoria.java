package aadd.repositorio;

import aadd.modelo.Estacion;
import repositorio.RepositorioMemoria;
/*
 * Repositorio preparado con datos de prueba
 */
public class RepositorioEstacionesMemoria extends RepositorioMemoria<Estacion> {

	public RepositorioEstacionesMemoria() {

		// Datos iniciales para pruebas

		Estacion estacion = new Estacion();

		this.add(estacion);

	}
}

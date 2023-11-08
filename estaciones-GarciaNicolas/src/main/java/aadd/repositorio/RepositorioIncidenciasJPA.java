package aadd.repositorio;

import aadd.modelo.Incidencia;
import repositorio.RepositorioJPA;

public class RepositorioIncidenciasJPA extends RepositorioJPA<Incidencia>{

	@Override
	public Class<Incidencia> getClase() {
		// TODO Auto-generated method stub
		return Incidencia.class;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return Incidencia.class.getName();
	}

}

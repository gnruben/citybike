package aadd.repositorio;

import java.util.ArrayList;
import java.util.List;

import aadd.modelo.EstadoIncidencia;
import aadd.modelo.Incidencia;
import repositorio.RepositorioException;
import repositorio.RepositorioJPA;

public class RepositorioIncidenciasJPA extends RepositorioJPA<Incidencia>{

	@Override
	public Class<Incidencia> getClase() {
		return Incidencia.class;
	}

	@Override
	public String getNombre() {
		return Incidencia.class.getName();
	}
	
	public List<Incidencia> getIncidenciasAbiertas(){
		List<Incidencia> incidencias= new ArrayList<Incidencia>();
		
		try {
			List<Incidencia> todasIncidencias = getAll();
			
			for(Incidencia i: todasIncidencias) {
				if(i.getEstado()== EstadoIncidencia.PENDIENTE)
					incidencias.add(i);
			}
			
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		
		return incidencias;
	}

}

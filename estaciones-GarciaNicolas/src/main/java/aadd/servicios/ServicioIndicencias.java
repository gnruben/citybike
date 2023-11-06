package aadd.servicios;

import java.util.List;

import aadd.modelo.Bicicleta;
import aadd.modelo.EstadoBicicleta;
import aadd.modelo.EstadoIncidencia;
import aadd.modelo.Incidencia;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;


public class ServicioIndicencias implements IServicioIncidencias {
	
	private Repositorio<Bicicleta, String> repositorio = FactoriaRepositorios.getRepositorio(Bicicleta.class);

	@Override
	public void crearIncidencia(String idbicicleta, String descripcion) {
		try {
			if (repositorio.getIds().contains(idbicicleta)) {
				Incidencia incidencia = new Incidencia();
				incidencia.setEstado(EstadoIncidencia.PENDIENTE);
				Bicicleta bici = this.repositorio.getById(idbicicleta);
				bici.setEstado(EstadoBicicleta.NODISPONIBLE);
				
			}
		} catch (RepositorioException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void gestionIncidencias() {
//		for (Incidencia i : serviciosIncidencias.get) {
//			switch (i.getEstado()) {
//				case CANCE
//			}
//		}
		// TODO dónde están las incidencias? en bicicleta?
		
	}

	@Override
	public List<Incidencia> getIncidenciasAbiertas() {
		// TODO  cómo obtengo las incidencias??
		return null;
	}

}

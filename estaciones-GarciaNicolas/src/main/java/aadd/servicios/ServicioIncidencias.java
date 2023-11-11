package aadd.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import aadd.modelo.Bicicleta;
import aadd.modelo.EstadoIncidencia;
import aadd.modelo.Incidencia;
import aadd.modelo.Usuario;
import aadd.repositorio.RepositorioBicicletasJPA;
import aadd.repositorio.RepositorioIncidenciasJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioIncidencias implements IServicioIncidencias {
	
	Repositorio<Bicicleta, String> repositorioBicicleta = FactoriaRepositorios.getRepositorio(Bicicleta.class); 	
	private IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	private RepositorioIncidenciasJPA repositorioIncidencia; //TODO private Repositorio<Incidencia, String> repositorioIncidencia = FactoriaRepositorios.getRepositorio(Incidencia.class); 
	
	@Override
	public void crearIncidencia(String idbicicleta, String descripcion) {
		Bicicleta bicicleta;
		try {
			bicicleta = repositorioBicicleta.getById(idbicicleta);
			if(bicicleta != null){
				Incidencia incidencia = new Incidencia();
				incidencia.setBicicleta(bicicleta);
				incidencia.setDescripcion(descripcion);
				incidencia.setFechaInicio(LocalDate.now());
				incidencia.setEstado(EstadoIncidencia.PENDIENTE);
				bicicleta.setDisponible(false);
				
				repositorioIncidencia.add(incidencia); 
			}
		
		} catch (EntidadNoEncontrada | RepositorioException e ) {
			e.printStackTrace();
		} 
	}
	
    private void cancelarIncidencia(Incidencia incidencia, String motivoCierre) {
        incidencia.setEstado(EstadoIncidencia.CANCELADA);
        incidencia.setFechaFin(LocalDate.now());
        incidencia.getBicicleta().setDisponible(true);
        incidencia.getBicicleta().setMotivo(motivoCierre);
    }

    private void asignarIncidencia(Incidencia incidencia, String operarioAsignado) {
    	incidencia.setEstado(EstadoIncidencia.ASIGNADA);
		incidencia.setOperarioAsignado(operarioAsignado);
		incidencia.getBicicleta().setDisponible(false);
		servicioEstaciones.retirarBicicleta(incidencia.getBicicleta().getId()); 
	
     }

    private void resolverIncidencia(Incidencia incidencia, String motivoCierre, boolean isReparada) {
    	incidencia.setEstado(EstadoIncidencia.RESUELTA);
		incidencia.getBicicleta().setMotivo(motivoCierre);
		incidencia.setFechaFin(LocalDate.now());
		if (isReparada) {
			servicioEstaciones.estacionarBicicleta(incidencia.getBicicleta().getId(), null); 
			incidencia.getBicicleta().setDisponible(true);								
		}else 
			servicioEstaciones.darBajaBicicleta(incidencia.getBicicleta().getId(), motivoCierre);
    }
	
	@Override
	public void gestionIncidencias(Usuario usuario, String motivoCierre, String operarioAsignado, boolean isReparada) {
		
			try {
				if (usuario.esAdministrador()) {  
					for (Incidencia i : repositorioIncidencia.getAll()) {
						
						if(i.getEstado()== EstadoIncidencia.PENDIENTE) {

								if(motivoCierre!=null) 
									cancelarIncidencia(i, motivoCierre);
								else 
									asignarIncidencia(i, operarioAsignado);

						}
						else if (i.getEstado()== EstadoIncidencia.ASIGNADA)
							resolverIncidencia(i, motivoCierre, isReparada);
					}
				}
			} catch (RepositorioException e) {
					e.printStackTrace();
			}
	}

	@Override
	public List<Incidencia> getIncidenciasAbiertas() {
		return repositorioIncidencia.getIncidenciasAbiertas();
	}
	
}

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
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioIncidencias implements IServicioIncidencias {
	
	private RepositorioBicicletasJPA repositorioBicicleta;   //TODO private Repositorio<Bicicleta, String> repositorioBicicleta = FactoriaRepositorios.getRepositorio(Bicicleta.class); 	
	private RepositorioIncidenciasJPA repositorioIncidencia; //TODO private Repositorio<Incidencia, String> repositorioIncidencia = FactoriaRepositorios.getRepositorio(Incidencia.class); 
	private IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
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
				
				repositorioIncidencia.add(incidencia); //Guardar la incidencia en la BD
			}
		
		} catch (EntidadNoEncontrada | RepositorioException e ) {
			e.printStackTrace();
		} 
	}

	@Override
	public List<Incidencia> getIncidenciasAbiertas() {
		List<Incidencia> incidencias = new ArrayList<Incidencia>();
	
		try {
			for (Incidencia i: repositorioIncidencia.getAll()) {
				if (i.getEstado() == EstadoIncidencia.PENDIENTE) {
					incidencias.add(i);
				}
			}
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		
		return incidencias;
	}
	
	@Override
	public void gestionIncidencias(Usuario usuario, String motivoCierre, String operarioAsignado, boolean isReparada) {
		try {
			if (usuario.esAdministrador()) {  //sólo el administrador es el que puede gestionar las incidencias
				for (Incidencia i : repositorioIncidencia.getAll()) {
					switch (i.getEstado()) {
						case PENDIENTE:
							if(motivoCierre!=null) {
								i.setEstado(EstadoIncidencia.CANCELADA);
								i.setFechaFin(LocalDate.now());
								i.getBicicleta().setDisponible(true);
								i.getBicicleta().setMotivo(motivoCierre);
								
							}else {
								i.setEstado(EstadoIncidencia.ASIGNADA);
								i.setOperarioAsignado(operarioAsignado);
								i.getBicicleta().setDisponible(false);
								servicioEstaciones.retirarBicicleta(i.getBicicleta().getId()); //TODO
							}
							break;
						case ASIGNADA:
								i.setEstado(EstadoIncidencia.RESUELTA);
								i.getBicicleta().setMotivo(motivoCierre);
								if (isReparada) {
									servicioEstaciones.estacionarBicicleta(i.getBicicleta().getId(), null); //TODO 
									i.getBicicleta().setDisponible(true);								
								}else 
									servicioEstaciones.darBajaBicicleta(i.getBicicleta().getId(), motivoCierre); //TODO
											
							break;
					default:
						break;		
					}
				}
			}
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
		
		
	}




}

package aadd.servicios;

import java.time.LocalDate;
import java.util.List;

import aadd.modelo.Bicicleta;
import aadd.modelo.EstadoIncidencia;
import aadd.modelo.Incidencia;
import aadd.modelo.Usuario;
import aadd.repositorio.IRepositorioBicicletasAdHoc;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioIncidencias implements IServicioIncidencias {
	
	private IRepositorioBicicletasAdHoc repositorioBicicleta = FactoriaRepositorios.getRepositorio(Bicicleta.class); 	
	private IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

	
	/**
	 * Crear una incidencia en una bicicleta. Esta operación recibe el identificador de la bicicleta y 
	 * una descripción de la incidencia. La incidencia se crea con estado pendiente y la aplicación 
	 * establece su fecha de creación y un código identificador. La bici se considera no disponible.
	 *  
	 *  @param idBicicleta: Identificador de la Bicicleta.
	 *  @param descripcion: Descripción de la Incidencia.
	 */
	@Override
	public void crearIncidencia(String idBicicleta, String descripcion) {
		Bicicleta bicicleta;
		try {
			bicicleta = repositorioBicicleta.getById(idBicicleta);
			if(bicicleta != null){
				Incidencia incidencia = new Incidencia();
				incidencia.setBicicleta(bicicleta);
				incidencia.setDescripcion(descripcion);
				incidencia.setFechaInicio(LocalDate.now());
				incidencia.setEstado(EstadoIncidencia.PENDIENTE);
				bicicleta.addIncidencia(incidencia); //TODO
				bicicleta.setDisponible(false);
				
				//repositorioIncidencia.add(incidencia); 
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
		incidencia.setIdOperarioAsignado(operarioAsignado);
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
	
    /**
	 * Gestión de incidencias. Esta funcionalidad permite cambiar el estado de las incidencias y 
	 * gestionarlas. La gestión dependerá del estado de la incidencia.

	 *  @param usuario: Usuario que hace la gestión. Debe ser el Administrador.
	 *  @param movitoCierre: En caso de que se cancela o para resolver la incidencia, se indica el motivo del cierre de la incidencia.
	 *  @param operarioAsignado: En caso de que la incidencia será asignada, se indica el identificador del operario al que le fue asignada.
	 *  @param isReparada: En caso de que la incidencia será resultada, se necesita saber si se ha reparado o no la bicicleta.
	 */
	@Override
	public void gestionIncidencias(Usuario usuario, String motivoCierre, String idOperarioAsignado, boolean isReparada) {
		
			try {
				if (usuario.esAdministrador()) {  
					for (Bicicleta b: repositorioBicicleta.getAll())
						for (Incidencia i : b.getIncidencias()){ // repositorioBicicleta.getIncidenciaByBicicleta(b.getId())) {
							
							if(i.getEstado() == EstadoIncidencia.PENDIENTE) {
								
								if(motivoCierre!=null) 
									cancelarIncidencia(i, motivoCierre);
								else 
									asignarIncidencia(i, idOperarioAsignado);
							}
							else if (i.getEstado() == EstadoIncidencia.ASIGNADA)
								resolverIncidencia(i, motivoCierre, isReparada);
					}
				}
			} catch (RepositorioException e) {
					e.printStackTrace();
			}
	}

	/**
	 * Recuperar incidencias abiertas. Esta operación devolverá un listado de incidencias que no 
	 * hayan sido cerradas.
	 */
	@Override
	public List<Incidencia> getIncidenciasAbiertas() {
		return repositorioBicicleta.getIncidenciasPendientes();
	}
	
}

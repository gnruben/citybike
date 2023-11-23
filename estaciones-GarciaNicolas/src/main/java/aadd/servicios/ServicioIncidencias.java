package aadd.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
	public String crearIncidencia(String idBicicleta, String descripcion) throws BicicletaNotFoundException{
		if (idBicicleta == null || idBicicleta.isEmpty())
			throw new IllegalArgumentException("El id de la bicicleta, que se quiere asignarle una incidencia, no puede ser nulo ni vacío");
		if (descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException("Tiene que haber una descripción de la incidencia que se ha detectado en la bicicleta");
	
		String id = null;
		try {
			Bicicleta bicicleta = repositorioBicicleta.getById(idBicicleta);
			if(bicicleta != null){
				Incidencia incidencia = new Incidencia();
				incidencia.setId(UUID.randomUUID().toString());  //ID
				incidencia.setBicicleta(bicicleta);
				incidencia.setDescripcion(descripcion);
				incidencia.setFechaInicio(LocalDate.now());
				incidencia.setEstado(EstadoIncidencia.PENDIENTE);
				bicicleta.addIncidencia(incidencia); //TODO
				
				repositorioBicicleta.update(bicicleta); //TODO
				bicicleta.setDisponible(false);
				
				//repositorioIncidencia.add(incidencia); 
				
				id = incidencia.getId();
			}
		
		} catch (EntidadNoEncontrada | RepositorioException e ) {
			throw new BicicletaNotFoundException("Bicicleta no encontrada con ID: " + idBicicleta);
		}
		
		return id;
	}
	
	
    private void cancelarIncidencia(Incidencia incidencia) {
        incidencia.setEstado(EstadoIncidencia.CANCELADA);
        incidencia.setFechaFin(LocalDate.now());
        incidencia.getBicicleta().setDisponible(true);
        incidencia.getBicicleta().setMotivo(incidencia.getDescripcion());
    }

    private void asignarIncidencia(Incidencia incidencia, String idOperarioAsignado) {
    	incidencia.setEstado(EstadoIncidencia.ASIGNADA);
		incidencia.setIdOperarioAsignado(idOperarioAsignado);
		incidencia.getBicicleta().setDisponible(false);
		servicioEstaciones.retirarBicicleta(incidencia.getBicicleta().getId()); 
	
     }

    private void resolverIncidencia(Incidencia incidencia, boolean isReparada) {
    	incidencia.setEstado(EstadoIncidencia.RESUELTA);
		incidencia.getBicicleta().setMotivo(incidencia.getDescripcion());
		incidencia.setFechaFin(LocalDate.now());
		if (isReparada) {
			servicioEstaciones.estacionarBicicleta(incidencia.getBicicleta().getId()); 
			incidencia.getBicicleta().setDisponible(true);								
		}else 
			servicioEstaciones.darBajaBicicleta(incidencia.getBicicleta().getId(), incidencia.getDescripcion());
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
									cancelarIncidencia(i);
								else 
									asignarIncidencia(i, idOperarioAsignado);
							}
							else if (i.getEstado() == EstadoIncidencia.ASIGNADA)
								resolverIncidencia(i, isReparada);
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

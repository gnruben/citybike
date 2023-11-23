package aadd.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import aadd.modelo.Bicicleta;
import aadd.modelo.EstadoIncidencia;
import aadd.modelo.Incidencia;
import aadd.modelo.Usuario;
import aadd.repositorio.IRepositorioBicicletasAdHoc;
import aadd.repositorio.RepositorioBicicletaAdHocJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioIncidencias implements IServicioIncidencias {

	private Repositorio<Bicicleta, String> repositorioBicicleta = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	private IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

	/**
	 * Crear una incidencia en una bicicleta. Esta operación recibe el identificador
	 * de la bicicleta y una descripción de la incidencia. La incidencia se crea con
	 * estado pendiente y la aplicación establece su fecha de creación y un código
	 * identificador. La bici se considera no disponible.
	 * 
	 * @param idBicicleta: Identificador de la Bicicleta.
	 * @param descripcion: Descripción de la Incidencia.
	 * @throws ServicioIncidenciasException
	 */
	@Override
	public String crearIncidencia(String idBicicleta, String descripcion) throws ServicioIncidenciasException {
		if (idBicicleta == null || idBicicleta.isEmpty())
			throw new IllegalArgumentException(
					"El id de la bicicleta, que se quiere asignarle una incidencia, no puede ser nulo ni vacío");
		if (descripcion == null || descripcion.isEmpty())
			throw new IllegalArgumentException(
					"Tiene que haber una descripción de la incidencia que se ha detectado en la bicicleta");

		String id = null;
		try {
			Bicicleta bicicleta = repositorioBicicleta.getById(idBicicleta);
			if (bicicleta != null) {
				Incidencia incidencia = new Incidencia();
				incidencia.setId(UUID.randomUUID().toString()); // ID
				incidencia.setBicicleta(bicicleta);
				incidencia.setDescripcion(descripcion);
				incidencia.setFechaInicio(LocalDate.now());
				incidencia.setEstado(EstadoIncidencia.PENDIENTE);
				bicicleta.addIncidencia(incidencia);

				repositorioBicicleta.update(bicicleta); // TODO
				bicicleta.setDisponible(false);

				// repositorioIncidencia.add(incidencia);

				id = incidencia.getId();
			}

		} catch (EntidadNoEncontrada e) {
			throw new ServicioIncidenciasException("Bicicleta no encontrada con ID: " + idBicicleta);
		} catch (RepositorioException e) {
			throw new ServicioIncidenciasException("Error en el repositorio: " + e.getStackTrace());
		}

		return id;
	}

	@Override
	public void cancelarIncidencia(Incidencia incidencia, String motivo) throws ServicioIncidenciasException {
		if (incidencia.getEstado() != EstadoIncidencia.PENDIENTE)
			throw new ServicioIncidenciasException("El estado de la incidencia no es PENDIENTE no se puede cancelar");
		
		incidencia.setEstado(EstadoIncidencia.CANCELADA);
		incidencia.setFechaFin(LocalDate.now());

		incidencia.getBicicleta().setDisponible(true);
		Bicicleta bici = incidencia.getBicicleta();
		try {
			repositorioBicicleta.update(bici);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			throw new ServicioIncidenciasException("Error en el repositorio de bicis");
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			throw new ServicioIncidenciasException("No se encontró la bici");
		}
	}

	@Override
	public void asignarIncidencia(Incidencia incidencia, String idOperarioAsignado)
			throws ServicioIncidenciasException {
		if (incidencia.getEstado() != EstadoIncidencia.PENDIENTE)
			throw new ServicioIncidenciasException("El estado de la incidencia no es PENDIENTE no se puede asignar");
		incidencia.setEstado(EstadoIncidencia.ASIGNADA);
		incidencia.setIdOperarioAsignado(idOperarioAsignado);
		
		try {
			repositorioBicicleta.update(incidencia.getBicicleta());
			servicioEstaciones.retirarBicicleta(incidencia.getBicicleta().getId());
			
		} catch (ServicioEstacionesException  e) {
			// TODO Auto-generated catch block
			throw new ServicioIncidenciasException("Error en el servicio de estaciones");
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			throw new ServicioIncidenciasException("Error en el repositorio");
		} catch (EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			throw new ServicioIncidenciasException("Error no se encontró la bici vinculada a la incidencia: "+incidencia.getBicicleta().getId());
		}

	}

	@Override
	public void resolverIncidencia(Incidencia incidencia, String motivo, boolean isReparada) throws ServicioIncidenciasException {
		if (incidencia.getEstado() != EstadoIncidencia.ASIGNADA)
			throw new ServicioIncidenciasException("El estado de la incidencia no es ASIGNADA no se puede resolver");
		incidencia.setEstado(EstadoIncidencia.RESUELTA);
		
		incidencia.setFechaFin(LocalDate.now());
		try {
			if (isReparada) {

				servicioEstaciones.estacionarBicicleta(incidencia.getBicicleta().getId());

				
			} else
				servicioEstaciones.darBajaBicicleta(incidencia.getBicicleta().getId(),motivo);
		} catch (ServicioEstacionesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gestión de incidencias. Esta funcionalidad permite cambiar el estado de las
	 * incidencias y gestionarlas. La gestión dependerá del estado de la incidencia.
	 * 
	 * @param usuario:          Usuario que hace la gestión. Debe ser el
	 *                          Administrador.
	 * @param movitoCierre:     En caso de que se cancela o para resolver la
	 *                          incidencia, se indica el motivo del cierre de la
	 *                          incidencia.
	 * @param operarioAsignado: En caso de que la incidencia será asignada, se
	 *                          indica el identificador del operario al que le fue
	 *                          asignada.
	 * @param isReparada:       En caso de que la incidencia será resultada, se
	 *                          necesita saber si se ha reparado o no la bicicleta.
	 * @throws ServicioIncidenciasException
	 */
	/*
	 * @Override public void gestionIncidencias(Usuario usuario, String
	 * motivoCierre, String idOperarioAsignado, boolean isReparada) throws
	 * ServicioIncidenciasException {
	 * 
	 * try { if (usuario.esAdministrador()) { for (Bicicleta b :
	 * repositorioBicicleta.getAll()) for (Incidencia i : b.getIncidencias()) { //
	 * repositorioBicicleta.getIncidenciaByBicicleta(b.getId())) // {
	 * 
	 * if (i.getEstado() == EstadoIncidencia.PENDIENTE) {
	 * 
	 * if (motivoCierre != null) cancelarIncidencia(i); else asignarIncidencia(i,
	 * idOperarioAsignado); } else if (i.getEstado() == EstadoIncidencia.ASIGNADA)
	 * resolverIncidencia(i, isReparada); } } } catch (RepositorioException e) {
	 * e.printStackTrace(); } }
	 */

	/**
	 * Recuperar incidencias abiertas. Esta operación devolverá un listado de
	 * incidencias que no hayan sido cerradas.
	 */
	@Override
	public List<Incidencia> getIncidenciasAbiertas() {
		return ((RepositorioBicicletaAdHocJPA) repositorioBicicleta).getIncidenciasPendientes();
	}

}

package aadd.servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder.In;

import aadd.dto.BicicletaDTO;
import aadd.dto.IncidenciaDTO;
import aadd.modelo.Bicicleta;
import aadd.modelo.EstadoIncidencia;
import aadd.modelo.Incidencia;
import aadd.repositorio.IRepositorioBicicletasAdHoc;
import aadd.repositorio.RepositorioBicicletaAdHocJPA;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioIncidencias implements IServicioIncidencias {

	private Repositorio<Bicicleta, String> repositorioBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
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
			Bicicleta bicicleta = repositorioBicicletas.getById(idBicicleta);
			// if (bicicleta != null
			if (bicicleta.isDisponible()) {
				Incidencia incidencia = new Incidencia();
				incidencia.setId(UUID.randomUUID().toString()); // ID
				incidencia.setBicicleta(bicicleta);
				incidencia.setDescripcion(descripcion);
				incidencia.setFechaInicio(LocalDate.now());
				incidencia.setEstado(EstadoIncidencia.PENDIENTE);
				bicicleta.addIncidencia(incidencia);

				bicicleta.setDisponible(false);
				repositorioBicicletas.update(bicicleta);
				id = incidencia.getId();
			} else {
				throw new ServicioIncidenciasException("Bicicleta no disponible: " + idBicicleta);
			}

		} catch (EntidadNoEncontrada e) {
			throw new ServicioIncidenciasException("Bicicleta no encontrada con ID: " + idBicicleta);
		} catch (RepositorioException e) {
			throw new ServicioIncidenciasException("Error en el repositorio: " + e.getStackTrace());
		}

		return id;
	}

	@Override
	public void cancelarIncidencia(Incidencia incidencia, String motivoCierre) throws ServicioIncidenciasException {

		if (incidencia == null)
			throw new IllegalArgumentException("Debe introducir incidencia que se quiere cancelar.");
		if (motivoCierre == null || motivoCierre.isEmpty())
			throw new IllegalArgumentException(
					"Tiene que haber un motivo de cierre de la incidencia que se quiere cancelar.");

		if (incidencia.getEstado() != EstadoIncidencia.PENDIENTE)
			throw new ServicioIncidenciasException("El estado de la incidencia no es PENDIENTE no se puede cancelar");

		incidencia.setEstado(EstadoIncidencia.CANCELADA);
		incidencia.setFechaFin(LocalDate.now());
		incidencia.setMotivoCierre(motivoCierre);

		incidencia.getBicicleta().setDisponible(true);
		Bicicleta bici = incidencia.getBicicleta();

		try {
			repositorioBicicletas.update(bici);
		} catch (RepositorioException e) {
			throw new ServicioIncidenciasException("Error en el repositorio de bicis");
		} catch (EntidadNoEncontrada e) {
			throw new ServicioIncidenciasException("No se encontró la bici");
		}
	}
	@Override
	public void cancelarIncidencia(String idIncidencia, String motivoCierre) throws ServicioIncidenciasException {
		// TODO Auto-generated method stub

		Incidencia incidencia=((IRepositorioBicicletasAdHoc)repositorioBicicletas).getIncidenciaById(idIncidencia);
		if (motivoCierre == null || motivoCierre.isEmpty())
			throw new IllegalArgumentException(
					"Tiene que haber un motivo de cierre de la incidencia que se quiere cancelar.");

		if (incidencia.getEstado() != EstadoIncidencia.PENDIENTE)
			throw new ServicioIncidenciasException("El estado de la incidencia no es PENDIENTE no se puede cancelar");

		incidencia.setEstado(EstadoIncidencia.CANCELADA);
		incidencia.setFechaFin(LocalDate.now());
		incidencia.setMotivoCierre(motivoCierre);

		incidencia.getBicicleta().setDisponible(true);
		Bicicleta bici = incidencia.getBicicleta();

		try {
			repositorioBicicletas.update(bici);
		} catch (RepositorioException e) {
			throw new ServicioIncidenciasException("Error en el repositorio de bicis");
		} catch (EntidadNoEncontrada e) {
			throw new ServicioIncidenciasException("No se encontró la bici");
		}

	}


	@Override
	public void asignarIncidencia(Incidencia incidencia, String idOperarioAsignado)
			throws ServicioIncidenciasException {
		if (incidencia == null)
			throw new IllegalArgumentException("Debe introducir incidencia que se le quiere asignar un operario.");
		if (idOperarioAsignado == null || idOperarioAsignado.isEmpty())
			throw new IllegalArgumentException(
					"El id del Operario, al que le ha sido asignada esta incidencia, no puede ser nulo.");

		if (incidencia.getEstado() != EstadoIncidencia.PENDIENTE)
			throw new ServicioIncidenciasException("El estado de la incidencia no es PENDIENTE no se puede asignar");

		try {
			incidencia.setEstado(EstadoIncidencia.ASIGNADA);
			incidencia.setIdOperarioAsignado(idOperarioAsignado);
			repositorioBicicletas.update(incidencia.getBicicleta());
			servicioEstaciones.retirarBicicleta(incidencia.getBicicleta().getId());

		} catch (ServicioEstacionesException e) {
			throw new ServicioIncidenciasException("Error en el servicio de estaciones");
		} catch (RepositorioException e) {
			throw new ServicioIncidenciasException("Error en el repositorio");
		} catch (EntidadNoEncontrada e) {
			throw new ServicioIncidenciasException(
					"Error no se encontró la bici vinculada a la incidencia: " + incidencia.getBicicleta().getId());
		}
	}
	
	@Override
	public void asignarIncidencia(String idIncidencia, String idOperarioAsignado)
			throws ServicioIncidenciasException {
		
		Incidencia incidencia=((IRepositorioBicicletasAdHoc)repositorioBicicletas).getIncidenciaById(idIncidencia);
		if (idOperarioAsignado == null || idOperarioAsignado.isEmpty())
			throw new IllegalArgumentException(
					"El id del Operario, al que le ha sido asignada esta incidencia, no puede ser nulo.");

		if (incidencia.getEstado() != EstadoIncidencia.PENDIENTE)
			throw new ServicioIncidenciasException("El estado de la incidencia no es PENDIENTE no se puede asignar");

		try {
			incidencia.setEstado(EstadoIncidencia.ASIGNADA);
			incidencia.setIdOperarioAsignado(idOperarioAsignado);
			repositorioBicicletas.update(incidencia.getBicicleta());
			servicioEstaciones.retirarBicicleta(incidencia.getBicicleta().getId());

		} catch (ServicioEstacionesException e) {
			throw new ServicioIncidenciasException("Error en el servicio de estaciones");
		} catch (RepositorioException e) {
			throw new ServicioIncidenciasException("Error en el repositorio");
		} catch (EntidadNoEncontrada e) {
			throw new ServicioIncidenciasException(
					"Error no se encontró la bici vinculada a la incidencia: " + incidencia.getBicicleta().getId());
		}
		
	}



	// TODO: pasar directamenteel id de la incidencia?
	//
	@Override
	public void resolverIncidencia(Incidencia incidencia, String motivoCierre, boolean isReparada)
			throws ServicioIncidenciasException {
		if (incidencia == null)
			throw new IllegalArgumentException("Debe introducir incidencia que se quiere resolver.");
		if (motivoCierre == null || motivoCierre.isEmpty())
			throw new IllegalArgumentException(
					"Tiene que haber un motivo de cierre de la incidencia que se quiere resolver.");

		if (incidencia.getEstado() != EstadoIncidencia.ASIGNADA)// TODO: se debería consultar el estado de la incidencia
																// del repositorio en su lugar?
			throw new ServicioIncidenciasException("El estado de la incidencia no es ASIGNADA no se puede resolver");

		try {
			Bicicleta bici = repositorioBicicletas.getById(incidencia.getBicicleta().getId());
			List<Incidencia> incidencias = bici.getIncidencias();
			Incidencia inc = incidencias.stream().filter(i -> i.getId().equals(incidencia.getId())).findFirst().get();
			inc.setEstado(EstadoIncidencia.RESUELTA);
			inc.setFechaFin(LocalDate.now());
			inc.setMotivoCierre(motivoCierre);
			repositorioBicicletas.update(bici);
			if (isReparada)
				servicioEstaciones.estacionarBicicleta(incidencia.getBicicleta().getId());
			else
				servicioEstaciones.darBajaBicicleta(incidencia.getBicicleta().getId(), motivoCierre);

		} catch (ServicioEstacionesException e) {
			e.printStackTrace();
		} catch (RepositorioException e) {
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
		}
	}

	@Override
	public void resolverIncidencia(String idIncidencia, String motivoCierre, boolean isReparada)
			throws ServicioIncidenciasException {
		
		Incidencia incidencia=getIncidenciaByID(idIncidencia);
		
		if (motivoCierre == null || motivoCierre.isEmpty())
			throw new IllegalArgumentException(
					"Tiene que haber un motivo de cierre de la incidencia que se quiere resolver.");

		if (incidencia.getEstado() != EstadoIncidencia.ASIGNADA)// TODO: se debería consultar el estado de la incidencia
																// del repositorio en su lugar?
			throw new ServicioIncidenciasException("El estado de la incidencia no es ASIGNADA no se puede resolver");

		try {
			Bicicleta bici = incidencia.getBicicleta();
			
			
			incidencia.setEstado(EstadoIncidencia.RESUELTA);
			incidencia.setFechaFin(LocalDate.now());
			incidencia.setMotivoCierre(motivoCierre);
			repositorioBicicletas.update(bici);
			if (isReparada)
				servicioEstaciones.estacionarBicicleta(incidencia.getBicicleta().getId());
			else
				servicioEstaciones.darBajaBicicleta(incidencia.getBicicleta().getId(), motivoCierre);

		} catch (ServicioEstacionesException e) {
			e.printStackTrace();
		} catch (RepositorioException e) {
			e.printStackTrace();
		} catch (EntidadNoEncontrada e) {
			e.printStackTrace();
		}
	}
	




	/**
	 * Recuperar incidencias abiertas. Esta operación devolverá un listado de
	 * incidencias que no hayan sido cerradas.
	 */
	@Override
	public List<Incidencia> getIncidenciasAbiertas() {
		return ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciasAbiertas();
	}

	
	@Override
	public List<Incidencia> getIncidenciasResueltas() {
		return ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciasResueltas();
	}
	
	@Override
	public List<Incidencia> getIncidenciasCanceladas() {
		return ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciasCanceladas();
	}
	
	@Override
	public List<Incidencia> getIncidenciasPendientes() {
		return ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciasPendientes();
	}
	@Override
	public List<Incidencia> getIncidenciasAsignadas() {
		return ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciasAsignadas();
	}




	// DTO

	@Override
	public IncidenciaDTO transformToDTO(Incidencia incidencia) {
		IncidenciaDTO idto = new IncidenciaDTO(incidencia.getId(), incidencia.getFechaInicio(),
				incidencia.getFechaFin(), incidencia.getDescripcion(), incidencia.getMotivoCierre(),
				incidencia.getEstado(), incidencia.getIdOperarioAsignado(), incidencia.getBicicleta().getId());

		return idto;
	}

	// Lazy para Incidencias Abiertas

	@Override
	public List<IncidenciaDTO> incidenciasAbiertasLazy(int start, int max) throws ServicioIncidenciasException {
		List<Incidencia> incidenciasAbiertas = getIncidenciasAbiertas();
		int tam = incidenciasAbiertas.size();

		List<IncidenciaDTO> incidenciasDTO = new ArrayList<IncidenciaDTO>();
		List<Incidencia> incidencias;

		if (tam == 0)
			throw new ServicioIncidenciasException("Error: No se han encontrado incidencias abiertas");

		if (tam < start)
			return new ArrayList<IncidenciaDTO>();

		if (tam < (max + start + 1))
			incidencias = incidenciasAbiertas.subList(start, tam);
		else
			incidencias = incidenciasAbiertas.subList(start, start + max);

		for (Incidencia b : incidencias)
			incidenciasDTO.add(transformToDTO(b));

		return incidenciasDTO;
	}

	@Override
	public int countIncidenciasAbiertas() throws ServicioIncidenciasException {
		return getIncidenciasAbiertas().size();
	}

	@Override
	public Incidencia getIncidenciaByID(String idIncidencia) {
		return ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciaById(idIncidencia);

	}

	@Override
	public List<IncidenciaDTO> incidenciasResueltasLazy(int start, int max) throws ServicioIncidenciasException {
		List<Incidencia> incidenciasResueltas = getIncidenciasResueltas();
		int tam = incidenciasResueltas.size();

		List<IncidenciaDTO> incidenciasDTO = new ArrayList<IncidenciaDTO>();
		List<Incidencia> incidencias;

		if (tam == 0)
			throw new ServicioIncidenciasException("Error: No se han encontrado incidencias resueltas");

		if (tam < start)
			return new ArrayList<IncidenciaDTO>();

		if (tam < (max + start + 1))
			incidencias = incidenciasResueltas.subList(start, tam);
		else
			incidencias = incidenciasResueltas.subList(start, start + max);

		for (Incidencia b : incidencias)
			incidenciasDTO.add(transformToDTO(b));

		return incidenciasDTO;

		
	}

	@Override
	public List<IncidenciaDTO> incidenciasAsignadasLazy(int start, int max) throws ServicioIncidenciasException {
		List<Incidencia> incidenciasAsignadas = getIncidenciasAsignadas();
		int tam = incidenciasAsignadas.size();

		List<IncidenciaDTO> incidenciasDTO = new ArrayList<IncidenciaDTO>();
		List<Incidencia> incidencias;

		if (tam == 0)
			throw new ServicioIncidenciasException("Error: No se han encontrado incidencias resueltas");

		if (tam < start)
			return new ArrayList<IncidenciaDTO>();

		if (tam < (max + start + 1))
			incidencias = incidenciasAsignadas.subList(start, tam);
		else
			incidencias = incidenciasAsignadas.subList(start, start + max);

		for (Incidencia b : incidencias)
			incidenciasDTO.add(transformToDTO(b));

		return incidenciasDTO;


	}

	@Override
	public List<IncidenciaDTO> incidenciasPendientesLazy(int start, int max) throws ServicioIncidenciasException {
		List<Incidencia> incidenciasPendientes = getIncidenciasPendientes();
		int tam = incidenciasPendientes.size();

		List<IncidenciaDTO> incidenciasDTO = new ArrayList<IncidenciaDTO>();
		List<Incidencia> incidencias;

		if (tam == 0)
			throw new ServicioIncidenciasException("Error: No se han encontrado incidencias resueltas");

		if (tam < start)
			return new ArrayList<IncidenciaDTO>();

		if (tam < (max + start + 1))
			incidencias = incidenciasPendientes.subList(start, tam);
		else
			incidencias = incidenciasPendientes.subList(start, start + max);

		for (Incidencia b : incidencias)
			incidenciasDTO.add(transformToDTO(b));

		return incidenciasDTO;

		
	}
	
	@Override
	public List<IncidenciaDTO> incidenciasCanceladasLazy(int start, int max) throws ServicioIncidenciasException {
		List<Incidencia> incidenciasCanceladas = getIncidenciasCanceladas();
		int tam = incidenciasCanceladas.size();

		List<IncidenciaDTO> incidenciasDTO = new ArrayList<IncidenciaDTO>();
		List<Incidencia> incidencias;

		if (tam == 0)
			throw new ServicioIncidenciasException("Error: No se han encontrado incidencias resueltas");

		if (tam < start)
			return new ArrayList<IncidenciaDTO>();

		if (tam < (max + start + 1))
			incidencias = incidenciasCanceladas.subList(start, tam);
		else
			incidencias = incidenciasCanceladas.subList(start, start + max);

		for (Incidencia b : incidencias)
			incidenciasDTO.add(transformToDTO(b));

		return incidenciasDTO;

	}

	@Override
	public int countIncidenciasResueltas() throws ServicioIncidenciasException {
		return getIncidenciasResueltas().size();
	}

	@Override
	public int countIncidenciasAsignadas() throws ServicioIncidenciasException {
		return getIncidenciasAsignadas().size();
	}

	@Override
	public int countIncidenciasPendientes() throws ServicioIncidenciasException {
		return getIncidenciasPendientes().size();
	}

	@Override
	public int countIncidenciasCanceladas() throws ServicioIncidenciasException {
		return getIncidenciasCanceladas().size();
	}


}

package aadd.web.bicicleta;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import aadd.dto.IncidenciaDTO;
import aadd.modelo.Bicicleta;
import aadd.modelo.Incidencia;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.IServicioIncidencias;
import aadd.servicios.ServicioIncidenciasException;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import servicio.FactoriaServicios;

//TODO
/*
 * Refresco al asignar la incidencia sin tener que refrescar manualmente. 
 * Al asignar la última incidencia y refrescar manualmente, nos sale un error de "Error: No se han encontrado incidencias resueltas", donde no tendría que mostrarlo.
 * El conteo se tiene que recalcular (Total).
 * 
 * Teniendo arreglado lo anterior, faltaría Cancelar y Resolver una incidencia.
 */

@Named
@ViewScoped
public class GestionarIncidenciaWeb extends LazyDataModel<IncidenciaDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IServicioEstaciones servicioEstaciones;
	private IServicioIncidencias servicioIncidencias;
	private Repositorio<Bicicleta, String> repositorioBicicletas;

	private List<IncidenciaDTO> incidencias;
	private IncidenciaDTO incidenciaDTO;

	private String estadoVista;//="Pendientes";
	private String idIncidencia;
	private String idOperarioAsignado;
	private String motivo;
	private Integer total;

	@Inject
	protected FacesContext facesContext;

	@PostConstruct
	public void init() {
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		if(estadoVista==null) {
			estadoVista="Pendientes";
		}
System.out.println("pasa por init: "+estadoVista);
		findTotalResults();

	}

	protected int findTotalResults() {
		if (total == null) {
			try {

				switch (estadoVista) {
				case "Pendientes": {
					total = servicioIncidencias.countIncidenciasPendientes();
					break;
				}

				case "Asignadas": {
					System.out.println("pasa por findTotalResults Asignada: "+estadoVista);
					total = servicioIncidencias.countIncidenciasAsignadas();
					break;
				}

				case "Canceladas": {
					total = servicioIncidencias.countIncidenciasCanceladas();
					break;
				}
				case "Resueltas": {
					total = servicioIncidencias.countIncidenciasResueltas();
					break;
				}
				case "Abiertas": {
					total = servicioIncidencias.countIncidenciasAbiertas();
					break;
				}

				default: {
					total = servicioIncidencias.countIncidenciasPendientes();
					break;
				}
				}

			} catch (ServicioIncidenciasException e) {
				e.printStackTrace();
			}
		}
		return total;
	}

	public GestionarIncidenciaWeb() {

		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
		repositorioBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
		incidenciaDTO = null;
		// estado = "Pendiente";
	}

	public List<IncidenciaDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {

		switch (estadoVista) {
		case "Pendientes":
			return buscarIncidenciasPendientes(first, pageSize);
		case "Asignadas":
			System.out.println("pasa por load asignadas");
			return buscarIncidenciasAsignadas(first, pageSize);
		case "Resueltas":
			return buscarIncidenciasResueltas(first, pageSize);

		case "Canceladas":
			return buscarIncidenciasCanceladas(first, pageSize);
		case "Abiertas":
			return buscarIncidenciasAbiertas(first, pageSize);
		default:
			return buscarIncidenciasPendientes(first, pageSize);

		}
		// return buscarIncidenciasPendientes(first, pageSize);
	}

	public List<IncidenciaDTO> buscarIncidenciasAbiertas(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasAbiertasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<IncidenciaDTO> buscarIncidenciasPendientes(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasPendientesLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			//e.printStackTrace();
			
			System.out.println(e.getMessage());
		}

		return null;

	}

	public List<IncidenciaDTO> buscarIncidenciasAsignadas(int inicio, int size) {

		try {
			System.out.println("pasa por buscarIncidenciasAsignadas: "+estadoVista);
			return servicioIncidencias.incidenciasAsignadasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<IncidenciaDTO> buscarIncidenciasResueltas(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasResueltasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<IncidenciaDTO> buscarIncidenciasCanceladas(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasCanceladasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return findTotalResults();
	}

	public void buscarAbiertas() {
		try {
			total = servicioIncidencias.countIncidenciasAbiertas();
		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}
	}

	public void buscarResueltas() {
		try {
			total = servicioIncidencias.countIncidenciasResueltas();
		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}
	}

	public void buscarAsignadas() {
		try {
			total = servicioIncidencias.countIncidenciasAsignadas();
		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}
	}

	public void buscarPendientes() {
		try {
			total = servicioIncidencias.countIncidenciasPendientes();
		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}
	}

	public void buscarCanceladas() {
		try {
			total = servicioIncidencias.countIncidenciasCanceladas();
		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}
	}

	
	public void asignarIncidencia( String idOperario) {
		try {

			 idOperarioAsignado = idOperario;

			servicioIncidencias.asignarIncidencia(idIncidencia, idOperarioAsignado); // TODO ver lo de idIncidencia
			buscarPendientes();// actualiza el total

		} catch (ServicioIncidenciasException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public void cancelarIncidencia(String motivo) {// String motivo) {
		try {

			
			servicioIncidencias.cancelarIncidencia(idIncidencia, motivo);

			buscarPendientes();// actualiza el total

		} catch (ServicioIncidenciasException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void resolverIncidencia(String motivo,boolean disponible) {
		try {

			
			servicioIncidencias.resolverIncidencia(idIncidencia, motivo,disponible);

			buscarAsignadas();// actualiza el total

		} catch (ServicioIncidenciasException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}



	public IncidenciaDTO getIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;

		Incidencia incidencia = servicioIncidencias.getIncidenciaByID(idIncidencia);

		incidenciaDTO = servicioIncidencias.transformToDTO(incidencia);
		return incidenciaDTO;

	}

	public String getIdIncidencia() {
		return idIncidencia;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void cambiarBusqueda(String estado){
		
		estadoVista=estado;
		switch (estadoVista) {
		case "Pendientes":
			 buscarPendientes();
		case "Asignadas":
			buscarAsignadas();
			System.out.println("pasa por case 'Asignadas'");
			
		case "Resueltas":
			buscarResueltas();

		case "Canceladas":
			buscarCanceladas();
		case "Abiertas":
			buscarAbiertas();
		default:
			buscarPendientes();

		}
		
		//TODO
		System.out.println("Se ha cambiado el estado a "+estadoVista);
	}

	public String getEstadoVista() {
		return estadoVista;
	}

	public void setEstadoVista(String estadoVista) {
		this.estadoVista = estadoVista;
	}

	public String getIdOperarioAsignado() {
		return idOperarioAsignado;
	}

	public void setIdOperarioAsignado(String idOperarioAsignado) {
		this.idOperarioAsignado = idOperarioAsignado;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public void setIdIncidencia(String idIncidencia) {
		this.idIncidencia = idIncidencia;
	}
}

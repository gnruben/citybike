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
	
	private String idIncidencia;
	private String idOperarioAsignado;
	private Integer total;

	@Inject
	protected FacesContext facesContext;

	@PostConstruct
	public void init() {
		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

		findTotalResults();

	}

	protected int findTotalResults() {
		if (total == null) {
			try {
				total = servicioIncidencias.countIncidenciasAbiertas();
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
		//estado = "Pendiente";
	}

	public List<IncidenciaDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {

//		switch(estado) {
//			case "Pendiente":
//				return buscarIncidenciasPendientes(first, pageSize);
//			//case "Abierta":
//			default:
//				return buscarIncidenciasAbiertas(first, pageSize);
				
		//}
		return buscarIncidenciasPendientes(first, pageSize);
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

	/*
	 * public void cancelarIncidencia(IncidenciaDTO inc, String motivoCierre) { try
	 * { idIncidencia= inc.getId(); Incidencia incidencia =
	 * ((RepositorioBicicletaAdHocJPA)
	 * repositorioBicicletas).getIncidenciaById(idIncidencia);
	 * 
	 * servicioIncidencias.cancelarIncidencia(incidencia, motivoCierre);
	 * facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	 * "", "Incidencia cancelada correctamente"));
	 * 
	 * load(); } catch (ServicioIncidenciasException e) {
	 * facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
	 * "", e.getMessage())); e.printStackTrace(); } }
	 */
	public void asignarIncidencia(String idOperario) {
		try {
			
			
			idOperarioAsignado = idOperario;
			
			servicioIncidencias.asignarIncidencia(idIncidencia, idOperarioAsignado); // TODO ver lo de idIncidencia
			buscarAsignadas();
		} catch (ServicioIncidenciasException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void resolverIncidencia(IncidenciaDTO inc, String motivoCierre,
	 * boolean isReparada) { try { idIncidencia= inc.getId(); Incidencia incidencia
	 * = ((RepositorioBicicletaAdHocJPA)
	 * repositorioBicicletas).getIncidenciaById(idIncidencia);
	 * 
	 * servicioIncidencias.resolverIncidencia(incidencia, motivoCierre, isReparada
	 * ); facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
	 * "", "Incidencia resuelta correctamente"));
	 * 
	 * load(); } catch (ServicioIncidenciasException e) {
	 * 
	 * facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * "", e.getMessage()));
	 * 
	 * e.printStackTrace(); } catch (IllegalArgumentException e) {
	 * facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
	 * "", e.getMessage())); e.printStackTrace(); } }
	 */

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
	
}

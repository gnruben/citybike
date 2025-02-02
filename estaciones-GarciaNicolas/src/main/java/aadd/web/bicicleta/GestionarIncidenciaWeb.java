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
import aadd.modelo.Incidencia;
import aadd.servicios.IServicioIncidencias;
import aadd.servicios.ServicioIncidenciasException;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class GestionarIncidenciaWeb extends LazyDataModel<IncidenciaDTO> {

	private static final long serialVersionUID = 1L;
	private IServicioIncidencias servicioIncidencias;
	private IncidenciaDTO incidenciaDTO;
	private String estadoVista;
	private String idIncidencia;
	private String idOperarioAsignado;
	private String motivo;
	private boolean disponible;

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	private Integer total;

	@Inject
	protected FacesContext facesContext;

	@PostConstruct
	public void init() {
		if (estadoVista == null) {
			estadoVista = "Pendientes";
		}
		findTotalResults();

	}

	protected int findTotalResults() {
		try {

			switch (estadoVista) {
			case "Pendientes": {
				total = servicioIncidencias.countIncidenciasPendientes();
				break;
			}

			case "Asignadas": {
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
			System.out.println(e.getMessage());
		}
		return total;
	}

	public GestionarIncidenciaWeb() {
		servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
		incidenciaDTO = null;
	}

	public List<IncidenciaDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {

		switch (estadoVista) {
		case "Pendientes":
			return buscarIncidenciasPendientes(first, pageSize);
		case "Asignadas":
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
	}

	public List<IncidenciaDTO> buscarIncidenciasAbiertas(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasAbiertasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public List<IncidenciaDTO> buscarIncidenciasPendientes(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasPendientesLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public List<IncidenciaDTO> buscarIncidenciasAsignadas(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasAsignadasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public List<IncidenciaDTO> buscarIncidenciasResueltas(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasResueltasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	public List<IncidenciaDTO> buscarIncidenciasCanceladas(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasCanceladasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
		}
	}

	public void buscarResueltas() {
		try {
			total = servicioIncidencias.countIncidenciasResueltas();

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAsignadas() {
		try {
			total = servicioIncidencias.countIncidenciasAsignadas();

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarPendientes() {
		try {
			total = servicioIncidencias.countIncidenciasPendientes();

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarCanceladas() {
		try {
			total = servicioIncidencias.countIncidenciasCanceladas();

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}
	}

	public void asignarIncidencia(String idOperario) {
		try {
			idOperarioAsignado = idOperario;
			servicioIncidencias.asignarIncidencia(idIncidencia, idOperarioAsignado);
			buscarPendientes(); 

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}
	}

	public void cancelarIncidencia(String motivo) {
		try {
			servicioIncidencias.cancelarIncidencia(idIncidencia, motivo);
			buscarPendientes(); 

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}
	}

	public void resolverIncidencia(String motivo, boolean disponible) {
		try {
			servicioIncidencias.resolverIncidencia(idIncidencia, motivo, disponible);
			buscarResueltas();

		} catch (ServicioIncidenciasException e) {
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

	public void cambiarBusqueda(String estado) {

		this.estadoVista = estado;

		switch (estadoVista) {
		case "Pendientes":
			buscarPendientes();
			break;
		case "Asignadas":
			buscarAsignadas();
			break;

		case "Resueltas":
			buscarResueltas();
			break;

		case "Canceladas":
			buscarCanceladas();
			break;

		case "Abiertas":
			buscarAbiertas();
			break;

		default:
			buscarPendientes();
			break;
		}
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

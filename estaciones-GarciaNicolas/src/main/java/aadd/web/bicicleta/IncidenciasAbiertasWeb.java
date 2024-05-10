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
import aadd.servicios.IServicioIncidencias;
import aadd.servicios.ServicioIncidenciasException;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class IncidenciasAbiertasWeb extends LazyDataModel<IncidenciaDTO> {

	private static final long serialVersionUID = 1L;

	private String idIncidencia;
	private IServicioIncidencias servicioIncidencias;

	private Integer total;

	@Inject
	protected FacesContext facesContext;

	@PostConstruct
	public void init() {
		findTotalResults();
	}

	protected int findTotalResults() {
		if (total == null) {

			try {

				total = servicioIncidencias.countIncidenciasAbiertas();

			} catch (ServicioIncidenciasException e) {
				System.out.println(e.getMessage());
			}
		}
		
		return total;
	}

	public IncidenciasAbiertasWeb() {
		servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
	}

	public List<IncidenciaDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {

		return buscarIncidenciasAbiertas(first, pageSize);
	}

	public List<IncidenciaDTO> buscarIncidenciasAbiertas(int inicio, int size) {

		try {
			return servicioIncidencias.incidenciasAbiertasLazy(inicio, size);

		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}

		return null;

	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return findTotalResults();
	}

	public void buscar() {
		try {
			total = servicioIncidencias.countIncidenciasAbiertas();
		} catch (ServicioIncidenciasException e) {
			System.out.println(e.getMessage());
		}
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

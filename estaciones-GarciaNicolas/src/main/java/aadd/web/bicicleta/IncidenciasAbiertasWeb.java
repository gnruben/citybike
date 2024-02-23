package aadd.web.bicicleta;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
import aadd.repositorio.RepositorioBicicletaAdHocJPA;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.IServicioIncidencias;
import aadd.servicios.ServicioIncidenciasException;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class IncidenciasAbiertasWeb extends LazyDataModel<IncidenciaDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idIncidencia;

	private IServicioEstaciones servicioEstaciones;
	private IServicioIncidencias servicioIncidencias;
	private Repositorio<Bicicleta, String> repositorioBicicletas;  

	private List<IncidenciaDTO> incidencias;
	
	private Integer total;

	@Inject
	protected FacesContext facesContext;

	@PostConstruct
    public void init() {
        servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

			findTotalResults();

    }
    protected int findTotalResults(){
        if (total == null) {
           try {
			total = servicioIncidencias.countIncidenciasAbiertas();
		} catch (ServicioIncidenciasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
        }
        return total;
    }
	
	public IncidenciasAbiertasWeb() {

		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
		servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
		repositorioBicicletas =  FactoriaRepositorios.getRepositorio(Bicicleta.class);
	}

	public List<IncidenciaDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,Map<String, FilterMeta> filterBy) {


		return buscarIncidenciasAbiertas(first, pageSize);
	}
	public List<IncidenciaDTO> buscarIncidenciasAbiertas(int inicio, int size){
		
		try {
			return servicioIncidencias.incidenciasAbiertasLazy(inicio, size);
			
		} catch (ServicioIncidenciasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
        	e.printStackTrace();
        }
    }
	

	/*public void cancelarIncidencia(IncidenciaDTO inc, String motivoCierre) {
		try {
			idIncidencia= inc.getId();
			Incidencia incidencia = ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciaById(idIncidencia);
			
			servicioIncidencias.cancelarIncidencia(incidencia, motivoCierre);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Incidencia cancelada correctamente"));

			load();
		} catch (ServicioIncidenciasException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.getMessage()));
			e.printStackTrace();
		}
	}*/
	/*public void asignarIncidencia(IncidenciaDTO inc, String idOperarioAsignado) {
		try {
			idIncidencia= inc.getId();
			Incidencia incidencia = ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciaById(idIncidencia);
			
			servicioIncidencias.asignarIncidencia(incidencia, idOperarioAsignado);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Incidencia asignada correctamente"));

			load();
		} catch (ServicioIncidenciasException e) {

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));

			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.getMessage()));
			e.printStackTrace();
		}
	}*/

	/*public void resolverIncidencia(IncidenciaDTO inc, String motivoCierre, boolean isReparada) {
		try {
			idIncidencia= inc.getId();
			Incidencia incidencia = ((RepositorioBicicletaAdHocJPA) repositorioBicicletas).getIncidenciaById(idIncidencia);
			
			servicioIncidencias.resolverIncidencia(incidencia, motivoCierre, isReparada );
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Incidencia resuelta correctamente"));

			load();
		} catch (ServicioIncidenciasException e) {

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));

			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.getMessage()));
			e.printStackTrace();
		}
	}*/
	
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

package aadd.web.bicicleta;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import aadd.dto.IncidenciaDTO;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.IServicioIncidencias;
import aadd.servicios.ServicioIncidenciasException;
import servicio.FactoriaServicios;

public class AsignarIncidenciaWeb extends LazyDataModel<IncidenciaDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private IServicioEstaciones servicioEstaciones;
	private IServicioIncidencias servicioIncidencias;
	
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

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IncidenciaDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}

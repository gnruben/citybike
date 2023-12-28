package aadd.web.bicicleta;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import aadd.dto.BicicletaDTO;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.ServicioEstacionesException;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class BuscarBiciWeb extends LazyDataModel<BicicletaDTO> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer total;

	private double latitud;
	private double longitud;
    private IServicioEstaciones servicioEstaciones;
    
    @PostConstruct
    public void init() {
        servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
        findTotalResults();
    }
    protected int findTotalResults() {
        if (total == null) {
            try {
                total = servicioEstaciones.countBicicleta(latitud, longitud);
            } catch (ServicioEstacionesException e) {             
            	e.printStackTrace();
            }
        }
        return total;
    }

    public void buscar() {
        try {
            total = servicioEstaciones.countBicicleta(latitud, longitud);
        } catch (ServicioEstacionesException e) {
        	e.printStackTrace();
        }
    }

    public List<BicicletaDTO> buscarBicicleta(int inicio, int size) {
        try {
            return servicioEstaciones.bicisCercanasLazy(latitud, longitud, inicio, size);
        } catch (ServicioEstacionesException e) {
        	e.printStackTrace();

        }
        
        return null;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return findTotalResults();
    }

    @Override
    public List<BicicletaDTO> load(int first, int pageSize, Map<String, SortMeta> sortBy,Map<String, FilterMeta> filterBy) {
        return buscarBicicleta(first, pageSize);
    }

    public Integer getTotal() {
        return total;
    }
    
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
}

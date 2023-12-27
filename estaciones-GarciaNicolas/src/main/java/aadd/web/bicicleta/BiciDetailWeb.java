package aadd.web.bicicleta;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.dto.BicicletaDTO;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.IServicioIncidencias;
import aadd.servicios.ServicioEstacionesException;
import aadd.servicios.ServicioIncidenciasException;

import servicio.FactoriaServicios;

@Named
@ViewScoped
public class BiciDetailWeb implements Serializable {

	private String idBicicleta;

	private IServicioEstaciones servicioEstaciones;
	private IServicioIncidencias servicioIncidencias;

	private BicicletaDTO bicicleta;
	private String descripcion;
	
	
	

	@Inject

	protected FacesContext facesContext;

	public BiciDetailWeb() {     

	        servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	        servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);        
	    }

	public void load() {

		try {

			bicicleta = servicioEstaciones.getById(idBicicleta);

		} catch (ServicioEstacionesException e) {

			facesContext.addMessage(null,

					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
		}

	}

	
	public void addIncidencia() {
		try {
			servicioIncidencias.crearIncidencia(idBicicleta, descripcion);
			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Incidencia añadida correctamente"));

			load();
		} catch (ServicioIncidenciasException  e) {

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));

			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.getMessage()));
			e.printStackTrace();
		}
	}

	public String getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(String idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public BicicletaDTO getBicicleta() {
		return bicicleta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

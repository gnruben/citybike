package aadd.web.bicicleta;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.modelo.Estacion;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.ServicioEstacionesException;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class AltaBiciWeb implements Serializable {

	private String modelo;
	
	private Estacion estacion;

	private LocalDate fechaFundacion;

	private IServicioEstaciones servicioEstaciones;
	
	@Inject
	protected FacesContext facesContext;

	protected ResourceBundle bundle;
	
	public AltaBiciWeb() {

		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	}
	
	@PostConstruct
	public void initBundle() {
	    bundle = ResourceBundle.getBundle("i18n.text", facesContext.getViewRoot().getLocale());

	}

	public void altaBicicleta() {
		try {
	        String resultado = servicioEstaciones.altaBicicleta(modelo,estacion);
	        		
	        facesContext.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "",bundle.getString("exitoRevista")));
	        /*
	        try {

	            facesContext.getExternalContext().redirect("detail.xhtml?id="+resultado);

	        } catch (IOException e) {

	            facesContext.addMessage(null,

	                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));

	            e.printStackTrace();

	        }*/

	    } catch (RepositorioException e) {

	        facesContext.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));

	        e.printStackTrace();

	    }

	    catch(IllegalArgumentException e) {

	        facesContext.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.getMessage()));

	        e.printStackTrace();

	    }

	}

	public String getIssn() {

		return issn;

	}

	public void setIssn(String issn) {

		this.issn = issn;

	}

	public String getNombre() {

		return nombre;

	}

	public void setNombre(String nombre) {

		this.nombre = nombre;

	}

	public String getDescripcion() {

		return descripcion;

	}

	public void setDescripcion(String descripcion) {

		this.descripcion = descripcion;

	}

	public LocalDate getFechaFundacion() {

		return fechaFundacion;

	}

	public void setFechaFundacion(LocalDate fechaFundacion) {

		this.fechaFundacion = fechaFundacion;

	}

}

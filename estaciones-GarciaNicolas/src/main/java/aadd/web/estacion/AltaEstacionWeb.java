package aadd.web.estacion;

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

import aadd.servicios.IServicioEstaciones;
import aadd.servicios.ServicioEstacionesException;

import servicio.FactoriaServicios;

@Named
@ViewScoped
public class AltaEstacionWeb implements Serializable {

	

	private String nombre;

	private int numPuestos;
	
	private String postalCode;
	
	private double lat;
	
	private double lng;


	private IServicioEstaciones servicioEstaciones;
	
	@Inject
	protected FacesContext facesContext;

	protected ResourceBundle bundle;
	
	public AltaEstacionWeb() {

		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	}
	
	@PostConstruct
	public void initBundle() {
	    bundle = ResourceBundle.getBundle("i18n.text", facesContext.getViewRoot().getLocale());

	}

	public void altaEstacion() {
		try {
	        String resultado = servicioEstaciones.crear(nombre, numPuestos, postalCode, lat, lng);
	        		
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

	    } catch (ServicioEstacionesException e) {

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



	public String getNombre() {

		return nombre;

	}

	public void setNombre(String nombre) {

		this.nombre = nombre;

	}

	public int getNumPuestos() {
		return numPuestos;
	}

	public void setNumPuestos(int numPuestos) {
		this.numPuestos = numPuestos;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public IServicioEstaciones getServicioEstaciones() {
		return servicioEstaciones;
	}

	public void setServicioEstaciones(IServicioEstaciones servicioEstaciones) {
		this.servicioEstaciones = servicioEstaciones;
	}



}

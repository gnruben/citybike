package aadd.web.bicicleta;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.servicios.IServicioEstaciones;
import aadd.servicios.ServicioEstacionesException;
import aadd.web.locale.ActiveLocale;
import servicio.FactoriaServicios;

@Named
@ViewScoped
public class AltaBiciWeb implements Serializable {

	private String modelo;

	private String idEstacion;

	private IServicioEstaciones servicioEstaciones;

	@Inject
	protected FacesContext facesContext;
	
	@Inject
    private ActiveLocale localeConfig;

	protected ResourceBundle bundle;

	public AltaBiciWeb() {

		servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	@PostConstruct
	public void initBundle() {
		bundle = ResourceBundle.getBundle("i18n.text", facesContext.getViewRoot().getLocale());

	}

	public void altaBicicleta() {
		try {
			String resultado = servicioEstaciones.altaBicicleta(modelo, idEstacion);

			facesContext.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", localeConfig.getBundle().getString("exitoAltaBici")));

			try {

				facesContext.getExternalContext().redirect("detail.xhtml?id=" + resultado);

			} catch (IOException e) {

				facesContext.addMessage(null,

						new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se ha podido navegar"));

				e.printStackTrace();

			}

		} catch (ServicioEstacionesException e) {

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));

			e.printStackTrace();

		}

		catch (IllegalArgumentException e) {

			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", e.getMessage()));

			e.printStackTrace();

		}

	}


}

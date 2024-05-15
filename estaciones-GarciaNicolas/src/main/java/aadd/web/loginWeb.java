package aadd.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
@Named
@SessionScoped
public class loginWeb implements Serializable{
	
	private String rol;
	
	
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public void login() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("rol");
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rol", rol);
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void logout() {
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}

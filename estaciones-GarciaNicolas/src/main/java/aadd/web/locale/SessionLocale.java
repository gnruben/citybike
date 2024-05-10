package aadd.web.locale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import aadd.modelo.RolUsuario;
import aadd.modelo.Usuario;

@Named
@SessionScoped
public class SessionLocale implements Serializable {

/*
    private Usuario usuario;

    @Inject

    private FacesContext context;

    private ResourceBundle bundle;

    @PostConstruct

    public void init() {

        Application app = context.getApplication();

        usuario = app.getViewHandler().calculateLocale(context);
        bundle = ResourceBundle.getBundle("i18n.text", actual);

        localesDisponibles = new ArrayList<>();

        localesDisponibles.add(app.getDefaultLocale());

        Iterator<Locale> supported = app.getSupportedLocales();

        while(supported.hasNext()) {

            localesDisponibles.add(supported.next());

        }           

    }


   /* public void reload() {     
    	context.getPartialViewContext().getEvalScripts().add("location.replace(location)");
    }


    
    public String getRol() {

        return usuario.getRol().toString();

    }

    public void setRol(String rol) {

        //actual = Locale.forLanguageTag(rol);
        //bundle = ResourceBundle.getBundle("i18n.text", actual);
    	
    	if(rol == "gestor")
    		usuario.setRol(RolUsuario.GESTOR);
    	else
    		usuario.setRol(RolUsuario.CLIENTE);
    }

   /* public List<Locale> getLocalesDisponibles() {

        return localesDisponibles;

    }


	public ResourceBundle getBundle() {
		return bundle;
	}
    
    */
}

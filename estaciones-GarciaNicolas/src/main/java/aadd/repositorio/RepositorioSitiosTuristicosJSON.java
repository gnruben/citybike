package aadd.repositorio;

import aadd.modelo.SitioTuristico;

public class RepositorioSitiosTuristicosJSON extends RepositorioJSON<SitioTuristico> {


    public RepositorioSitiosTuristicosJSON() {
        super();
    }

    protected Class<?> getClase(){
    	return SitioTuristico.class;
    }
   
}

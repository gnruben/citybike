package aadd.servicio.test;

import java.util.ArrayList;
import java.util.List;

import aadd.modelo.SitioTuristico;
import aadd.repositorio.RepositorioSitiosTuristicosJSON;
import repositorio.RepositorioException;

public class PruebaRepositorio1 {

    public static void main(String[] args) throws RepositorioException {
        RepositorioSitiosTuristicosJSON repositorio = new RepositorioSitiosTuristicosJSON();

        // Crear un nuevo SitioTuristico
        SitioTuristico sitioTuristico = new SitioTuristico("1", "Ejemplooooo", 40.0, -74.0, "https://ejemplo.com");
        List<String> categ = new ArrayList<>();
        categ.add("Naturaleza");
        categ.add("Cultura");
        sitioTuristico.addCategorias(categ);
        List<String> enl = new ArrayList<>();
        enl.add("https://enlace1.com");
        enl.add("https://enlace2.com");
        sitioTuristico.addEnlaces(enl);
        sitioTuristico.setUrlArticulo("https://ejemplo.com");

        try {
            // Agregar el sitio turístico al repositorio
            repositorio.add(sitioTuristico);
            System.out.println("Sitio turístico agregado al repositorio.");
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

        // Comprobar el contenido del archivo JSON después de agregar el sitio turístico
        System.out.println("Contenido del archivo JSON:");
        for (SitioTuristico s : repositorio.getAll()) {
        	System.out.println(s);
        }
        
    }
}


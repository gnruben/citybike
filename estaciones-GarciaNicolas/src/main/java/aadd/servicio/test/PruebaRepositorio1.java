package aadd.servicio.test;

import java.util.ArrayList;
import java.util.List;

import aadd.modelo.SitioTuristico;
import aadd.modelo.ResumenSitioTuristico;
import aadd.repositorio.RepositorioSitiosTuristicosJSON;
import repositorio.RepositorioException;

public class PruebaRepositorio1 {

    public static void main(String[] args) throws RepositorioException {
        RepositorioSitiosTuristicosJSON repositorio = new RepositorioSitiosTuristicosJSON();

        // Crear un nuevo SitioTuristico
//        ResumenSitioTuristico resumen = new ResumenSitioTuristico();
//        resumen.setNombre("Ejemplooooo");
//        resumen.setDescripcion("Descripción de ejemplo");
//        resumen.setLatitud(40.0);
//        resumen.setLongitud(-74.0);
//        resumen.setUrlArticulo("https://ejemplo.com");

        SitioTuristico sitioTuristico = new SitioTuristico("1", "Descripción de ejemplo.", "https://ejemplo.com");

        List<String> categ = new ArrayList<>();
        String a = "Naturaleza";
        categ.add(a);
        categ.add("Cultura");
        sitioTuristico.setCategorias(categ);

        List<String> enl = new ArrayList<>();
        enl.add("https://enlace1.com");
        enl.add("https://enlace2.com");
        sitioTuristico.setEnlaces(enl);

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

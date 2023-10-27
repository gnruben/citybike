package aadd.servicio.test;

import java.time.LocalDateTime;
import java.util.UUID;

import aadd.modelo.Estacion;
import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;
import aadd.repositorio.RepositorioEstacionesMemoria;
import aadd.repositorio.RepositorioSitiosTuristicosJSON;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class PruebaRepositorios {
    public static void main(String[] args) throws RepositorioException, EntidadNoEncontrada {
        // Prueba del repositorio de Estaciones
        RepositorioEstacionesMemoria repositorioEstaciones = new RepositorioEstacionesMemoria();
        Estacion estacion1 = new Estacion();
        estacion1.setNombre("Estacion 1");
        estacion1.setNumeroPuestos(10);
        estacion1.setDireccionPostal("Dirección 1");
        estacion1.setFechaAlta(LocalDateTime.now());
        estacion1.setId(UUID.randomUUID().toString());
        estacion1.setLongitud(40.123);
        estacion1.setLatitud(-3.456);

        String estacionId = repositorioEstaciones.add(estacion1);
        Estacion estacionRecuperada = repositorioEstaciones.getEstacion(estacionId);
        
        System.out.println("Estación Recuperada:\n" + estacionRecuperada);
        System.out.println("\nLas lista de Estación que hay en el repositorio\n");
        for(Estacion e: repositorioEstaciones.getEstaciones())
        	System.out.println(e);
        
        // Prueba del repositorio de Sitios Turísticos
        System.out.println("\n");

        RepositorioSitiosTuristicosJSON repositorioSitiosTuristicos = new RepositorioSitiosTuristicosJSON();
        ResumenSitioTuristico resumen = new ResumenSitioTuristico();
        resumen.setNombre("Resumen 1");
        resumen.setDescripcion("Descripción 1");
        resumen.setLatitud(40.123);
        resumen.setLongitud(-3.456);
        resumen.setUrlArticulo("URL 1");
        
        SitioTuristico sitio1 = new SitioTuristico("Sitio 1", resumen);
        
        System.out.println("1. Lo que hay en el repositorio de Sitios Turisticos (antes de añadir sitio 1):\n");
        for(SitioTuristico sss : repositorioSitiosTuristicos.getAll()) {
        	System.out.println(sss);
        }

        String sitioId = repositorioSitiosTuristicos.add(sitio1);
        System.out.println("2. Lo que hay en el repositorio de Sitios Turisticos (después de añadir sitio 1):");
        for(SitioTuristico ss : repositorioSitiosTuristicos.getAll()) {
        	System.out.println(ss);
        }
        
        System.out.println("\n3. El id del sitio 1 ¿coincide?\n"+sitioId);
        
        SitioTuristico sitioRecuperado = repositorioSitiosTuristicos.getById(sitioId);
        System.out.println("\n4. Sitio Turístico recuperado (por getById):\n" +sitioRecuperado);
        
        ResumenSitioTuristico resumen2 = new ResumenSitioTuristico();
        resumen2.setNombre("Resumen 2");
        resumen2.setDescripcion("Descripción 2");
        resumen2.setLatitud(40.123);
        resumen2.setLongitud(-2.456);
        resumen2.setUrlArticulo("URL 2");
        
        SitioTuristico sitio2 = new SitioTuristico("\nSitio 2", resumen2);
        sitio2.setId(sitioId);
        
        repositorioSitiosTuristicos.update(sitio2);
        System.out.println("\n5. Sitios Turisticos (después del update):\n");
        for (SitioTuristico s : repositorioSitiosTuristicos.getAll()) {
        	System.out.println(s);
        }
        
        repositorioSitiosTuristicos.delete(sitio2);
        System.out.println("\n6. Sitios Turisticos (después del delete):");
        for (SitioTuristico s : repositorioSitiosTuristicos.getAll()) {
        	System.out.println(s);
        }
    }
}


package aadd.servicio.test;

import aadd.modelo.*;

import aadd.repositorio.IRepositorioHistorialEstacionamientoAdHoc;
import aadd.repositorio.RepositorioSitiosTuristicosJSON;
import aadd.servicios.*;
import repositorio.*;
import servicio.FactoriaServicios;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Programa2 {

	public static void main(String[] args) throws RepositorioException, ServicioIncidenciasException, ServicioEstacionesException, EntidadNoEncontrada, ServicioSitiosTuristicosException {
        
	   	Repositorio<Bicicleta, String> repositorioBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	   	Repositorio<Estacion, String> repositorioEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
	   	RepositorioSitiosTuristicosJSON repositorioSitios = FactoriaRepositorios.getRepositorio(SitioTuristico.class);
	   	
	   	IServicioIncidencias  servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
	    IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	    
	     
	    String idEstacion = servicioEstaciones.crear("1546s", 10, "20154", 36.846, 0.51); //Creamos la estación
	    
	    								           //Crear bicicletas:
     	Bicicleta b1= new Bicicleta();
     	b1.setModelo("ModeloX");
     	b1.setDisponible(false);
     	b1.setFechaAlta(LocalDate.now());
     	
     	repositorioBicicletas.add(b1);
	          	
       String idBicicleta = servicioEstaciones.altaBicicleta("ModeloX", repositorioEstaciones.getById(idEstacion));
       	        												
        // Crear incidencias:
 	
        String descripcion = "Problema con el freno";
       servicioIncidencias.crearIncidencia(idBicicleta, descripcion);
        
        String descripcion2 = "Problema con el sillón";
        servicioIncidencias.crearIncidencia(idBicicleta, descripcion2);
        
        String descripcion3 = "Problema con la rueda";
        servicioIncidencias.crearIncidencia(idBicicleta, descripcion3);
        

        // Obtener incidencias abiertas
        
        List<Incidencia> incidenciasAbiertas = servicioIncidencias.getIncidenciasAbiertas(); 

        // Cancelar incidencia
        Incidencia incidenciaCancelada = incidenciasAbiertas.get(0); 
        servicioIncidencias.cancelarIncidencia(incidenciaCancelada, "Problema resuelto");
        
        // Asignar incidencia
        incidenciasAbiertas = servicioIncidencias.getIncidenciasAbiertas();        
        Incidencia incidenciaAsignada = incidenciasAbiertas.get(0);     
        
        
        String idOperario = "Operario123";
        servicioIncidencias.asignarIncidencia(incidenciaAsignada, idOperario);
        System.out.println("La incidencia con id: "+incidenciaAsignada.getId()+", fue asignada al operario con id: "+idOperario);

        // Resolver incidencia
        servicioIncidencias.resolverIncidencia(incidenciaAsignada, "Problema resuelto", true);
        System.out.println("\nSe resolvió la incidencia: " + incidenciaAsignada);


        // Crear estación
        String e1 = "Estacion1";
        int numeroPuestos = 10;
        String postalcode = "12345";  
        double latitud = 40.0;
        double longitud = -73.0;

        String e2 = "Estacion2";
        int np = 5;
        String pc = "13245";
        double l = 41.0;
        double lo = -72.0;
        
        String e3 = "Estacion3";
        int npp = 50;
        String pcc = "10845";
        double ll = 35.0;
        double llo = -58.0;
        
        
        Bicicleta b2= new Bicicleta();
     	b2.setModelo("ModeloXMK");
     	b2.setFechaAlta(LocalDate.now());
     	
     	Bicicleta b3= new Bicicleta();
     	b3.setModelo("ModeloXXY");
     	b3.setFechaAlta(LocalDate.now());
     	
     	Bicicleta b4= new Bicicleta();
     	b4.setModelo("ModeloLMN");
     	b4.setFechaAlta(LocalDate.now());
     
    	Bicicleta b5= new Bicicleta();
     	b5.setModelo("ModeloNona");
     	b5.setFechaAlta(LocalDate.now());
     	     	
        String idEs1 = servicioEstaciones.crear(e1, numeroPuestos, postalcode, latitud, longitud);
        String idEs2 = servicioEstaciones.crear(e2, np, pc, l, lo);
        String idEs3 = servicioEstaciones.crear(e3, npp, pcc, ll, llo);
        
        // Estacionar bicicleta
        String idB1 = servicioEstaciones.altaBicicleta("ModeloXXY", repositorioEstaciones.getById(idEs1));
        String idB2 = servicioEstaciones.altaBicicleta("ModeloXMK", repositorioEstaciones.getById(idEs2));
        String idB3 = servicioEstaciones.altaBicicleta("ModeloLMN", repositorioEstaciones.getById(idEs2));
        String idB4 = servicioEstaciones.altaBicicleta("ModeloNona", repositorioEstaciones.getById(idEs3));
            
        servicioEstaciones.retirarBicicleta(idB2);                                 //Se retira ModeloXMK

        servicioIncidencias.crearIncidencia(idB1, "Problema con las ruedas");
       
        servicioIncidencias.crearIncidencia(idB3, "Problemas graves");
        
        // Obtener bicicletas estacionadas cerca de una ubicación
        
        List<Bicicleta> bicicletasEstacionadasCerca = servicioEstaciones.getBicisEstacionadasCerca(latitud, longitud);
        
        System.out.println("\nBicicletas estacionadas cerca de la ubicación: \n");
        
        for (Bicicleta b: bicicletasEstacionadasCerca) {
        	System.out.println(b);
        }
       
        
        
        SitioTuristico s1 = new SitioTuristico("s1", "kagdcibabk", "Url1");
        SitioTuristico s2 = new SitioTuristico("s2", "aljsd", "Url2");
        SitioTuristico s3 = new SitioTuristico("s3", "a_LShADS", "Url3");
        SitioTuristico s4 = new SitioTuristico("s4", "ALCKHapa", "Url4");
        
        repositorioSitios.add(s1);
        repositorioSitios.add(s2);
        repositorioSitios.add(s3);
        repositorioSitios.add(s4);
        
        List<SitioTuristico> si1 = new LinkedList<SitioTuristico>();
        si1.add(s4);
        si1.add(s3);
        si1.add(s1);
        
        List<SitioTuristico> si2 = new LinkedList<SitioTuristico>();
        si2.add(s2);
        
        List<SitioTuristico> si3 = new LinkedList<SitioTuristico>();
        si3.add(s4);
        si3.add(s1);
        
        servicioEstaciones.setSitiosTuristicos(idEs1, si1);
        servicioEstaciones.setSitiosTuristicos(idEs2, si2);
        servicioEstaciones.setSitiosTuristicos(idEs3, si3);
        
        List<Estacion> estacionesTuristicas = servicioEstaciones.getEstacionesTuristicas();
        System.out.println("\n");
        for (Estacion n: estacionesTuristicas) {
        	String id = n.getId();
        	System.out.println("El id de la estacion: "+id+ ", el número de sitios turísticos que tiene: "+n.getSitiosTuristicos().size());
        
        }
    }
}

package aadd.servicio.test;

import aadd.modelo.Bicicleta;
import aadd.modelo.Estacion;
import aadd.modelo.Incidencia;
import aadd.modelo.RegistroHistoricoEstacionamiento;
import aadd.modelo.SitioTuristico;
import aadd.repositorio.IRepositorioBicicletasAdHoc;
import aadd.repositorio.IRepositorioHistorialEstacionamientoAdHoc;
import aadd.repositorio.RepositorioBicicletaAdHocJPA;
import aadd.repositorio.RepositorioHistorialMongoDB;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.IServicioIncidencias;
import aadd.servicios.ServicioEstaciones;
import aadd.servicios.ServicioEstacionesException;
import aadd.servicios.ServicioIncidencias;
import aadd.servicios.ServicioIncidenciasException;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Programa2 {

	public static void main(String[] args) throws RepositorioException, ServicioIncidenciasException, ServicioEstacionesException, EntidadNoEncontrada {
        
	   	Repositorio<Bicicleta, String> repositorioBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	   	Repositorio<Estacion, String> repositorioEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
	   	Repositorio<RegistroHistoricoEstacionamiento, String> repositorioHistorial = FactoriaRepositorios.getRepositorio(RegistroHistoricoEstacionamiento.class);


	   	IServicioIncidencias  servicioIncidencias = FactoriaServicios.getServicio(IServicioIncidencias.class);
	    IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);
	    IRepositorioHistorialEstacionamientoAdHoc reposEstacionamiento = FactoriaRepositorios.getRepositorio(IRepositorioHistorialEstacionamientoAdHoc.class);

	     
	    //Estacion e = new Estacion("1546s", 10, "20154", 36.846, 0.51);
	    String idEstacion = servicioEstaciones.crear("1546s", 10, "20154", 36.846, 0.51); //Creamos la estación
	    
	    
	    
	    								//Crear bicicletas:
     	Bicicleta b1= new Bicicleta();
     	b1.setModelo("ModeloX");
     	b1.setDisponible(false);
     	b1.setFechaAlta(LocalDate.now());
     	
     	repositorioBicicletas.add(b1);
	          	
       String idBicicleta = servicioEstaciones.altaBicicleta("ModeloX", repositorioEstaciones.getById(idEstacion));
       
       RegistroHistoricoEstacionamiento rh = new RegistroHistoricoEstacionamiento();
	    
     	
 
            												//Misma bicicleta se le crea 2 incidencias
        // Crear incidencias:
 	
        String descripcion = "Problema con el freno";
        String idIncidencia = servicioIncidencias.crearIncidencia(idBicicleta, descripcion);
        
        String descripcion2 = "Problema con el sillón";
        String idIncidencia2 = servicioIncidencias.crearIncidencia(idBicicleta, descripcion2);
        
        String descripcion3 = "Problema con la rueda";
        String idIncidencia3 = servicioIncidencias.crearIncidencia(idBicicleta, descripcion3);
        

        // Obtener incidencias abiertas
        
        List<Incidencia> incidenciasAbiertas = servicioIncidencias.getIncidenciasAbiertas(); //Las 2 incidencias están pendientes

        // Cancelar incidencia
        Incidencia incidenciaCancelada = incidenciasAbiertas.get(0); //Cancelamos la primera
        servicioIncidencias.cancelarIncidencia(incidenciaCancelada, "Problema resuelto");
        //System.out.println("Se canceló la incidencia 1 : " + incidenciaCancelada);
       System.out.println("El número de bicis en la estación: "+reposEstacionamiento.getNumeroBicisEnEstacion(idEstacion)+ 
    		   " y la capacidad de la bici es: "+repositorioEstaciones.getById(idEstacion).getNumeroPuestos());

        // Asignar incidencia
        incidenciasAbiertas = servicioIncidencias.getIncidenciasAbiertas(); //Nos queda la segunda incidencia pendiente        
        Incidencia incidenciaAsignada = incidenciasAbiertas.get(0);  //La 2º incidencia que quedaba (problema con el sillón)        
                
        
        String idOperario = "Operario123";
        servicioIncidencias.asignarIncidencia(incidenciaAsignada, idOperario);
        //System.out.println("Se asignó la incidencia: " + incidenciaAsignada+" a un operario: " +idOperario);

        // Resolver incidencia
        servicioIncidencias.resolverIncidencia(incidenciaAsignada, "Problema resuelto", true);
       // System.out.println("Se resolvió la incidencia: " + incidenciaAsignada);


        //-----------------------------

        // Crear estación
        String nombreEstacion = "Estacion1";
        int numeroPuestos = 10;
        String postalcode = "12345";
        double latitud = 40.0;
        double longitud = -74.0;

        String idEstacionn = servicioEstaciones.crear(nombreEstacion, numeroPuestos, postalcode, latitud, longitud);
        System.out.println("Se creó la estación con ID: " + idEstacion);

        // Obtener estación
        Estacion estacion = servicioEstaciones.getEstacion(idEstacion);
        //System.out.println("Estación obtenida: " + estacion);

        // Obtener sitios turísticos
        List<SitioTuristico> sitiosTuristicos = servicioEstaciones.getSitiosTuristicos(idEstacion);
        System.out.println("Sitios turísticos cerca de la estación: " + sitiosTuristicos);

        // Estacionar bicicleta
        String modeloBicicleta = "ModeloX";
        String idBicicletaa = servicioEstaciones.altaBicicleta(modeloBicicleta, estacion);
        System.out.println("Se dio de alta la bicicleta con ID: " + idBicicletaa);

        // Estacionar bicicleta en una estación específica
        servicioEstaciones.estacionarBicicleta(idBicicleta, idEstacion);
        System.out.println("Se estacionó la bicicleta en la estación: " + idEstacion);

        // Retirar bicicleta
        servicioEstaciones.retirarBicicleta(idBicicleta);
        System.out.println("Se retiró la bicicleta de la estación: " + idEstacion);

        // Dar de baja bicicleta
        String motivoBaja = "Fin de vida útil";
        servicioEstaciones.darBajaBicicleta(idBicicleta, motivoBaja);
        System.out.println("Se dio de baja la bicicleta con ID: " + idBicicleta);

        // Obtener bicicletas estacionadas cerca de una ubicación
        List<Bicicleta> bicicletasEstacionadasCerca = servicioEstaciones.getBicisEstacionadasCerca(latitud, longitud);
        System.out.println("Bicicletas estacionadas cerca de la ubicación: " + bicicletasEstacionadasCerca);

    }
}

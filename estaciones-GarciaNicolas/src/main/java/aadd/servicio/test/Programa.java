package aadd.servicio.test;

import aadd.modelo.Estacion;
import aadd.modelo.SitioTuristico;
import aadd.modelo.ResumenSitioTuristico;
import aadd.repositorio.RepositorioEstacionesMemoria;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.ISitiosTuristicos;
import aadd.servicios.SitiosTuristicosGeoNames;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

		public class Programa {
		    public static void main(String[] args) throws ParserConfigurationException {
		        try {
		            RepositorioEstacionesMemoria rep = new RepositorioEstacionesMemoria();

		            // Creación de una estación
		            Estacion estacion = new Estacion("Estación de Prueba", 20, "123 Main St", 40.0, -73.0);

		            // Asociar un sitio turístico a la estación
		            SitioTuristico sitioTuristico = new SitioTuristico("Sitio de Prueba", "Resumen de prueba", "https://example.com");
		            //estacion.asociarSitioTuristico(sitioTuristico);

		            rep.add(estacion);
		            //System.out.println(rep.getEstaciones());
		            
		            // Creación de un servicio de estaciones
		            IServicioEstaciones servicioEstaciones = FactoriaServicios.getServicio(IServicioEstaciones.class);

		            // Creación de un servicio de sitios turísticos
		            ISitiosTuristicos sitiosTuristicos = FactoriaServicios.getServicio(ISitiosTuristicos.class);

		            // Obtener resúmenes de sitios turísticos cercanos a una ubicación
		            double latitud = 37.98404;	
		            double longitud = -1.12865 ;
		            double radio = 10.0;
		            List<ResumenSitioTuristico> resumenesCercanos = sitiosTuristicos.getResumenesCercanos(latitud, longitud, radio);
   
		            // Imprimir resúmenes de sitios turísticos cercanos
		            System.out.println("Resúmenes de sitios turísticos cercanos:");
		            for (ResumenSitioTuristico resumen : resumenesCercanos) {
		                System.out.println(resumen);
		                
		            }
		            
		            List<SitioTuristico> sitioss = new ArrayList<SitioTuristico>();
		            for(ResumenSitioTuristico r : resumenesCercanos) {
		            	String articulo = r.getUrlArticulo();
		            	int index = articulo.lastIndexOf("/");
		            	
		            	String nombre = articulo.substring(index+1);
		            	//System.out.println(nombre);
		            	SitioTuristico st = sitiosTuristicos.getSitioTuristico(nombre);
		            	
		            	sitioss.add(st);
		            	
		            }
		             
		            Estacion es = new Estacion("es1", 10, "2008", latitud, longitud);
		            String n= rep.add(es);
		           // System.out.println(rep.getAll());
		            
		            String id = servicioEstaciones.crear(es.getNombre(), es.getNumeroPuestos(), es.getDireccionPostal(), es.getLatitud(), es.getLongitud());
		            servicioEstaciones.setSitiosTuristicos(id, sitioss);
		            Estacion es2 = rep.getById(es.getId());
		            
		            
		            for(SitioTuristico s: es2.getSitiosTuristicos()) {
		            	System.out.println(s.getNombre());
		            }
		          

		            
		            String estacionId = servicioEstaciones.crear("Otra Estación", 30, "456 Elm St", 41.0, -74.0);
		            Estacion estacionn = new Estacion("Otra Estación", 30, "456 Elm St", 41.0, -74.0);
		            estacionn.setId(estacionId);
		            rep.add(estacionn);
		          
		            System.out.println("Estaciones que hay en el repositorio de estaciones de memoria \n"+rep.getAll());
		            System.out.println("Estación con id específico en el repositorio de memoria \n"+rep.getById(estacionId));
		            System.out.println("valor estacionid y estacionn: "+estacionId+ ", "+estacionn.getId());
		            
		            Estacion estacionObtenida = servicioEstaciones.getEstacion(estacionn.getId());

		            // Imprimir detalles de la estación
//		            System.out.println("Detalles de la estación obtenida:");
//		            System.out.println("Nombre: " + estacionObtenida.getNombre());
//		            System.out.println("Número de Puestos: " + estacionObtenida.getNumeroPuestos());
//		            System.out.println("Dirección Postal: " + estacionObtenida.getDireccionPostal());
//		            System.out.println("Latitud: " + estacionObtenida.getLatitud());
//		            System.out.println("Longitud: " + estacionObtenida.getLongitud());
//		            System.out.println("Fecha de Alta: " + estacionObtenida.getFechaAlta());

		            // Obtener sitios turísticos asociados a la estación
		           // List<SitioTuristico> sitiosAsociados = servicioEstaciones.getSitiosTuristicos(estacionId);
		           // System.out.println("Sitios turísticos asociados a la estación:");
		           // for (SitioTuristico sitio : sitiosAsociados) {
		              //  System.out.println("Nombre: " + sitio.getNombre());
		            //}
		        } catch (RepositorioException | EntidadNoEncontrada e) {
		            e.printStackTrace();
		        }
		    }
		}

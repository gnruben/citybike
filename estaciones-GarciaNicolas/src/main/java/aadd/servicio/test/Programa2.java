package aadd.servicio.test;

import aadd.modelo.Bicicleta;
import aadd.modelo.Estacion;
import aadd.modelo.Incidencia;
import aadd.modelo.SitioTuristico;
import aadd.servicios.IServicioEstaciones;
import aadd.servicios.IServicioIncidencias;
import aadd.servicios.ServicioEstaciones;
import aadd.servicios.ServicioEstacionesException;
import aadd.servicios.ServicioIncidencias;
import aadd.servicios.ServicioIncidenciasException;

import java.util.List;

public class Programa2 {

    public static void main(String[] args) {
        // Pruebas de ServicioIncidencias
        pruebaServicioIncidencias();

        // Pruebas de ServicioEstaciones
        pruebaServicioEstaciones();
    }

    private static void pruebaServicioIncidencias() {
        try {
            IServicioIncidencias servicioIncidencias = new ServicioIncidencias();

            // Crear incidencia
            String idBicicleta = "123";
            String descripcion = "Problema con el freno";
            String idIncidencia = servicioIncidencias.crearIncidencia(idBicicleta, descripcion);
            System.out.println("Se creó la incidencia con ID: " + idIncidencia);

            // Obtener incidencias abiertas
            List<Incidencia> incidenciasAbiertas = servicioIncidencias.getIncidenciasAbiertas();
            System.out.println("Incidencias abiertas: " + incidenciasAbiertas);

            // Cancelar incidencia
            Incidencia incidenciaCancelada = incidenciasAbiertas.get(0);
            servicioIncidencias.cancelarIncidencia(incidenciaCancelada, "Problema resuelto");
            System.out.println("Se canceló la incidencia: " + incidenciaCancelada);

            // Asignar incidencia
            incidenciasAbiertas = servicioIncidencias.getIncidenciasAbiertas();
            Incidencia incidenciaAsignada = incidenciasAbiertas.get(0);
            String idOperario = "Operario123";
            servicioIncidencias.asignarIncidencia(incidenciaAsignada, idOperario);
            System.out.println("Se asignó la incidencia a un operario: " + incidenciaAsignada);

            // Resolver incidencia
            incidenciasAbiertas = servicioIncidencias.getIncidenciasAbiertas();
            Incidencia incidenciaResuelta = incidenciasAbiertas.get(0);
            servicioIncidencias.resolverIncidencia(incidenciaResuelta, "Problema resuelto", true);
            System.out.println("Se resolvió la incidencia: " + incidenciaResuelta);

        } catch (ServicioIncidenciasException e) {
            e.printStackTrace();
        }
    }

    private static void pruebaServicioEstaciones() {
        try {
            IServicioEstaciones servicioEstaciones = new ServicioEstaciones();

            // Crear estación
            String nombreEstacion = "Estacion1";
            int numeroPuestos = 10;
            String postalcode = "12345";
            double latitud = 40.0;
            double longitud = -74.0;

            String idEstacion = servicioEstaciones.crear(nombreEstacion, numeroPuestos, postalcode, latitud, longitud);
            System.out.println("Se creó la estación con ID: " + idEstacion);

            // Obtener estación
            Estacion estacion = servicioEstaciones.getEstacion(idEstacion);
            System.out.println("Estación obtenida: " + estacion);

            // Obtener sitios turísticos
            List<SitioTuristico> sitiosTuristicos = servicioEstaciones.getSitiosTuristicos(idEstacion);
            System.out.println("Sitios turísticos cerca de la estación: " + sitiosTuristicos);

            // Estacionar bicicleta
            String modeloBicicleta = "ModeloX";
            String idBicicleta = servicioEstaciones.altaBicicleta(modeloBicicleta, estacion);
            System.out.println("Se dio de alta la bicicleta con ID: " + idBicicleta);

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

        } catch (ServicioEstacionesException e) {
            e.printStackTrace();
        }
    }  
}

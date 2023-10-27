package aadd.servicio.test;

import aadd.modelo.Estacion;
import aadd.servicios.IServicioEstaciones;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class Programa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IServicioEstaciones servicio=FactoriaServicios.getServicio(IServicioEstaciones.class);
		
		Estacion e1=new Estacion();
		Estacion e2;
		e1.setNombre("E1");
		e1.setLatitud(1.25);
		e1.setLongitud(-0.25);
		e1.setNumeroPuestos(5);
		try {
			String id=servicio.crear("E1", 5,"", 1.25, -0.25);
			
			e2=servicio.getEstacion(id);
			System.out.println("Añadida estación :" +e2.toString());
			
		} catch (RepositorioException | EntidadNoEncontrada e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package aadd.servicios;

/*
 * Excepción que representa un fallo en el sistema de persistencia.
 * Al instanciarla, se establece la excepción interna que produce el error (causa).
 */

@SuppressWarnings("serial")
public class ServicioSitiosTuristicosException extends Exception {

	public ServicioSitiosTuristicosException(String msg, Throwable causa) {		
		super(msg, causa);
	}
	
	public ServicioSitiosTuristicosException(String msg) {
		super(msg);		
	}
	
		
}

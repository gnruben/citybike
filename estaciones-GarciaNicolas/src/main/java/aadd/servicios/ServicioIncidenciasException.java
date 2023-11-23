package aadd.servicios;

/*
 * Excepción que representa un fallo en el sistema de persistencia.
 * Al instanciarla, se establece la excepción interna que produce el error (causa).
 */

@SuppressWarnings("serial")
public class ServicioIncidenciasException extends Exception {

	public ServicioIncidenciasException(String msg, Throwable causa) {		
		super(msg, causa);
	}
	
	public ServicioIncidenciasException(String msg) {
		super(msg);		
	}
	
		
}

package aadd.servicios;

/*
 * Excepción que representa un fallo en el sistema de persistencia.
 * Al instanciarla, se establece la excepción interna que produce el error (causa).
 */

@SuppressWarnings("serial")
public class ServicioEstacionesException extends Exception {

	public ServicioEstacionesException(String msg, Throwable causa) {		
		super(msg, causa);
	}
	
	public ServicioEstacionesException(String msg) {
		super(msg);		
	}	
}

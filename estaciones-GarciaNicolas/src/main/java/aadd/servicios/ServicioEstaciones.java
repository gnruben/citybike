package aadd.servicios;

import java.time.LocalDateTime;

import aadd.modelo.Estacion;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;

public class ServicioEstaciones implements IServicioEstaciones {

	private Repositorio<Estacion, String> repositorio = FactoriaRepositorios.getRepositorio(Estacion.class);

	@Override
	public String crear(String nombre, int numeroPuestos, double lat, double lng) throws RepositorioException {
		// TODO Auto-generated method stub
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío");
		if (numeroPuestos < 1)
			throw new IllegalArgumentException("El número dee puestos no puede ser menor que 1");

		Estacion estacion=new Estacion();
		estacion.setFechaAlta(LocalDateTime.now());
		estacion.setLatitud(lat);
		estacion.setLongitud(lng);
		
		String id = repositorio.add(estacion);
		return id;
	}



	@Override
	public void eliminar(String id) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		Estacion estacion=repositorio.getById(id);
		repositorio.delete(estacion);

	}



	@Override
	public Estacion getEstacion(String id) throws RepositorioException, EntidadNoEncontrada {
		// TODO Auto-generated method stub
		Estacion estacion=repositorio.getById(id);
		return estacion;
	}

}

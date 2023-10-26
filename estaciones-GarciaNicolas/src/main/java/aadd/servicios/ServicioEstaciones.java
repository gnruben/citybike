package aadd.servicios;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import aadd.modelo.Estacion;
import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioEstaciones implements IServicioEstaciones {

	private Repositorio<Estacion, String> repositorio = FactoriaRepositorios.getRepositorio(Estacion.class);
	private ISitiosTuristicos serviciosTuristicos=FactoriaServicios.getServicio(ISitiosTuristicos.class);
	private final double radius=5;

	@Override
	public String crear(String nombre, int numeroPuestos,String postalcode, double lat, double lng) throws RepositorioException {
		// TODO Auto-generated method stub
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío");
		if (numeroPuestos < 1)
			throw new IllegalArgumentException("El número dee puestos no puede ser menor que 1");

		Estacion estacion=new Estacion();
		estacion.setNombre(nombre);
		estacion.setNumeroPuestos(numeroPuestos);
		estacion.setLatitud(lat);
		estacion.setLongitud(lng);
		estacion.setFechaAlta(LocalDateTime.now());

		
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



	@Override
	public List<SitioTuristico> getSitiosTuristicos(String id) throws RepositorioException, EntidadNoEncontrada {
		double lat,lng;
		
		Estacion estacion=repositorio.getById(id);
		lat=estacion.getLatitud();
		lng=estacion.getLongitud();
		
		List<ResumenSitioTuristico> resumenes=serviciosTuristicos.getResumenesCercanos(lat, lng, radius);
		List<SitioTuristico> stList=new LinkedList<SitioTuristico>();
		
		for(ResumenSitioTuristico r:resumenes) {
			stList.add( serviciosTuristicos.getSitioTuristico(r.getNombre()));
		}
		return stList;
		
	}



	@Override
	public void setSitiosTuristicos(String id, List<SitioTuristico> sitios)
			throws RepositorioException, EntidadNoEncontrada {
		Estacion estacion=repositorio.getById(id);
		estacion.setSitiosTuristicos(sitios);
		
	}

	
}

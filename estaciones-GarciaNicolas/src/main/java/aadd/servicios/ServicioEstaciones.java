package aadd.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import aadd.modelo.Alquiler;
import aadd.modelo.Bicicleta;
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
	private Repositorio<Alquiler,String> repositorioHistorial=FactoriaRepositorios.getRepositorio(Alquiler.class);
	private Repositorio<Bicicleta,String> repositorioBicicletas=FactoriaRepositorios.getRepositorio(Bicicleta.class);
	private ISitiosTuristicos serviciosTuristicos=FactoriaServicios.getServicio(ISitiosTuristicos.class);
	

	@Override
	public String crear(String nombre, int numeroPuestos,String postalcode, double lat, double lng) throws ServicioEstacionesException {
		// TODO Auto-generated method stub
		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío");
		if (numeroPuestos < 1)
			throw new IllegalArgumentException("El número de puestos no puede ser menor que 1");

		Estacion estacion=new Estacion();
		estacion.setNombre(nombre);
		estacion.setNumeroPuestos(numeroPuestos);
		estacion.setLatitud(lat);
		estacion.setLongitud(lng);
		estacion.setFechaAlta(LocalDateTime.now());

		
		String id;
		try {
			id = repositorio.add(estacion);
		} catch (RepositorioException e) {
			// TODO Auto-generated catch block
			throw new ServicioEstacionesException("No se pudo crear la estación");
		}
		return id;
	}


	@Override
	public void eliminar(String id) throws ServicioEstacionesException{
		// TODO Auto-generated method stub
		Estacion estacion;
		try {
			estacion = repositorio.getById(id);
			repositorio.delete(estacion);
		} catch (RepositorioException e) {

			throw new ServicioEstacionesException("Un error en el repositorio no permitió la eliminación de la estación");
		} catch (EntidadNoEncontrada e) {

			throw new ServicioEstacionesException("No se encontro la estación para su eliminación");
		}
		

	}



	@Override
	public Estacion getEstacion(String id)  throws ServicioEstacionesException{
		// TODO Auto-generated method stub
		Estacion estacion=null;
		try {
			estacion = repositorio.getById(id);
		} catch (RepositorioException e) {

			throw new ServicioEstacionesException("Un error en el repositorio no permitió obtener la estación");
		} catch (EntidadNoEncontrada e) {

			throw new ServicioEstacionesException("No se encontro la estación");
		}
		
		return estacion;
	}



	@Override
	public List<SitioTuristico> getSitiosTuristicos(String id) throws ServicioEstacionesException{
		double lat,lng;
		
		Estacion estacion;
		List<SitioTuristico> stList=new LinkedList<SitioTuristico>();
		try {
			estacion = repositorio.getById(id);
			lat=estacion.getLatitud();
			lng=estacion.getLongitud();
			
			List<ResumenSitioTuristico> resumenes=serviciosTuristicos.getResumenesCercanos(lat, lng);
			
			
			for(ResumenSitioTuristico r:resumenes) {
				stList.add( serviciosTuristicos.getSitioTuristico(r.getNombre()));
			}
		} catch (RepositorioException e) {

			throw new ServicioEstacionesException("Un error en el repositorio no permitió obtener los sitios turísticos");
		} catch (EntidadNoEncontrada e) {

			throw new ServicioEstacionesException("Error: No se encontro la estación");
		} catch (ServicioSitiosTuristicosException e) {
			throw new ServicioEstacionesException("Error en el servicio de sitios turísticos");
		}

		return stList;
		
	}



	@Override
	public void setSitiosTuristicos(String id, List<SitioTuristico> sitios)
			throws RepositorioException, EntidadNoEncontrada {
		System.out.println(repositorio.getIds());

		Estacion estacion=repositorio.getById(id);
		System.out.println(estacion.getId());
		estacion.setSitiosTuristicos(sitios);
		repositorio.update(estacion);
	}
//############################################  BICICLETAS  ###################################

	@Override
	public String altaBicicleta(String modelo, Estacion e) throws ServicioEstacionesException {


		Bicicleta b=new Bicicleta();
		b.setModelo(modelo);
		b.setFechaAlta(LocalDate.now());
		
		
		String id;
		try {
			id = repositorioBicicletas.add(b);
			estacionarBicicleta(id, e.getId());
		} catch (RepositorioException e1) {
			// TODO Auto-generated catch block
			throw new ServicioEstacionesException("No se pudo dar de alta a la bicicleta");
		}
		
		return id;
	}


	@Override
	public void estacionarBicicleta(String idBicicleta) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void estacionarBicicleta(String idBicicleta, String idEstacion) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void retirarBicicleta(String idBicicleta) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void darBajaBicicleta(String idBicicleta, String motivoBaja) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Bicicleta> getBicisEstacionadasCerca(double lat, double lng) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Estacion> getEstacionesTuristicas() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

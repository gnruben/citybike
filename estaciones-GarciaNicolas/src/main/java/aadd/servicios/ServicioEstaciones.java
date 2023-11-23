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
import aadd.repositorio.IRepositorioEstacionesAdHoc;
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

		Estacion estacion= new Estacion(nombre, numeroPuestos, postalcode, lat, lng);
//		estacion.setNombre(nombre);
//		estacion.setNumeroPuestos(numeroPuestos);
//		estacion.setLatitud(lat);
//		estacion.setLongitud(lng);
//		estacion.setFechaAlta(LocalDateTime.now());

		
		String id;
		try {
			id = repositorio.add(estacion);
		} catch (RepositorioException e) {
			throw new ServicioEstacionesException("No se pudo crear la estación");
		}
		return id;
	}

	@Override
	public void eliminar(String id) throws ServicioEstacionesException{
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

	/**
	 * con la información de modelo de la bicicleta y estación en la que se 
	 * aparca. La aplicación proporcionará un identificador único a la bici (código) y 
	 * establecerá automáticamente la fecha de alta. La operación retorna el identificador de 
	 * la bici creada. Esta bici se considera disponible (para creación de incidencias,
	 * alquiler, etc.) y ocupa sitio en la estación (se estaciona).
	 * 
	 * @param modelo, el modelo de la bicicleta que se quiere dar de alta.
	 * @param e , Estación en la que se quiere dar de alta la bicicleta.
	 */
	@Override
	public String altaBicicleta(String modelo, Estacion e) throws ServicioEstacionesException {


		Bicicleta b= new Bicicleta();
		b.setModelo(modelo);
		b.setFechaAlta(LocalDate.now());
		
		
		String id;
		try {
			id = repositorioBicicletas.add(b);
			estacionarBicicleta(id, e.getId());
		} catch (RepositorioException e1) {
			throw new ServicioEstacionesException("No se pudo dar de alta a la bicicleta");
		}
		
		return id;
	}

	/**
	 * Esta operación recibe como parámetro el identificador de la bici y
	opcionalmente el identificador de una estación. Establece la estación para la bici y
	 crea un nuevo registro en el histórico de estacionamiento para
	  esa bici en esa estación, estableciendo en dicho histórico automáticamente 
	  la fecha de inicio.
	 Si el método no recibe ninguna estación, se realiza la misma funcionalidad 
	 realizando previamente la búsqueda de una estación con puestos libres para
	  estacionar la bicicleta.
	 * */
	@Override
	public void estacionarBicicleta(String idBicicleta) {
		// TODO Auto-generated method stub
		
		
	}

	

	@Override
	public void estacionarBicicleta(String idBicicleta, String idEstacion) {
		// TODO Auto-generated method stub
		
	}

/**
 * Esta operación recibe como parámetro el identificador de una bici que
está estacionada y procede a quitarla de la estación. Además,
 actualiza el registro histórico para esa bici y 
 estación estableciendo automáticamente la fecha de fin.
 * */
	@Override
	public void retirarBicicleta(String idBicicleta) {
		// TODO Auto-generated method stub
		
	}


	/**
	 * Esta operación recibe como parámetro el identificador de la bici y
el motivo de baja. Establece automáticamente la fecha de baja. 
Esta bici se considera no disponible y ya no ocupa sitio en la estación 
(se retira de la estación).
	 * */
	@Override
	public void darBajaBicicleta(String idBicicleta, String motivoBaja) {
		// TODO Auto-generated method stub
		
	}

/**
 * Esta operación recibirá unas coordenadas y devolverá las bicicletas aparcadas y 
 * disponibles en las tres estaciones más cercanas a dichas coordenadas.
 * */
	@Override
	public List<Bicicleta> getBicisEstacionadasCerca(double lat, double lng) {
		List<Bicicleta> list=new LinkedList<Bicicleta>();
		//TODO 
		//((IRepositorioEstacionesAdHoc)repositorio).getEstacionesCercanasA(lat, lng);
		
		
		return list;
	}

	/**
	 * Esta operación devuelve un listado de las estaciones ordenado de mayor a 
	 * menor número de sitios turísticos que tengan cerca.
	 * */

	@Override
	public List<Estacion> getEstacionesTuristicas() {
		List<Estacion> list=new LinkedList<Estacion>();
		return list*;
	}

	
}

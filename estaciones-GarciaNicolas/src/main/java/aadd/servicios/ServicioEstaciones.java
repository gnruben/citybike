package aadd.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import aadd.modelo.Bicicleta;
import aadd.modelo.Estacion;
import aadd.modelo.RegistroHistoricoEstacionamiento;
import aadd.modelo.ResumenSitioTuristico;
import aadd.modelo.SitioTuristico;
import aadd.repositorio.IRepositorioEstacionesAdHoc;
import aadd.repositorio.RepositorioHistorialMongoDB;
import repositorio.EntidadNoEncontrada;
import repositorio.FactoriaRepositorios;
import repositorio.Repositorio;
import repositorio.RepositorioException;
import servicio.FactoriaServicios;

public class ServicioEstaciones implements IServicioEstaciones {


	private Repositorio<Estacion, String> repositorioEstaciones = FactoriaRepositorios.getRepositorio(Estacion.class);
	private Repositorio<RegistroHistoricoEstacionamiento, String> repositorioHistorial = FactoriaRepositorios
			.getRepositorio(RegistroHistoricoEstacionamiento.class);
	private Repositorio<Bicicleta, String> repositorioBicicletas = FactoriaRepositorios.getRepositorio(Bicicleta.class);
	private ISitiosTuristicos serviciosTuristicos = FactoriaServicios.getServicio(ISitiosTuristicos.class);


	@Override
	public String crear(String nombre, int numeroPuestos, String postalcode, double lat, double lng)
			throws ServicioEstacionesException {

		if (nombre == null || nombre.isEmpty())
			throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío");
		if (numeroPuestos < 1)
			throw new IllegalArgumentException("El número de puestos no puede ser menor que 1");

		Estacion estacion = new Estacion(nombre, numeroPuestos, postalcode, lat, lng);

		estacion.setFechaAlta(LocalDateTime.now());//TODO: LocalDateTime o LocalDate?

		String id;
		try {
			id = repositorioEstaciones.add(estacion);
		} catch (RepositorioException e) {
			throw new ServicioEstacionesException("No se pudo crear la estación");
		}
		return id;
	}

	@Override
	public void eliminar(String id) throws ServicioEstacionesException {
		Estacion estacion;
		try {
			estacion = repositorioEstaciones.getById(id);
			repositorioEstaciones.delete(estacion);
		} catch (RepositorioException e) {

			throw new ServicioEstacionesException(
					"Un error en el repositorio no permitió la eliminación de la estación");
		} catch (EntidadNoEncontrada e) {

			throw new ServicioEstacionesException("No se encontro la estación para su eliminación");
		}

	}

	@Override
	public Estacion getEstacion(String id) throws ServicioEstacionesException {
		Estacion estacion = null;
		try {
			estacion = repositorioEstaciones.getById(id);
		} catch (RepositorioException e) {

			throw new ServicioEstacionesException("Un error en el repositorio no permitió obtener la estación");
		} catch (EntidadNoEncontrada e) {

			throw new ServicioEstacionesException("No se encontro la estación");
		}

		return estacion;
	}

	@Override
	public List<SitioTuristico> getSitiosTuristicos(String id) throws ServicioEstacionesException {
		double lat, lng;

		Estacion estacion;
		List<SitioTuristico> stList = new LinkedList<SitioTuristico>();
		try {
			estacion = repositorioEstaciones.getById(id);
			lat = estacion.getLatitud();
			lng = estacion.getLongitud();

			List<ResumenSitioTuristico> resumenes = serviciosTuristicos.getResumenesCercanos(lat, lng);

			for (ResumenSitioTuristico r : resumenes) {
				stList.add(serviciosTuristicos.getSitioTuristico(r.getNombre()));
			}
		} catch (RepositorioException e) {

			throw new ServicioEstacionesException(
					"Un error en el repositorio no permitió obtener los sitios turísticos");
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
		System.out.println(repositorioEstaciones.getIds());

		Estacion estacion = repositorioEstaciones.getById(id);
		System.out.println(estacion.getId());
		estacion.setSitiosTuristicos(sitios);
		repositorioEstaciones.update(estacion);
	}

//############################################  BICICLETAS  ###################################

	/**
	 * con la información de modelo de la bicicleta y estación en la que se aparca.
	 * La aplicación proporcionará un identificador único a la bici (código) y
	 * establecerá automáticamente la fecha de alta. La operación retorna el
	 * identificador de la bici creada. Esta bici se considera disponible (para
	 * creación de incidencias, alquiler, etc.) y ocupa sitio en la estación (se
	 * estaciona).
	 * 
	 * @param modelo, el modelo de la bicicleta que se quiere dar de alta.
	 * @param e       , Estación en la que se quiere dar de alta la bicicleta.
	 */
	@Override
	public String altaBicicleta(String modelo, Estacion e) throws ServicioEstacionesException {

		Bicicleta b = new Bicicleta();
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
	 * opcionalmente el identificador de una estación. Establece la estación para la
	 * bici y crea un nuevo registro en el histórico de estacionamiento para esa
	 * bici en esa estación, estableciendo en dicho histórico automáticamente la
	 * fecha de inicio. Si el método no recibe ninguna estación, se realiza la misma
	 * funcionalidad realizando previamente la búsqueda de una estación con puestos
	 * libres para estacionar la bicicleta.
	 * 
	 * @throws ServicioEstacionesException
	 */
	@Override
	public void estacionarBicicleta(String idBicicleta) throws ServicioEstacionesException {
		try {
			List<String> list_ids = repositorioEstaciones.getIds();
			
			Boolean flag = false;
			int i = 0;
			
			while (i < list_ids.size() && !flag) {
				
				if (hayPuestosLibres(list_ids.get(i))) {
					flag = true;
					estacionarBicicleta(idBicicleta, list_ids.get(i));
				}
			}

		} catch (RepositorioException e) {
			throw new ServicioEstacionesException("Ocurrió un error en el prepositorio");
		} catch (EntidadNoEncontrada e1) {
			throw new ServicioEstacionesException("No se pudo encontrar ");
		}
	}

	@Override
	public void estacionarBicicleta(String idBicicleta, String idEstacion) throws ServicioEstacionesException {
		try {

			if (hayPuestosLibres(idEstacion)) {
				Bicicleta bici=repositorioBicicletas.getById(idBicicleta);
				bici.setDisponible(true);
				repositorioBicicletas.update(bici);
				
				RegistroHistoricoEstacionamiento rh = new RegistroHistoricoEstacionamiento();
				rh.setFechaInicio(LocalDate.now());
				rh.setIdBici(idBicicleta);
				rh.setIdEstacion(idEstacion);
				try {
					repositorioHistorial.add(rh);
				} catch (RepositorioException re) {
					throw new ServicioEstacionesException(
							"No se pudo guardar el registro histórico por un error en el repositorio");
				}
			} else {
				throw new ServicioEstacionesException(
						"La estación no tiene puestos libres, no se pudo estacionar " + idEstacion);
			}
		} catch (RepositorioException e) {
			throw new ServicioEstacionesException("Ocurrió un error en el repositorio " + idEstacion);
		} catch (EntidadNoEncontrada e) {
			throw new ServicioEstacionesException("No se pudo encontrar la estación " + idEstacion);
		}
	}

	/**
	 * Esta operación recibe como parámetro el identificador de una bici que está
	 * estacionada y procede a quitarla de la estación. Además, actualiza el
	 * registro histórico para esa bici y estación estableciendo automáticamente la
	 * fecha de fin.
	 * @throws ServicioEstacionesException 
	 */
	@Override
	public void retirarBicicleta(String idBicicleta) throws ServicioEstacionesException {
		try {
			Bicicleta bici=repositorioBicicletas.getById(idBicicleta);
			if(!bici.isDisponible()) {
				throw new ServicioEstacionesException("La bicicleta no está disponible, no se puede retirar " + idBicicleta);
			}
			bici.setDisponible(false);
			repositorioBicicletas.update(bici);
			RegistroHistoricoEstacionamiento rh=((RepositorioHistorialMongoDB)repositorioHistorial).getUltimoRegistroByIdBici(idBicicleta);
			rh.setFechaFin(LocalDate.now());
			repositorioHistorial.update(rh);
		} catch (RepositorioException e) {

			throw new ServicioEstacionesException("Ocurrió un error en el repositorio " + idBicicleta);
		} catch (EntidadNoEncontrada e) {

			throw new ServicioEstacionesException("No se encontró la Bicicleta en el repositorio: " + idBicicleta);
		}
	}

	/**
	 * Esta operación recibe como parámetro el identificador de la bici y el motivo
	 * de baja. Establece automáticamente la fecha de baja. Esta bici se considera
	 * no disponible y ya no ocupa sitio en la estación (se retira de la estación).
	 * @throws ServicioEstacionesException 
	 */
	@Override
	public void darBajaBicicleta(String idBicicleta, String motivoBaja) throws ServicioEstacionesException {
		try {
			Bicicleta bici=repositorioBicicletas.getById(idBicicleta);
			bici.setFechaBaja(LocalDate.now());
			bici.setDisponible(false);
			bici.setMotivo(motivoBaja);
			

			repositorioBicicletas.update(bici);
			RegistroHistoricoEstacionamiento rh=((RepositorioHistorialMongoDB)repositorioHistorial).getUltimoRegistroByIdBici(idBicicleta);
			rh.setFechaFin(LocalDate.now());
			repositorioHistorial.update(rh);
			
		} catch (RepositorioException e) {

			throw new ServicioEstacionesException("Ocurrió un error en el repositorio " + idBicicleta);
		} catch (EntidadNoEncontrada e) {

			throw new ServicioEstacionesException("No se encontró la Bicicleta en el repositorio: " + idBicicleta);
		}
	}

	/**
	 * Esta operación recibirá unas coordenadas y devolverá las bicicletas aparcadas
	 * y disponibles en las tres estaciones más cercanas a dichas coordenadas.
	 * @throws ServicioEstacionesException 
	 */
	@Override
	public List<Bicicleta> getBicisEstacionadasCerca(double lat, double lng) throws ServicioEstacionesException {
		List<Bicicleta> list = new LinkedList<Bicicleta>();
		
		List<Estacion> estaciones = ((IRepositorioEstacionesAdHoc) repositorioEstaciones).getEstacionesCercanasA(lat, lng);

		for(Estacion e: estaciones) {
			List<String> idBicis = ((RepositorioHistorialMongoDB) repositorioHistorial).getIdBicisByIdEstacion(e.getId());
			for(String i: idBicis) {
				try {
					list.add(repositorioBicicletas.getById(i));
					
				} catch (RepositorioException e1) {
					throw new ServicioEstacionesException("Ocurrió un error en el repositorio " + i);
				} catch (EntidadNoEncontrada e1) {
					throw new ServicioEstacionesException("No se encontró la Bicicleta en el repositorio: " + i);
				}
			}
		}

		return list;
	}

	/**
	 * Método que dado el id de una estación devuelve un true si tiene puestos
	 * libres y false en caso contrario
	 * 
	 * @throws EntidadNoEncontrada
	 * @throws RepositorioException
	 * @throws ServicioEstacionesException
	 */
	private boolean hayPuestosLibres(String idEstacion) throws RepositorioException, EntidadNoEncontrada {

		int capacidad = repositorioEstaciones.getById(idEstacion).getNumeroPuestos();
		int numeroBicis = ((RepositorioHistorialMongoDB) repositorioHistorial).getNumeroBicisEnEstacion(idEstacion);

		return numeroBicis < capacidad;

	}

	/**
	 * Esta operación devuelve un listado de las estaciones ordenado de mayor a
	 * menor número de sitios turísticos que tengan cerca.
	 */

	@Override
	public List<Estacion> getEstacionesTuristicas() {
		List<Estacion> list = new LinkedList<Estacion>();
		return list;
	}

}

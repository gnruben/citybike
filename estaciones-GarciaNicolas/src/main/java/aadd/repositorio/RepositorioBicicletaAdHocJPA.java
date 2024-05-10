package aadd.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import aadd.modelo.Bicicleta;
import aadd.modelo.EstadoIncidencia;
import aadd.modelo.Incidencia;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioBicicletaAdHocJPA extends RepositorioJPA<Bicicleta> implements IRepositorioBicicletasAdHoc {

	@Override
	public Class<Bicicleta> getClase() {
		return Bicicleta.class;
	}

	@Override
	public String getNombre() {
		return Bicicleta.class.getName().substring(Bicicleta.class.getName().lastIndexOf(".") + 1);
	}

	@Override
	public List<Bicicleta> obtenerBicicletasPorModelo(String modelo) {
		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT b " + "FROM Bicicleta b " + "WHERE b.modelo = :modelo";

		TypedQuery<Bicicleta> query = em.createQuery(queryString, Bicicleta.class);
		query.setParameter("modelo", modelo);

		return query.getResultList();
	}

	@Override
	public List<Incidencia> getIncidenciasAbiertas() {

		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT DISTINCT i " + " FROM Bicicleta b " + " INNER JOIN b.incidencias i "
				+ " WHERE i.estado = :estadoPendiente OR i.estado = :estadoAsignada";

		TypedQuery<Incidencia> query = em.createQuery(queryString, Incidencia.class);
		query.setParameter("estadoPendiente", EstadoIncidencia.PENDIENTE);
		query.setParameter("estadoAsignada", EstadoIncidencia.ASIGNADA);
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		return query.getResultList();
	}
	
	@Override
	public Incidencia getIncidenciaById(String idIncidencia) {
		
		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT DISTINCT i " + " FROM Bicicleta b " + " INNER JOIN b.incidencias i "
				+ " WHERE i.id = :idIncidencia";

		TypedQuery<Incidencia> query = em.createQuery(queryString, Incidencia.class);
		query.setParameter("idIncidencia", idIncidencia);
		//query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		
		return query.getResultList().get(0);
	}

	@Override
	public List<Incidencia> getIncidenciasPendientes() {

		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT DISTINCT i " + " FROM Bicicleta b " + " INNER JOIN b.incidencias i "
				+ " WHERE i.estado = :estadoPendiente";

		TypedQuery<Incidencia> query = em.createQuery(queryString, Incidencia.class);
		query.setParameter("estadoPendiente", EstadoIncidencia.PENDIENTE);
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);

		return query.getResultList();
		
	}

	@Override
	public List<Incidencia> getIncidenciasAsignadas() {
		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT DISTINCT i " + " FROM Bicicleta b " + " INNER JOIN b.incidencias i "
				+ " WHERE i.estado = :estadoAsignada";

		TypedQuery<Incidencia> query = em.createQuery(queryString, Incidencia.class);
		
		query.setParameter("estadoAsignada", EstadoIncidencia.ASIGNADA);
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		return query.getResultList();
		
	}

	@Override
	public List<Incidencia> getIncidenciasResueltas() {
		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT DISTINCT i " + " FROM Bicicleta b " + " INNER JOIN b.incidencias i "
				+ " WHERE i.estado = :estadoResuelta";

		TypedQuery<Incidencia> query = em.createQuery(queryString, Incidencia.class);
		
		query.setParameter("estadoResuelta", EstadoIncidencia.RESUELTA);
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);

		return query.getResultList();
		
	}

	@Override
	public List<Incidencia> getIncidenciasCanceladas() {

		EntityManager em = EntityManagerHelper.getEntityManager();

		String queryString = "SELECT DISTINCT i " + " FROM Bicicleta b " + " INNER JOIN b.incidencias i "
				+ " WHERE i.estado = :estadoCancelada";

		TypedQuery<Incidencia> query = em.createQuery(queryString, Incidencia.class);
		query.setParameter("estadoCancelada", EstadoIncidencia.CANCELADA);
		query.setHint(QueryHints.REFRESH, HintValues.TRUE);
		

		return query.getResultList();
		
	}
}

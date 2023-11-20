package aadd.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import aadd.modelo.Bicicleta;
import aadd.modelo.EstadoIncidencia;
import aadd.modelo.Incidencia;
import repositorio.RepositorioJPA;
import utils.EntityManagerHelper;

public class RepositorioBicicletaAdHocJPA extends RepositorioJPA<Bicicleta> implements IRepositorioBicicletasAdHoc{

	@Override
	public Class<Bicicleta> getClase() {
		return Bicicleta.class;
	}

	@Override
	public String getNombre() {
		return Bicicleta.class.getName().substring(Bicicleta.class.getName().lastIndexOf(".") + 1);
	}
	
	/*
	@Override
	public List<Incidencia> getIncidenciaByBicicleta(String idBicicleta) { // TODO similar al método getIncidencias de la clase Bicicleta
		
		EntityManager em = EntityManagerHelper.getEntityManager();
		
		String queryString = "SELECT DISTINCT i "
				+ " FROM Bicicleta b "
				+ " INNER JOIN b.incidencias i "
				+ " WHERE b.id = :idBicicleta";
		
		TypedQuery<Incidencia> query = em.createQuery(queryString, Incidencia.class);
        query.setParameter("idBicicleta", idBicicleta);
	
		return query.getResultList();	
	}*/
	
	@Override
	public List<Bicicleta> obtenerBicicletasPorModelo(String modelo) {
        EntityManager em = EntityManagerHelper.getEntityManager();

        String queryString = "SELECT b "
        					+ "FROM Bicicleta b "
        					+ "WHERE b.modelo = :modelo";
        
        TypedQuery<Bicicleta> query = em.createQuery(queryString, Bicicleta.class);
        query.setParameter("modelo", modelo);

        return query.getResultList();
        
    }
	
	@Override
	public List<Incidencia> getIncidenciasPendientes()  {
		
		EntityManager em = EntityManagerHelper.getEntityManager();
		
		String queryString = "SELECT DISTINCT i "
				+ " FROM Bicicleta b "
				+ " INNER JOIN b.incidencias i "
				+ " WHERE i.estado = :estadoPendiente";
		
		TypedQuery<Incidencia> query = em.createQuery(queryString, Incidencia.class);
        query.setParameter("estadoPendiente", EstadoIncidencia.PENDIENTE);
	
		return query.getResultList();
		
	}

	
}

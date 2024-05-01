package org.clintonhealthaccess.vca.service.mtilds;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.mtilds.EntregaVisita;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Servicio para el objeto EntregaVisita
 * 
 * @author William Aviles
 * 
 **/

@Service("entregaVisitaService")
@Transactional
public class EntregaVisitaService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los metas
	 * 
	 * @return una lista de <code>EntregaTarget</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<EntregaVisita> getVisitas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM EntregaVisita");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los visitas activos
	 * 
	 * @return una lista de <code>EntregaVisita</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<EntregaVisita> getActiveVisitas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM EntregaVisita visit where vist.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una EntregaVisita
	 * @param id Identificador del visit 
	 * @return un <code>EntregaVisita</code>
	 */

	public EntregaVisita getVisita(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM EntregaVisita visit where " +
				"visit.ident =:ident");
		query.setParameter("ident",ident);
		EntregaVisita visit = (EntregaVisita) query.uniqueResult();
		return visit;
	}
	
	/**
	 * Regresa una EntregaVisita
	 * @param id Identificador del visit 
	 * @return un <code>EntregaVisita</code>
	 */

	public EntregaVisita getVisita(String ident, String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM EntregaVisita visit where " +
				"vist.ident =:ident and visit.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("ident",ident);
		query.setParameter("username",username);
		EntregaVisita visit = (EntregaVisita) query.uniqueResult();
		return visit;
	}
	
	
	/**
	 * Guarda un visit
	 * @param visit 
	 * 
	 */
	public void saveVisita(EntregaVisita visit) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(visit);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<EntregaVisita> getVisitasFiltro(String codeMeta, String ownerName,
			Long desde, Long hasta, String local, String llinSeason, String llinStatus, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from EntregaTarget tar where tar.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		// if not null set time parameters
		if(!(desde==null)) {
			sqlQuery = sqlQuery + " and tar.lastModified between :fechaInicio and :fechaFinal";
		}
		if (!(codeMeta==null)) {
			sqlQuery = sqlQuery + " and tar.household.code like:codeMeta";
		}
		if (!(ownerName==null)) {
			sqlQuery = sqlQuery + " and tar.household.ownerName like:ownerName";
		}
		if(!local.equals("ALL")) {
			sqlQuery = sqlQuery + " and tar.household.local.ident=:local";
		}
		if(!llinSeason.equals("ALL")) {
			sqlQuery = sqlQuery + " and tar.ciclo.ident=:llinSeason";
		}
		if(!llinStatus.equals("ALL")) {
			sqlQuery = sqlQuery + " and tar.status=:llinStatus";
		}
		
		if(!(pasivo==null)) {
			sqlQuery = sqlQuery + " and tar.pasive=:pasivo";
		}
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		if(!(desde==null)) {
			Timestamp timeStampInicio = new Timestamp(desde);
			Timestamp timeStampFinal = new Timestamp(hasta);
			query.setTimestamp("fechaInicio", timeStampInicio);
			query.setTimestamp("fechaFinal", timeStampFinal);
		}
		if (!(codeMeta==null)) {
			query.setParameter("codeMeta", "%" + codeMeta + "%");
		}
		if (!(ownerName==null)) {
			query.setParameter("ownerName", "%" + ownerName + "%");
		}
		if(!local.equals("ALL")) {
			query.setParameter("local", local);
		}
		if(!llinSeason.equals("ALL")) {
			query.setParameter("llinSeason", llinSeason);
		}
		if(!llinStatus.equals("ALL")) {
			query.setParameter("llinStatus", llinStatus);
		}
		if(!(pasivo==null)) {
			query.setParameter("pasivo", pasivo.charAt(0));
		}
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<EntregaVisita> getVisitasMovil(String username) {
		// Retrieve session from Hibernate
		
		//Set the SQL Query initially
		String sqlQuery = "from EntregaVisita vis where vis.pasive ='0' "
				+ "and vis.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') "
				+ "and vis.target.ciclo.pasive ='0'";
				
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	

}

package org.clintonhealthaccess.vca.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.irs.Visit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Visit
 * 
 * @author William Aviles
 * 
 **/

@Service("visitService")
@Transactional
public class VisitService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las visits
	 * 
	 * @return una lista de <code>Visit</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Visit> getVisits() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Visit");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las visits activas
	 * 
	 * @return una lista de <code>Visit</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Visit> getActiveVisits() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Visit visit where visit.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las visits de un target
	 * 
	 * @return una lista de <code>Visit</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Visit> getTargetVisits(String target) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Visit visit where visit.target.ident =:target");
		query.setParameter("target",target);
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Visit
	 * @param id Identificador del visit 
	 * @return un <code>Visit</code>
	 */

	public Visit getVisit(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Visit visit where " +
				"visit.ident =:ident");
		query.setParameter("ident",ident);
		Visit visit = (Visit) query.uniqueResult();
		return visit;
	}
	
	
	/**
	 * Guarda una visit
	 * @param visit El visit a guardar
	 * 
	 */
	public void saveVisit(Visit visit) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(visit);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Visit> getVisitasFiltro(String codeHouse, String ownerName,
			Long desde, Long hasta, String local, String irsSeason, String activity, String compVisit, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from Visit visita where visita.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		// if not null set time parameters
		if(!(desde==null)) {
			sqlQuery = sqlQuery + " and visita.visitDate between :fechaInicio and :fechaFinal";
		}
		if (!(codeHouse==null)) {
			sqlQuery = sqlQuery + " and visita.target.household.code like:codeHouse";
		}
		if (!(ownerName==null)) {
			sqlQuery = sqlQuery + " and visita.target.household.ownerName like:ownerName";
		}
		if(!local.equals("ALL")) {
			sqlQuery = sqlQuery + " and visita.target.household.local.ident=:local";
		}
		if(!irsSeason.equals("ALL")) {
			sqlQuery = sqlQuery + " and visita.target.irsSeason.ident=:irsSeason";
		}
		if(!activity.equals("ALL")) {
			sqlQuery = sqlQuery + " and visita.activity=:activity";
		}
		if(!compVisit.equals("ALL")) {
			sqlQuery = sqlQuery + " and visita.compVisit=:compVisit";
		}
		if(!(pasivo==null)) {
			sqlQuery = sqlQuery + " and visita.pasive=:pasivo";
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
		if (!(codeHouse==null)) {
			query.setParameter("codeHouse", "%" + codeHouse + "%");
		}
		if (!(ownerName==null)) {
			query.setParameter("ownerName", "%" + ownerName + "%");
		}
		if(!local.equals("ALL")) {
			query.setParameter("local", local);
		}
		if(!irsSeason.equals("ALL")) {
			query.setParameter("irsSeason", irsSeason);
		}
		if(!activity.equals("ALL")) {
			query.setParameter("activity", activity);
		}
		if(!compVisit.equals("ALL")) {
			query.setParameter("compVisit", compVisit);
		}
		if(!(pasivo==null)) {
			query.setParameter("pasivo", pasivo.charAt(0));
		}
		// Retrieve all
		return  query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Visit> getVisitsMovil(String username) {
		
		//Set the SQL Query initially
		String sqlQuery = "from Visit visita where visita.pasive ='0' and "
				+ "visita.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') "
				+ "and visita.target.irsSeason.pasive ='0'";
				
				
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	
	

}

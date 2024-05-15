package org.clintonhealthaccess.vca.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.irs.Target;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Servicio para el objeto Target
 * 
 * @author William Aviles
 * 
 **/

@Service("targetService")
@Transactional
public class TargetService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los metas
	 * 
	 * @return una lista de <code>Target</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Target> getMetas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Target");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los metas activos
	 * 
	 * @return una lista de <code>Target</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Target> getActiveMetas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Target target where target.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Target
	 * @param id Identificador del target 
	 * @return un <code>Target</code>
	 */

	public Target getMeta(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Target target where " +
				"target.ident =:ident");
		query.setParameter("ident",ident);
		Target district = (Target) query.uniqueResult();
		return district;
	}
	
	/**
	 * Regresa una Target
	 * @param id Identificador del target 
	 * @return un <code>Target</code>
	 */

	public Target getMeta(String ident, String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Target target where " +
				"target.ident =:ident and target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("ident",ident);
		query.setParameter("username",username);
		Target district = (Target) query.uniqueResult();
		return district;
	}
	
	
	/**
	 * Guarda un target
	 * @param target 
	 * 
	 */
	public void saveMeta(Target target) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(target);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Target> getMetasFiltro(String codeMeta, String ownerName,
			Long desde, Long hasta, String local, String irsSeason, String sprayStatus, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from Target tar where tar.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
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
		if(!irsSeason.equals("ALL")) {
			sqlQuery = sqlQuery + " and tar.irsSeason.ident=:irsSeason";
		}
		if(!sprayStatus.equals("ALL")) {
			sqlQuery = sqlQuery + " and tar.sprayStatus=:sprayStatus";
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
		if(!irsSeason.equals("ALL")) {
			query.setParameter("irsSeason", irsSeason);
		}
		if(!sprayStatus.equals("ALL")) {
			query.setParameter("sprayStatus", sprayStatus);
		}
		if(!(pasivo==null)) {
			query.setParameter("pasivo", pasivo.charAt(0));
		}
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Target> getMetasMovil(String username) {
		// Retrieve session from Hibernate
		
		//Set the SQL Query initially
		String sqlQuery = "from Target tar where tar.pasive ='0' "
				+ "and tar.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') "
				+ "and tar.irsSeason.pasive ='0'";
				
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	

	@SuppressWarnings("unchecked")
	public List<Object[]> getSeasonLocal() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("SELECT DISTINCT target.irsSeason.ident, target.household.local.ident, target.household.local.name FROM Target target ORDER BY target.household.local.name ASC");
		// Retrieve all
		return query.list();
	}




}

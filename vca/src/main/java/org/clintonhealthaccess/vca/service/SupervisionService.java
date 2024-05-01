package org.clintonhealthaccess.vca.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Supervision
 * 
 * @author William Aviles
 * 
 **/

@Service("supervisionService")
@Transactional
public class SupervisionService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las supervisions
	 * 
	 * @return una lista de <code>Supervision</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Supervision> getSupervisions() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Supervision");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las supervisions activas
	 * 
	 * @return una lista de <code>Supervision</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Supervision> getActiveSupervisions() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Supervision supervision where supervision.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las supervisions de un target
	 * 
	 * @return una lista de <code>Supervision</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Supervision> getTargetSupervisions(String target) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Supervision supervision where supervision.target.ident =:target");
		query.setParameter("target",target);
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Supervision
	 * @param id Identificador del supervision 
	 * @return un <code>Supervision</code>
	 */

	public Supervision getSupervision(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Supervision supervision where " +
				"supervision.ident =:ident");
		query.setParameter("ident",ident);
		Supervision supervision = (Supervision) query.uniqueResult();
		return supervision;
	}
	
	
	/**
	 * Guarda una supervision
	 * @param supervision El supervision a guardar
	 * 
	 */
	public void saveSupervision(Supervision supervision) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(supervision);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Supervision> getSupervisionsFiltro(String codeHouse, String ownerName,
			Long desde, Long hasta, String local, String irsSeason, String supervisor, String rociador, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from Supervision supervision where supervision.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		// if not null set time parameters
		if(!(desde==null)) {
			sqlQuery = sqlQuery + " and supervision.supervisionDate between :fechaInicio and :fechaFinal";
		}
		if (!(codeHouse==null)) {
			sqlQuery = sqlQuery + " and supervision.target.household.code like:codeHouse";
		}
		if (!(ownerName==null)) {
			sqlQuery = sqlQuery + " and supervision.target.household.ownerName like:ownerName";
		}
		if(!local.equals("ALL")) {
			sqlQuery = sqlQuery + " and supervision.target.household.local.ident=:local";
		}
		if(!irsSeason.equals("ALL")) {
			sqlQuery = sqlQuery + " and supervision.target.irsSeason.ident=:irsSeason";
		}
		if(!supervisor.equals("ALL")) {
			sqlQuery = sqlQuery + " and supervision.supervisor=:supervisor";
		}
		if(!rociador.equals("ALL")) {
			sqlQuery = sqlQuery + " and supervision.rociador=:rociador";
		}
		if(!(pasivo==null)) {
			sqlQuery = sqlQuery + " and supervision.pasive=:pasivo";
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
		if(!supervisor.equals("ALL")) {
			query.setParameter("supervisor", supervisor);
		}
		if(!rociador.equals("ALL")) {
			query.setParameter("rociador", rociador);
		}
		if(!(pasivo==null)) {
			query.setParameter("pasivo", pasivo.charAt(0));
		}
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Supervision> getSupervisionsMovil(String username) {
		//Set the SQL Query initially
		String sqlQuery = "from Supervision supervision where supervision.pasive ='0' and "
				+ "supervision.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') "
				+ "and supervision.target.irsSeason.pasive ='0'";
				
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}

}

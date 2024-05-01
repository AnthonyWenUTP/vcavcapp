package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Personal
 * 
 * @author William Aviles
 * 
 **/

@Service("personalService")
@Transactional
public class PersonalService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las personales
	 * 
	 * @return una lista de <code>Personal</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Personal> getPersonales() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Personal");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las personales activas
	 * 
	 * @return una lista de <code>Personal</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Personal> getActivePersonales() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Personal personal where personal.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las personales activas
	 * 
	 * @return una lista de <code>Personal</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Personal> getActiveRociadores() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Personal personal where personal.pasive ='0' and personal.sprayer is true");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las personales activas
	 * 
	 * @return una lista de <code>Personal</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Personal> getActiveSupervisores() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Personal personal where personal.pasive ='0' and personal.supervisor is true");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las personales activas
	 * 
	 * @return una lista de <code>Personal</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Personal> getActiveCentinelas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Personal personal where personal.pasive ='0' and personal.sentinel is true");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Personal
	 * @param id Identificador del personal 
	 * @return un <code>Personal</code>
	 */

	public Personal getPersonal(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Personal personal where " +
				"personal.ident =:ident");
		query.setParameter("ident",ident);
		Personal personal = (Personal) query.uniqueResult();
		return personal;
	}
	
	
	/**
	 * Guarda una personal
	 * @param personal El personal a guardar
	 * 
	 */
	public void savePersonal(Personal personal) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(personal);
	}
	

}

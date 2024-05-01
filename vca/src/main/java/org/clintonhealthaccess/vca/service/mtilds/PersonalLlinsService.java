package org.clintonhealthaccess.vca.service.mtilds;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.mtilds.PersonalLlins;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto PersonalLlins
 * 
 * @author William Aviles
 * 
 **/

@Service("personalLlinsService")
@Transactional
public class PersonalLlinsService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las PersonalLlinses
	 * 
	 * @return una lista de <code>PersonalLlins</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PersonalLlins> getPersonalLlinses() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PersonalLlins");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las PersonalLlinses activas
	 * 
	 * @return una lista de <code>PersonalLlins</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PersonalLlins> getActivePersonalLlinses() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PersonalLlins PersonalLlins where PersonalLlins.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	
	
	/**
	 * Regresa una PersonalLlins
	 * @param id Identificador del PersonalLlins 
	 * @return un <code>PersonalLlins</code>
	 */

	public PersonalLlins getPersonalLlins(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM PersonalLlins PersonalLlins where " +
				"PersonalLlins.ident =:ident");
		query.setParameter("ident",ident);
		PersonalLlins PersonalLlins = (PersonalLlins) query.uniqueResult();
		return PersonalLlins;
	}
	
	
	/**
	 * Guarda una PersonalLlins
	 * @param PersonalLlins El PersonalLlins a guardar
	 * 
	 */
	public void savePersonalLlins(PersonalLlins PersonalLlins) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(PersonalLlins);
	}
	

}

package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Person;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Servicio para el objeto Person
 * 
 * @author William Aviles
 * 
 **/

@Service("personService")
@Transactional
public class PersonService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los personas
	 * 
	 * @return una lista de <code>Person</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Person> getPersonas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Person");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los personas activos
	 * 
	 * @return una lista de <code>Person</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Person> getActivePersonas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Person p where p.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los personas de una casa
	 * 
	 * @return una lista de <code>Person</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Person> getPersonasCasa(String casa) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Person p where p.casa =:casa");
		query.setParameter("casa",casa);
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Person
	 * @param id Identificador del persona 
	 * @return un <code>Person</code>
	 */

	public Person getPersona(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Person p where " +
				"p.ident =:ident");
		query.setParameter("ident",ident);
		Person person = (Person) query.uniqueResult();
		return person;
	}
	
	
	/**
	 * Guarda un person
	 * @param person 
	 * 
	 */
	public void savePersona(Person person) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(person);
	}
	
	@SuppressWarnings("unchecked")
	public List<Person> getPersonasMovil(String username) {
		
		//Set the SQL Query initially
			
		String sqlQuery = "from Person p where p.pasive ='0' "
						+ "and p.casa.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') "
						+ "and p.casa.pasive ='0'";
				
				
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	
	

}

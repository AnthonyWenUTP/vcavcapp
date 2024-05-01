package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Censador;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Censador
 * 
 * @author William Aviles
 * 
 **/

@Service("censadorService")
@Transactional
public class CensadorService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las censadors
	 * 
	 * @return una lista de <code>Censador</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Censador> getCensadores() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Censador");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las censadors activas
	 * 
	 * @return una lista de <code>Censador</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Censador> getActiveCensadores() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Censador censador where censador.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Censador
	 * @param id Identificador del censador 
	 * @return un <code>Censador</code>
	 */

	public Censador getCensador(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Censador censador where " +
				"censador.ident =:ident");
		query.setParameter("ident",ident);
		Censador censador = (Censador) query.uniqueResult();
		return censador;
	}
	
	
	/**
	 * Guarda una censador
	 * @param censador El censador a guardar
	 * 
	 */
	public void saveCensador(Censador censador) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(censador);
	}
	

}

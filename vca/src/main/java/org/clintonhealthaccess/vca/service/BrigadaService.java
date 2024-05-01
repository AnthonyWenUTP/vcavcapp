package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Brigada
 * 
 * @author William Aviles
 * 
 **/

@Service("brigadaService")
@Transactional
public class BrigadaService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las brigadas
	 * 
	 * @return una lista de <code>Brigada</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Brigada> getBrigadas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Brigada");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las brigadas activas
	 * 
	 * @return una lista de <code>Brigada</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Brigada> getActiveBrigadas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Brigada brigada where brigada.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Brigada
	 * @param id Identificador del brigada 
	 * @return un <code>Brigada</code>
	 */

	public Brigada getBrigada(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Brigada brigada where " +
				"brigada.ident =:ident");
		query.setParameter("ident",ident);
		Brigada brigada = (Brigada) query.uniqueResult();
		return brigada;
	}
	
	
	/**
	 * Guarda una brigada
	 * @param brigada El brigada a guardar
	 * 
	 */
	public void saveBrigada(Brigada brigada) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(brigada);
	}
	

}

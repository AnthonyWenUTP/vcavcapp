package org.clintonhealthaccess.vca.service.mtilds;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Ciclo
 * 
 * @author William Aviles
 * 
 **/

@Service("cicloService")
@Transactional
public class CicloService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las ciclos
	 * 
	 * @return una lista de <code>Ciclo</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Ciclo> getCiclos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Ciclo");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las ciclos activas
	 * 
	 * @return una lista de <code>Ciclo</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Ciclo> getActiveCiclos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Ciclo ciclo where ciclo.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Ciclo
	 * @param id Identificador del ciclo 
	 * @return un <code>Ciclo</code>
	 */

	public Ciclo getCiclo(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Ciclo ciclo where " +
				"ciclo.ident =:ident");
		query.setParameter("ident",ident);
		Ciclo ciclo = (Ciclo) query.uniqueResult();
		return ciclo;
	}
	
	
	/**
	 * Guarda una ciclo
	 * @param ciclo El ciclo a guardar
	 * 
	 */
	public void saveCiclo(Ciclo ciclo) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(ciclo);
	}
	

}

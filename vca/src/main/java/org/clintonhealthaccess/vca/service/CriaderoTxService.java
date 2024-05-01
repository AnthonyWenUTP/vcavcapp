package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.CriaderoTx;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto CriaderoTx
 * 
 * @author William Aviles
 * 
 **/

@Service("criaderoTxService")
@Transactional
public class CriaderoTxService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las visits
	 * 
	 * @return una lista de <code>CriaderoTx</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<CriaderoTx> getCriaderoTxs() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM CriaderoTx");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las CriaderoTxs activas
	 * 
	 * @return una lista de <code>CriaderoTx</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<CriaderoTx> getActiveCriaderoTxs() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM CriaderoTx ct where ct.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las CriaderoTxs activas para móvil
	 * 
	 * @return una lista de <code>CriaderoTx</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<CriaderoTx> getActiveCriaderoTxsMovil() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM CriaderoTx ct where ct.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las visits de un criadero
	 * 
	 * @return una lista de <code>CriaderoTx</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<CriaderoTx> getCriaderoTxs(String criadero) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM CriaderoTx visit where visit.criadero.ident =:criadero and visit.pasive ='0'");
		query.setParameter("criadero",criadero);
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Visit
	 * @param id Identificador del CriaderoTx 
	 * @return un <code>CriaderoTx</code>
	 */

	public CriaderoTx getCriaderoTx(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM CriaderoTx visit where " +
				"visit.ident =:ident");
		query.setParameter("ident",ident);
		CriaderoTx visit = (CriaderoTx) query.uniqueResult();
		return visit;
	}
	
	
	/**
	 * Guarda una visit
	 * @param visit El CriaderoTx a guardar
	 * 
	 */
	public void saveCriaderoTx(CriaderoTx visit) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(visit);
	}
	
	

}

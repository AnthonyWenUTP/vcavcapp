package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.PtoDxVisit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto PtoDxVisit
 * 
 * @author William Aviles
 * 
 **/

@Service("ptoDxVisitService")
@Transactional
public class PtoDxVisitService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las visits
	 * 
	 * @return una lista de <code>PtoDxVisit</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PtoDxVisit> getPtoDxVisits() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PtoDxVisit");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las PtoDxVisits activas
	 * 
	 * @return una lista de <code>PtoDxVisit</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PtoDxVisit> getActivePtoDxVisits() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PtoDxVisit visit where visit.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las PtoDxVisits activas  para móvil
	 * 
	 * @return una lista de <code>PtoDxVisit</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PtoDxVisit> getActivePtoDxVisitsMovil() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PtoDxVisit visit where visit.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las visits de un punto
	 * 
	 * @return una lista de <code>PtoDxVisit</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PtoDxVisit> getPtoDxVisits(String punto) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PtoDxVisit visit where visit.punto.ident =:punto and visit.pasive ='0'");
		query.setParameter("punto",punto);
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Visit
	 * @param id Identificador del PtoDxVisit 
	 * @return un <code>PtoDxVisit</code>
	 */

	public PtoDxVisit getPtoDxVisit(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM PtoDxVisit visit where " +
				"visit.ident =:ident");
		query.setParameter("ident",ident);
		PtoDxVisit visit = (PtoDxVisit) query.uniqueResult();
		return visit;
	}
	
	
	/**
	 * Guarda una visit
	 * @param visit El PtoDxVisit a guardar
	 * 
	 */
	public void savePtoDxVisit(PtoDxVisit visit) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(visit);
	}
	
	

}

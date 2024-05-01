package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto IrsSeason
 * 
 * @author William Aviles
 * 
 **/

@Service("temporadaService")
@Transactional
public class IrsSeasonService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las temporadas
	 * 
	 * @return una lista de <code>IrsSeason</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<IrsSeason> getIrsSeasons() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM IrsSeason");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las temporadas activas
	 * 
	 * @return una lista de <code>IrsSeason</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<IrsSeason> getActiveIrsSeasons() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM IrsSeason temporada where temporada.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una IrsSeason
	 * @param id Identificador del temporada 
	 * @return un <code>IrsSeason</code>
	 */

	public IrsSeason getIrsSeason(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM IrsSeason temporada where " +
				"temporada.ident =:ident");
		query.setParameter("ident",ident);
		IrsSeason temporada = (IrsSeason) query.uniqueResult();
		return temporada;
	}
	
	
	/**
	 * Guarda una temporada
	 * @param temporada El temporada a guardar
	 * 
	 */
	public void saveIrsSeason(IrsSeason temporada) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(temporada);
	}
	
	
	/**
	 * Guarda un target
	 * @param target El target a guardar
	 * 
	 */
	public void saveTarget(Target target) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(target);
	}
	

}

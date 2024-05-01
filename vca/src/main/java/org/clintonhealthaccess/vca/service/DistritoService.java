package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Distrito;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto District
 * 
 * @author William Aviles
 * 
 **/

@Service("distritoService")
@Transactional
public class DistritoService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los distritos
	 * 
	 * @return una lista de <code>Distrito</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Distrito> getDistricts() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Distrito");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los distritos activos
	 * 
	 * @return una lista de <code>Distrito</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Distrito> getActiveDistricts() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Distrito dis where dis.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una District
	 * @param id Identificador del distrito 
	 * @return un <code>Distrito</code>
	 */

	public Distrito getDistrict(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Distrito dis where " +
				"dis.ident =:ident");
		query.setParameter("ident",ident);
		Distrito district = (Distrito) query.uniqueResult();
		return district;
	}
	
	
	/**
	 * Guarda un distrito
	 * @param district 
	 * 
	 */
	public void saveDistrict(Distrito district) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(district);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Localidad> getLocalidadesDistrito(String distrito) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Localidad loc " +
				"where (loc.pasive ='0' and loc.district.ident =:distrito)");
		query.setParameter("distrito",distrito);
		// Retrieve all
		return  query.list();
	}
	

}

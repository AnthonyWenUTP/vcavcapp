package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Area;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Area
 * 
 * @author William Aviles
 * 
 **/

@Service("areaService")
@Transactional
public class AreaService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las areas
	 * 
	 * @return una lista de <code>Area</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Area> getAreas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Area");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las areas activas
	 * 
	 * @return una lista de <code>Area</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Area> getActiveAreas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Area area where area.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Area
	 * @param id Identificador del area 
	 * @return un <code>Area</code>
	 */

	public Area getArea(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Area area where " +
				"area.ident =:ident");
		query.setParameter("ident",ident);
		Area area = (Area) query.uniqueResult();
		return area;
	}
	
	
	/**
	 * Guarda una area
	 * @param area El area a guardar
	 * 
	 */
	public void saveArea(Area area) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(area);
	}

	@SuppressWarnings("unchecked")
	public List<Localidad> getLocalidadesArea(String area) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Localidad loc " +
				"where (loc.pasive ='0' and loc.district.area.ident =:area)");
		query.setParameter("area",area);
		// Retrieve all
		return  query.list();
	}

}

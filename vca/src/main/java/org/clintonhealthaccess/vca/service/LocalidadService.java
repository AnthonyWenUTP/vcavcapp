package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Localidad;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Localidad
 * 
 * @author William Aviles
 * 
 **/

@Service("localidadService")
@Transactional
public class LocalidadService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los localidades
	 * 
	 * @return una lista de <code>Localidad</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Localidad> getLocalities() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Localidad");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los localidades activos
	 * 
	 * @return una lista de <code>Localidad</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Localidad> getActiveLocalities() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Localidad loc where loc.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los localidades activos de un usuario
	 * 
	 * @return una lista de <code>Localidad</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Localidad> getActiveLocalitiesUsuario(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Localidad loc where loc.pasive ='0' and "
				+ "loc.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	
	//viv.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')
	
	/**
	 * Regresa una Locality
	 * @param id Identificador del localidad 
	 * @return un <code>Localidad</code>
	 */

	public Localidad getLocal(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Localidad loc where " +
				"loc.ident =:ident");
		query.setParameter("ident",ident);
		Localidad district = (Localidad) query.uniqueResult();
		return district;
	}
	
	
	/**
	 * Guarda un localidad
	 * @param district 
	 * 
	 */
	public void saveLocal(Localidad district) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(district);
	}
	

}

package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Muestra;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 
 * @author William Aviles
 * 
 **/

@Service("muestraService")
@Transactional
public class MuestraService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los Muestra
	 * 
	 * @return una lista de <code>Muestra</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Muestra> getMuestras() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Muestra m");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los Muestra activos para móvil
	 * 
	 * @return una lista de <code>Muestra</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Muestra> getActiveMuestrasMovil() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Muestra m where m.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los Muestra activos
	 * 
	 * @return una lista de <code>Muestra</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Muestra> getActiveMuestras() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Muestra m where m.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Muestra
	 * @param id Identificador del Muestra 
	 * @return un <code>Muestra</code>
	 */

	public Muestra getMuestra(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Muestra m where " +
				"m.ident =:ident");
		query.setParameter("ident",ident);
		Muestra pdx = (Muestra) query.uniqueResult();
		return pdx;
	}
	
	/**
	 * Regresa una Muestra
	 * @param id Identificador del Muestra 
	 * @return un <code>Muestra</code>
	 */

	public Muestra getMuestra(String ident, String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Muestra m where " +
				"m.ident =:ident and m.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("ident",ident);
		query.setParameter("username",username);
		Muestra pdx = (Muestra) query.uniqueResult();
		return pdx;
	}
	
	
	/**
	 * Guarda un Muestra
	 * @param Muestra 
	 * 
	 */
	public void saveMuestra(Muestra muestra) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(muestra);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Muestra> getMuestraFiltro(String codigo, String local, String recordUser, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from Muestra pd where pd.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if (!(codigo==null)) {
			sqlQuery = sqlQuery + " and pd.codigo like:codigo";
		}
		
		if(!local.equals("ALL")) {
			sqlQuery = sqlQuery + " and pd.local.ident=:local";
		}
		
		if(!recordUser.equals("ALL")) {
			sqlQuery = sqlQuery + " and pd.recordUser=:recordUser";
		}
		
		if(!(pasivo==null)) {
			sqlQuery = sqlQuery + " and pd.pasive=:pasivo";
		}
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		
		if (!(codigo==null)) {
			query.setParameter("codigo", "%" + codigo + "%");
		}
		if(!local.equals("ALL")) {
			query.setParameter("local", local);
		}
		if(!recordUser.equals("ALL")) {
			query.setParameter("recordUser", recordUser);
		}
		if(!(pasivo==null)) {
			query.setParameter("pasivo", pasivo.charAt(0));
		}
		// Retrieve all
		return  query.list();
	}

}

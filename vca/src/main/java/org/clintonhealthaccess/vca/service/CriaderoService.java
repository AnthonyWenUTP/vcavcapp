package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.domain.PuntosCriadero;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Servicio para el objeto Criadero
 * 
 * @author William Aviles
 * 
 **/

@Service("criaderoService")
@Transactional
public class CriaderoService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los Criadero
	 * 
	 * @return una lista de <code>Criadero</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Criadero> getCriaderos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Criadero");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los Criadero activos
	 * 
	 * @return una lista de <code>Criadero</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Criadero> getActiveCriaderos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Criadero c where c.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa todos los Criadero activos para móvil
	 * 
	 * @return una lista de <code>Criadero</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Criadero> getActiveCriaderosMovil() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Criadero c where c.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Criadero
	 * @param id Identificador del Criadero 
	 * @return un <code>Criadero</code>
	 */

	public Criadero getCriadero(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Criadero c where " +
				"c.ident =:ident");
		query.setParameter("ident",ident);
		Criadero pdx = (Criadero) query.uniqueResult();
		return pdx;
	}
	
	/**
	 * Regresa una Criadero
	 * @param id Identificador del Criadero 
	 * @return un <code>Criadero</code>
	 */

	public Criadero getCriadero(String ident, String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Criadero c where " +
				"c.ident =:ident and c.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("ident",ident);
		query.setParameter("username",username);
		Criadero pdx = (Criadero) query.uniqueResult();
		return pdx;
	}
	
	
	/**
	 * Guarda un Criadero
	 * @param Criadero 
	 * 
	 */
	public void saveCriadero(Criadero c) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(c);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Criadero> getCriaderoFiltro(String clave, String local, String recordUser, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from Criadero c where c.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		
		if(!local.equals("ALL")) {
			sqlQuery = sqlQuery + " and c.local.ident=:local";
		}
		
		if(!recordUser.equals("ALL")) {
			sqlQuery = sqlQuery + " and c.recordUser=:recordUser";
		}
		
		if(!(pasivo==null)) {
			sqlQuery = sqlQuery + " and c.pasive=:pasivo";
		}
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		

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
	
	
	@SuppressWarnings("unchecked")
	public List<PuntosCriadero> getActivePuntosCriaderos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PuntosCriadero pc where pc.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PuntosCriadero> getActivePuntosCriaderosMovil() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PuntosCriadero pc where pc.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PuntosCriadero> getPuntosCriaderos(String criadero) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PuntosCriadero pc where pc.criadero.ident =:criadero and pc.pasive ='0'order by pc.order");
		query.setParameter("criadero",criadero);
		// Retrieve all
		return  query.list();
	}
	
	public int removePuntosCriaderos(String criadero) {
		int deletedRows = 0;
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		try {
			Query query = session.createQuery("Delete FROM PuntosCriadero pc where pc.criadero.ident =:criadero");
			query.setParameter("criadero",criadero);
			deletedRows = query.executeUpdate();
		} catch (javax.persistence.NoResultException e) {
			return deletedRows;
		} catch (HibernateException he) {
			throw he;
		}
		return deletedRows;
	}
	
	/**
	 * Guarda un Punto Criadero
	 * @param pc 
	 * 
	 */
	public void savePuntosCriadero(PuntosCriadero pc) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(pc);
	}

}

package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Caso;
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

@Service("casoService")
@Transactional
public class CasoService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los Caso
	 * 
	 * @return una lista de <code>Caso</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Caso> getCasos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Caso c");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los Caso activos
	 * 
	 * @return una lista de <code>Caso</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Caso> getActiveCasos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Caso pd where pd.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa todos los Caso activos para móvil
	 * 
	 * @return una lista de <code>Caso</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Caso> getActiveCasosMovil() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Caso pd where pd.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Caso 
	 * @param id Identificador del Caso 
	 * @return un <code>Caso</code>
	 */

	public Caso getCaso(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Caso pd where " +
				"pd.ident =:ident");
		query.setParameter("ident",ident);
		Caso pdx = (Caso) query.uniqueResult();
		return pdx;
	}
	
	/**
	 * Regresa una Caso
	 * @param id Identificador del Caso 
	 * @return un <code>Caso</code>
	 */

	public Caso getCaso(String ident, String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Caso pd where " +
				"pd.ident =:ident and pd.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("ident",ident);
		query.setParameter("username",username);
		Caso pdx = (Caso) query.uniqueResult();
		return pdx;
	}
	
	
	/**
	 * Guarda un Caso
	 * @param Caso 
	 * 
	 */
	public void saveCaso(Caso pdx) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(pdx);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Caso> getCasoFiltro(String codigo, String local, String recordUser, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from Caso pd where pd.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
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

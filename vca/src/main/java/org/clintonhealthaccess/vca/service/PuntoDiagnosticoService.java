package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Servicio para el objeto PuntoDiagnostico
 * 
 * @author William Aviles
 * 
 **/

@Service("puntoDiagnosticoService")
@Transactional
public class PuntoDiagnosticoService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los PuntoDiagnostico
	 * 
	 * @return una lista de <code>PuntoDiagnostico</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PuntoDiagnostico> getPuntoDiagnosticos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PuntoDiagnostico");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los PuntoDiagnostico activos
	 * 
	 * @return una lista de <code>PuntoDiagnostico</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PuntoDiagnostico> getActivePuntoDiagnosticos() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PuntoDiagnostico pd where pd.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los PuntoDiagnostico activos para móvil
	 * 
	 * @return una lista de <code>PuntoDiagnostico</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<PuntoDiagnostico> getActivePuntoDiagnosticosMovil() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM PuntoDiagnostico pd where pd.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una PuntoDiagnostico
	 * @param id Identificador del PuntoDiagnostico 
	 * @return un <code>PuntoDiagnostico</code>
	 */

	public PuntoDiagnostico getPuntoDiagnostico(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM PuntoDiagnostico pd where " +
				"pd.ident =:ident");
		query.setParameter("ident",ident);
		PuntoDiagnostico pdx = (PuntoDiagnostico) query.uniqueResult();
		return pdx;
	}
	
	/**
	 * Regresa una PuntoDiagnostico
	 * @param id Identificador del PuntoDiagnostico 
	 * @return un <code>PuntoDiagnostico</code>
	 */

	public PuntoDiagnostico getPuntoDiagnostico(String ident, String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM PuntoDiagnostico pd where " +
				"pd.ident =:ident and pd.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("ident",ident);
		query.setParameter("username",username);
		PuntoDiagnostico pdx = (PuntoDiagnostico) query.uniqueResult();
		return pdx;
	}
	
	
	/**
	 * Guarda un PuntoDiagnostico
	 * @param PuntoDiagnostico 
	 * 
	 */
	public void savePuntoDiagnostico(PuntoDiagnostico pdx) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(pdx);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PuntoDiagnostico> getPuntoDiagnosticoFiltro(String clave, String local, String recordUser, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from PuntoDiagnostico pd where pd.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if (!(clave==null)) {
			sqlQuery = sqlQuery + " and pd.clave like:clave";
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
		
		if (!(clave==null)) {
			query.setParameter("clave", "%" + clave + "%");
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

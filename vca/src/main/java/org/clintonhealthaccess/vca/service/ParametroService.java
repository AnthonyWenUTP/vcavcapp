package org.clintonhealthaccess.vca.service;

import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Parametro;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Servicio para el objeto Parametro
 * 
 * @author William Aviles
 * 
 **/

@Service("parametroService")
@Transactional
public class ParametroService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todas las parametros
	 * 
	 * @return una lista de <code>Parametro</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Parametro> getParametros() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Parametro");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todas las parametros activas
	 * 
	 * @return una lista de <code>Parametro</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Parametro> getActiveParametros() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Parametro parametro where parametro.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una Parametro
	 * @param id Identificador del parametro 
	 * @return un <code>Parametro</code>
	 */

	public Parametro getParametro(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Parametro parametro where " +
				"parametro.ident =:ident");
		query.setParameter("ident",ident);
		Parametro parametro = (Parametro) query.uniqueResult();
		return parametro;
	}
	
	/**
	 * Regresa una Parametro
	 * @param code Identificador del parametro 
	 * @return un <code>Parametro</code>
	 */

	public Parametro getParametroByCode(String code) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Parametro parametro where " +
				"parametro.code =:code and parametro.pasive ='0'");
		query.setParameter("code",code);
		Parametro parametro = (Parametro) query.uniqueResult();
		return parametro;
	}
	
	
	/**
	 * Guarda una parametro
	 * @param parametro El parametro a guardar
	 * 
	 */
	public void saveParametro(Parametro parametro) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(parametro);
	}
	

}

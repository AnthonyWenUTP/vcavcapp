package org.clintonhealthaccess.vca.service.mtilds;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.mtilds.EvaluacionMosquitero;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Servicio para el objeto EvaluacionMosquitero
 * 
 * @author William Aviles
 * 
 **/

@Service("evaluacionMosquiteroService")
@Transactional
public class EvaluacionMosquiteroService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los metas
	 * 
	 * @return una lista de <code>EvaluacionMosquitero</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<EvaluacionMosquitero> getEvaluacionMosquiteros() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM EvaluacionMosquitero");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los evaluaciones activos
	 * 
	 * @return una lista de <code>EvaluacionMosquitero</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<EvaluacionMosquitero> getActiveEvaluacionMosquiteros() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM EvaluacionMosquitero eval where eval.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una EvaluacionMosquitero
	 * @param id Identificador del eval 
	 * @return un <code>EvaluacionMosquitero</code>
	 */

	public EvaluacionMosquitero getEvaluacion(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM EvaluacionMosquitero eval where " +
				"eval.ident =:ident");
		query.setParameter("ident",ident);
		EvaluacionMosquitero eval = (EvaluacionMosquitero) query.uniqueResult();
		return eval;
	}
	
	/**
	 * Regresa una EvaluacionMosquitero
	 * @param id Identificador del eval 
	 * @return un <code>EvaluacionMosquitero</code>
	 */

	public EvaluacionMosquitero getEvaluacion(String ident, String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM EvaluacionMosquitero eval where " +
				"eval.ident =:ident and eval.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("ident",ident);
		query.setParameter("username",username);
		EvaluacionMosquitero eval = (EvaluacionMosquitero) query.uniqueResult();
		return eval;
	}
	
	
	/**
	 * Guarda un eval
	 * @param eval 
	 * 
	 */
	public void saveEvaluacionMosquitero(EvaluacionMosquitero eval) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(eval);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<EvaluacionMosquitero> getEvaluacionMosquiterosFiltro(String codeMeta, String ownerName,
			Long desde, Long hasta, String local, String llinSeason, String llinStatus, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from EntregaTarget tar where tar.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		// if not null set time parameters
		if(!(desde==null)) {
			sqlQuery = sqlQuery + " and tar.lastModified between :fechaInicio and :fechaFinal";
		}
		if (!(codeMeta==null)) {
			sqlQuery = sqlQuery + " and tar.household.code like:codeMeta";
		}
		if (!(ownerName==null)) {
			sqlQuery = sqlQuery + " and tar.household.ownerName like:ownerName";
		}
		if(!local.equals("ALL")) {
			sqlQuery = sqlQuery + " and tar.household.local.ident=:local";
		}
		if(!llinSeason.equals("ALL")) {
			sqlQuery = sqlQuery + " and tar.ciclo.ident=:llinSeason";
		}
		if(!llinStatus.equals("ALL")) {
			sqlQuery = sqlQuery + " and tar.status=:llinStatus";
		}
		
		if(!(pasivo==null)) {
			sqlQuery = sqlQuery + " and tar.pasive=:pasivo";
		}
		
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		if(!(desde==null)) {
			Timestamp timeStampInicio = new Timestamp(desde);
			Timestamp timeStampFinal = new Timestamp(hasta);
			query.setTimestamp("fechaInicio", timeStampInicio);
			query.setTimestamp("fechaFinal", timeStampFinal);
		}
		if (!(codeMeta==null)) {
			query.setParameter("codeMeta", "%" + codeMeta + "%");
		}
		if (!(ownerName==null)) {
			query.setParameter("ownerName", "%" + ownerName + "%");
		}
		if(!local.equals("ALL")) {
			query.setParameter("local", local);
		}
		if(!llinSeason.equals("ALL")) {
			query.setParameter("llinSeason", llinSeason);
		}
		if(!llinStatus.equals("ALL")) {
			query.setParameter("llinStatus", llinStatus);
		}
		if(!(pasivo==null)) {
			query.setParameter("pasivo", pasivo.charAt(0));
		}
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<EvaluacionMosquitero> getEvaluacionMosquiterosMovil(String username) {
		// Retrieve session from Hibernate
		
		//Set the SQL Query initially
		String sqlQuery = "from EvaluacionMosquitero eval where eval.pasive ='0' "
				+ "and eval.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') "
				+ "and eval.target.ciclo.pasive ='0'";
				
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery);
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	

}

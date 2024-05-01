package org.clintonhealthaccess.vca.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.OldHousehold;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * Servicio para el objeto OldHousehold
 * 
 * @author William Aviles
 * 
 **/

@Service("oldHouseholdService")
@Transactional
public class OldHouseholdService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los viviendas
	 * 
	 * @return una lista de <code>OldHousehold</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<OldHousehold> getViviendas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM OldHousehold");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los viviendas activos
	 * 
	 * @return una lista de <code>OldHousehold</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<OldHousehold> getActiveViviendas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM OldHousehold house where house.pasive ='0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa una OldHousehold
	 * @param id Identificador del household 
	 * @return un <code>OldHousehold</code>
	 */

	public OldHousehold getVivienda(String ident) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM OldHousehold house where " +
				"house.ident =:ident");
		query.setParameter("ident",ident);
		OldHousehold district = (OldHousehold) query.uniqueResult();
		return district;
	}
	
	/**
	 * Regresa una OldHousehold
	 * @param id Identificador del household 
	 * @return un <code>OldHousehold</code>
	 */

	public OldHousehold getVivienda(String ident, String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM OldHousehold house where " +
				"house.ident =:ident and house.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')");
		query.setParameter("ident",ident);
		query.setParameter("username",username);
		OldHousehold district = (OldHousehold) query.uniqueResult();
		return district;
	}
	
	
	/**
	 * Guarda un household
	 * @param household 
	 * 
	 */
	public void saveVivienda(OldHousehold household) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(household);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<OldHousehold> getHousesFiltro(String codeHouse, String ownerName,
			Long desde, Long hasta, String local, String censusTaker, String recordUser, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from OldHousehold viv where viv.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		// if not null set time parameters
		if(!(desde==null)) {
			sqlQuery = sqlQuery + " and viv.censusDate between :fechaInicio and :fechaFinal";
		}
		if (!(codeHouse==null)) {
			sqlQuery = sqlQuery + " and viv.code like:codeHouse";
		}
		if (!(ownerName==null)) {
			sqlQuery = sqlQuery + " and viv.ownerName like:ownerName";
		}
		if(!local.equals("ALL")) {
			sqlQuery = sqlQuery + " and viv.local.ident=:local";
		}
		if(!censusTaker.equals("ALL")) {
			sqlQuery = sqlQuery + " and viv.censusTaker.ident=:censusTaker";
		}
		if(!recordUser.equals("ALL")) {
			sqlQuery = sqlQuery + " and viv.recordUser=:recordUser";
		}
		
		if(!(pasivo==null)) {
			sqlQuery = sqlQuery + " and viv.pasive=:pasivo";
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
		if (!(codeHouse==null)) {
			query.setParameter("codeHouse", "%" + codeHouse + "%");
		}
		if (!(ownerName==null)) {
			query.setParameter("ownerName", "%" + ownerName + "%");
		}
		if(!local.equals("ALL")) {
			query.setParameter("local", local);
		}
		if(!censusTaker.equals("ALL")) {
			query.setParameter("censusTaker", censusTaker);
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
	public List<OldHousehold> getHousesMosquiteros(String codeHouse, String ownerName,
			Long desde, Long hasta, String local, String censusTaker, String recordUser, String username, String pasivo) {
		//Set the SQL Query initially
		String sqlQuery = "from OldHousehold viv where viv.local.ident in "
				+ "(Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') "
				+ "and viv.ident in (select tar.household.ident FROM EntregaTarget tar where tar.ciclo.pasive ='0')";
		// if not null set time parameters
		if(!(desde==null)) {
			sqlQuery = sqlQuery + " and viv.censusDate between :fechaInicio and :fechaFinal";
		}
		if (!(codeHouse==null)) {
			sqlQuery = sqlQuery + " and viv.code like:codeHouse";
		}
		if (!(ownerName==null)) {
			sqlQuery = sqlQuery + " and viv.ownerName like:ownerName";
		}
		if(!local.equals("ALL")) {
			sqlQuery = sqlQuery + " and viv.local.ident=:local";
		}
		if(!censusTaker.equals("ALL")) {
			sqlQuery = sqlQuery + " and viv.censusTaker.ident=:censusTaker";
		}
		if(!recordUser.equals("ALL")) {
			sqlQuery = sqlQuery + " and viv.recordUser=:recordUser";
		}
		
		if(!(pasivo==null)) {
			sqlQuery = sqlQuery + " and viv.pasive=:pasivo";
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
		if (!(codeHouse==null)) {
			query.setParameter("codeHouse", "%" + codeHouse + "%");
		}
		if (!(ownerName==null)) {
			query.setParameter("ownerName", "%" + ownerName + "%");
		}
		if(!local.equals("ALL")) {
			query.setParameter("local", local);
		}
		if(!censusTaker.equals("ALL")) {
			query.setParameter("censusTaker", censusTaker);
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

package org.clintonhealthaccess.vca.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Caso;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para el dashboard de censo
 * 
 * @author William Aviles
 * 
 **/

@Service("dashboardMap1Service")
@Transactional
public class DashboardMap1Service {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa datos de casos por fecha
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosCasosxFecha(String area, String district, String foci, String localidad,Long desde, Long hasta,String tiempo, String username, String estado) {
		// Retrieve session from Hibernate
		String sqlQueryTiempo = "";
		String sqlQueryEnd = "";
		if(tiempo.equals("Dia")) {
			sqlQueryTiempo = "SELECT (caso.mxDate) as fecha, year(caso.mxDate) as anio, ";
			sqlQueryEnd = " GROUP BY (caso.mxDate), year(caso.mxDate) order by (caso.mxDate)";
		}else if(tiempo.equals("Semana")) {
			sqlQueryTiempo = "SELECT year(caso.mxDate) as anio , week(caso.mxDate) as semana, ";
			sqlQueryEnd = " GROUP BY year(caso.mxDate), week(caso.mxDate) order by year(caso.mxDate), week(caso.mxDate)";
		}
		else if(tiempo.equals("Mes")) {
			sqlQueryTiempo = "SELECT year(caso.mxDate) as anio , month(caso.mxDate) as mes, ";
			sqlQueryEnd = " GROUP BY year(caso.mxDate) , month(caso.mxDate) order by month(caso.mxDate)";
		}
		
		
		String sqlQueryStart = " COUNT(caso.ident) AS Total,  SUM( CASE inv WHEN '1' THEN 1 ELSE 0 END ) as Investigados,  SUM( CASE tx WHEN '1' THEN 1 ELSE 0 END ) as TxIni"
				+ ",  SUM( CASE txComp WHEN '1' THEN 1 ELSE 0 END ) as TxComp,  SUM( CASE sx WHEN '1' THEN 1 ELSE 0 END ) as seg2sem,  SUM( CASE sxComp WHEN '1' THEN 1 ELSE 0 END ) as seg4sem"
				+ ",  SUM( CASE WHEN txSup is not 'No' THEN 1 ELSE 0 END ) as TxSup,  SUM( CASE lostFollowUp WHEN '1' THEN 1 ELSE 0 END ) as lostFollowUp";
		String sqlQueryFilter = " from Caso caso where caso.pasive='0' and caso.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.ident=:localidad";
		}
		if(!estado.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.estadocaso=:estado";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and caso.mxDate between :fechaInicio and :fechaFinal";
		}
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQueryTiempo +sqlQueryStart + sqlQueryFilter + sqlQueryEnd);
		query.setParameter("username",username);
		if(!area.equals("ALL")) {
			query.setParameter("area", area);
		}
		if(!district.equals("ALL")) {
			query.setParameter("district", district);
		}
		if(!foci.equals("ALL")) {
			query.setParameter("foci", foci);
		}
		if(!localidad.equals("ALL")) {
			query.setParameter("localidad", localidad);
		}
		if(!estado.equals("ALL")) {
			query.setParameter("estado", estado);
		}
		if(!(desde==null)) {
			Timestamp timeStampInicio = new Timestamp(desde);
			Timestamp timeStampFinal = new Timestamp(hasta);
			query.setTimestamp("fechaInicio", timeStampInicio);
			query.setTimestamp("fechaFinal", timeStampFinal);
		}
		// Retrieve all
		return  query.list();
	}
	

	/**
	 * Regresa datos de casos por OU
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosCasosxOU(String area, String district, String foci, String localidad, Long desde, Long hasta, String tipoOU, String username, String estado) {
		// Retrieve session from Hibernate
		String sqlQueryTipoOU = "";
		String sqlQueryEnd = "";
		if(tipoOU.equals("LOCAL")) {
			sqlQueryTipoOU = "SELECT (caso.local.name) as localidad, ";
			sqlQueryEnd = " GROUP BY (caso.local.name)";
		}else if(tipoOU.equals("AREA")) {
			sqlQueryTipoOU = "SELECT (caso.local.district.area.name) as area, ";
			sqlQueryEnd = " GROUP BY (caso.local.district.area.name)";
		}
		else if(tipoOU.equals("DISTR")) {
			sqlQueryTipoOU = "SELECT (caso.local.district.name) as district, ";
			sqlQueryEnd = " GROUP BY (caso.local.district.name)";
		}
		
		String sqlQueryStart = " COUNT(caso.ident) AS Total, "
				+ "SUM( CASE inv WHEN '1' THEN 1 ELSE 0 END ) AS Investigados, SUM( CASE tx WHEN '1' THEN 1 ELSE 0 END ) AS IniTrat"
				+ ",  SUM( CASE txComp WHEN '1' THEN 1 ELSE 0 END ) as TxComp,  SUM( CASE sx WHEN '1' THEN 1 ELSE 0 END ) as seg2sem,  SUM( CASE sxComp WHEN '1' THEN 1 ELSE 0 END ) as seg4sem "
				+ ",  SUM( CASE WHEN txSup is not 'No' THEN 1 ELSE 0 END ) as TxSup,  SUM( CASE lostFollowUp WHEN '1' THEN 1 ELSE 0 END ) as lostFollowUp";
		String sqlQueryFilter = " from Caso caso where caso.pasive = '0' and caso.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.ident=:localidad";
		}
		if(!estado.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.estadocaso=:estado";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and caso.mxDate between :fechaInicio and :fechaFinal";
		}
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQueryTipoOU + sqlQueryStart + sqlQueryFilter + sqlQueryEnd);
		query.setParameter("username",username);
		if(!area.equals("ALL")) {
			query.setParameter("area", area);
		}
		if(!district.equals("ALL")) {
			query.setParameter("district", district);
		}
		if(!foci.equals("ALL")) {
			query.setParameter("foci", foci);
		}
		if(!localidad.equals("ALL")) {
			query.setParameter("localidad", localidad);
		}
		if(!estado.equals("ALL")) {
			query.setParameter("estado", estado);
		}
		if(!(desde==null)) {
			Timestamp timeStampInicio = new Timestamp(desde);
			Timestamp timeStampFinal = new Timestamp(hasta);
			query.setTimestamp("fechaInicio", timeStampInicio);
			query.setTimestamp("fechaFinal", timeStampFinal);
		}
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa datos de casos por Estado
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosCasosxEstado(String area, String district, String foci, String localidad, Long desde, Long hasta, String tipoOU, String username) {
		// Retrieve session from Hibernate
		String sqlQueryTipoOU = "";
		String sqlQueryEnd = "";
		if(tipoOU.equals("LOCAL")) {
			sqlQueryTipoOU = "SELECT (caso.local.name) as localidad, ";
			sqlQueryEnd = " GROUP BY (caso.local.name)";
		}else if(tipoOU.equals("AREA")) {
			sqlQueryTipoOU = "SELECT (caso.local.district.area.name) as area, ";
			sqlQueryEnd = " GROUP BY (caso.local.district.area.name)";
		}
		else if(tipoOU.equals("DISTR")) {
			sqlQueryTipoOU = "SELECT (caso.local.district.name) as district, ";
			sqlQueryEnd = " GROUP BY (caso.local.district.name)";
		}
		
		String sqlQueryStart = " COUNT(caso.ident) AS Total, "
				+ "SUM( CASE estadocaso WHEN 'CONF' THEN 1 ELSE 0 END ) AS Positivos, SUM( CASE estadocaso WHEN 'TRAT' THEN 1 ELSE 0 END ) AS EnTratamiento "
				+ ", SUM( CASE estadocaso WHEN 'TRATC' THEN 1 ELSE 0 END ) AS TratamientoComp, SUM( CASE estadocaso WHEN 'SEG2' THEN 1 ELSE 0 END ) AS seg2sem, SUM( CASE estadocaso WHEN 'SEG4' THEN 1 ELSE 0 END ) AS seg4sem "
				+ ", SUM( CASE estadocaso WHEN 'SEGPOS' THEN 1 ELSE 0 END ) AS nocurado, SUM( CASE estadocaso WHEN 'SEGINC' THEN 1 ELSE 0 END ) AS lostfollowup ";
		String sqlQueryFilter = " from Caso caso where caso.pasive = '0' and caso.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.ident=:localidad";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and caso.mxDate between :fechaInicio and :fechaFinal";
		}
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQueryTipoOU + sqlQueryStart + sqlQueryFilter + sqlQueryEnd);
		query.setParameter("username",username);
		if(!area.equals("ALL")) {
			query.setParameter("area", area);
		}
		if(!district.equals("ALL")) {
			query.setParameter("district", district);
		}
		if(!foci.equals("ALL")) {
			query.setParameter("foci", foci);
		}
		if(!localidad.equals("ALL")) {
			query.setParameter("localidad", localidad);
		}
		if(!(desde==null)) {
			Timestamp timeStampInicio = new Timestamp(desde);
			Timestamp timeStampFinal = new Timestamp(hasta);
			query.setTimestamp("fechaInicio", timeStampInicio);
			query.setTimestamp("fechaFinal", timeStampFinal);
		}
		// Retrieve all
		return  query.list();
	}
	
	

	/**
	 * Regresa datos de casos
	 * 
	 * @return lista de casos
	 */
	@SuppressWarnings("unchecked")
	public List<Caso> getDatosCasosxUbi(String area, String district, String foci, String localidad,Long desde, Long hasta, String username, List<String> estadocaso) {
		// Retrieve session from Hibernate
		
		String sqlQueryFilter = "from Caso caso where caso.pasive = '0' and caso.latitude is not null and caso.longitude is not null and caso.latitude <> 0 and caso.longitude <> 0 and "
				+ "caso.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') and "
				+ "caso.estadocaso in :estadocaso ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and caso.local.ident=:localidad";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and caso.mxDate between :fechaInicio and :fechaFinal";
		}
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQueryFilter );
		query.setParameter("username",username);
		query.setParameterList("estadocaso",estadocaso);
		if(!area.equals("ALL")) {
			query.setParameter("area", area);
		}
		if(!district.equals("ALL")) {
			query.setParameter("district", district);
		}
		if(!foci.equals("ALL")) {
			query.setParameter("foci", foci);
		}
		if(!localidad.equals("ALL")) {
			query.setParameter("localidad", localidad);
		}
		if(!(desde==null)) {
			Timestamp timeStampInicio = new Timestamp(desde);
			Timestamp timeStampFinal = new Timestamp(hasta);
			query.setTimestamp("fechaInicio", timeStampInicio);
			query.setTimestamp("fechaFinal", timeStampFinal);
		}
		// Retrieve all
		return  query.list();
	}
	
	
}

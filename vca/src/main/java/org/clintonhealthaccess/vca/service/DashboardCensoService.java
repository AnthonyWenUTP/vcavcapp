package org.clintonhealthaccess.vca.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Household;
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

@Service("dashboardCensoService")
@Transactional
public class DashboardCensoService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa datos de vivienda por fecha
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosHouseholdxFecha(String area, String district, String foci, String localidad, String censustaker,Long desde, Long hasta,String tiempo, String username) {
		// Retrieve session from Hibernate
		String sqlQueryTiempo = "";
		String sqlQueryEnd = "";
		if(tiempo.equals("Dia")) {
			sqlQueryTiempo = "SELECT (viv.censusDate) as fecha, year(viv.censusDate) as anio, ";
			sqlQueryEnd = " GROUP BY (viv.censusDate), year(viv.censusDate) order by (viv.censusDate)";
		}else if(tiempo.equals("Semana")) {
			sqlQueryTiempo = "SELECT year(viv.censusDate) as anio , week(viv.censusDate) as semana, ";
			sqlQueryEnd = " GROUP BY year(viv.censusDate), week(viv.censusDate) order by year(viv.censusDate), week(viv.censusDate)";
		}
		else if(tiempo.equals("Mes")) {
			sqlQueryTiempo = "SELECT year(viv.censusDate) as anio , month(viv.censusDate) as mes, ";
			sqlQueryEnd = " GROUP BY year(viv.censusDate) , month(viv.censusDate) order by month(viv.censusDate)";
		}
		
		
		String sqlQueryStart = " COUNT(viv.ident) AS Total, "
				+ "SUM( CASE viv.pasive WHEN '1' THEN 1 ELSE 0 END ) AS Invalidas, "
				+ "SUM( CASE viv.pasive WHEN '0' THEN 1 ELSE 0 END ) AS Validas";
		String sqlQueryFilter = " from Household viv where viv.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.ident=:localidad";
		}
		if(!censustaker.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.censusTaker.ident=:censustaker";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and viv.censusDate between :fechaInicio and :fechaFinal";
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
		if(!censustaker.equals("ALL")) {
			query.setParameter("censustaker", censustaker);
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
	 * Regresa datos de vivienda por OU
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosHouseholdxOU(String area, String district, String foci, String localidad, String censustaker,Long desde, Long hasta, String tipoOU, String username) {
		// Retrieve session from Hibernate
		String sqlQueryTipoOU = "";
		String sqlQueryEnd = "";
		if(tipoOU.equals("LOCAL")) {
			sqlQueryTipoOU = "SELECT (viv.local.name) as localidad, ";
			sqlQueryEnd = " GROUP BY (viv.local.name)";
		}else if(tipoOU.equals("AREA")) {
			sqlQueryTipoOU = "SELECT (viv.local.district.area.name) as area, ";
			sqlQueryEnd = " GROUP BY (viv.local.district.area.name)";
		}
		else if(tipoOU.equals("DISTR")) {
			sqlQueryTipoOU = "SELECT (viv.local.district.name) as district, ";
			sqlQueryEnd = " GROUP BY (viv.local.district.name)";
		}
		else if(tipoOU.equals("CENS")) {
			sqlQueryTipoOU = "SELECT (viv.censusTaker.name) as censador, ";
			sqlQueryEnd = " GROUP BY (viv.censusTaker.name)";
		}
		
		String sqlQueryStart = " COUNT(viv.ident) AS Total, SUM( CASE inhabited WHEN '1' THEN 1 ELSE 0 END ) AS Habitadas, SUM( CASE inhabited WHEN '1' THEN 1 ELSE 0 END )/COUNT(viv.ident)*100 AS PorcHab, "
				+ "SUM(rooms) AS cuartos, SUM(sprRooms) AS rociables, (SUM(sprRooms)/SUM(rooms))*100 AS PorcRoc, SUM(habitants) AS habitantes, SUM( CASE inhabited WHEN '0' THEN 1 ELSE 0 END ) AS NoHabitadas,"
				+ "SUM( CASE inhabited WHEN '9' THEN 1 ELSE 0 END ) AS Cerradas, SUM(personasCharlas) AS charlas, SUM( CASE inhabited WHEN '8' THEN 1 ELSE 0 END ) AS Renuentes ";
		String sqlQueryFilter = " from Household viv where viv.pasive = '0' and viv.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.ident=:localidad";
		}
		if(!censustaker.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.censusTaker.ident=:censustaker";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and viv.censusDate between :fechaInicio and :fechaFinal";
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
		if(!censustaker.equals("ALL")) {
			query.setParameter("censustaker", censustaker);
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
	 * Regresa datos de vivienda
	 * 
	 * @return lista de viviendas
	 */
	@SuppressWarnings("unchecked")
	public List<Household> getDatosHouseholdxUbi(String area, String district, String foci, String localidad, String censustaker,Long desde, Long hasta, String username) {
		// Retrieve session from Hibernate
		
		String sqlQueryFilter = "from Household viv where viv.latitude is not null and viv.longitude is not null and viv.latitude <> 0 and viv.longitude <> 0 and "
				+ "viv.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.ident=:localidad";
		}
		if(!censustaker.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.censusTaker.ident=:censustaker";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and viv.censusDate between :fechaInicio and :fechaFinal";
		}
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQueryFilter );
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
		if(!censustaker.equals("ALL")) {
			query.setParameter("censustaker", censustaker);
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
	 * Regresa datos de vivienda por Mat
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getDatosHouseholdxMat(String area, String district, String foci, String localidad, String censustaker,Long desde, Long hasta, String username) {
		// Retrieve session from Hibernate

		
		String sqlQueryStart = "Select viv.material, COUNT(viv.ident) AS Total";
		String sqlQueryFilter = " from Household viv where viv.pasive = '0' and viv.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')";
		String sqlQueryEnd = " Group by viv.material";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.local.ident=:localidad";
		}
		if(!censustaker.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.censusTaker.ident=:censustaker";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and viv.censusDate between :fechaInicio and :fechaFinal";
		}
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQueryStart + sqlQueryFilter + sqlQueryEnd);
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
		if(!censustaker.equals("ALL")) {
			query.setParameter("censustaker", censustaker);
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

package org.clintonhealthaccess.vca.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.irs.Target;
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

@Service("dashboardIrsService")
@Transactional
public class DashboardIrsService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa datos de las metas
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosMetas(String area, String district, String foci, String localidad, String temporada, String username) {
		// Retrieve session from Hibernate
		String sqlQuery = "";
		String sqlQueryEnd = "";	
		sqlQuery = "SELECT ";
		sqlQueryEnd = " ";
		String sqlQueryStart = " COUNT(tar.sprayStatus) AS Total, "
				+ "SUM( CASE tar.sprayStatus WHEN 'NOTVIS' THEN 1 ELSE 0 END ) AS SinVisita, "
				+ "SUM( CASE tar.sprayStatus WHEN 'DROPPED' THEN 1 ELSE 0 END ) AS Descartadas, "
				+ "SUM( CASE tar.sprayStatus WHEN 'PENDING' THEN 1 ELSE 0 END ) AS Notificadas, "
				+ "SUM( CASE tar.sprayStatus WHEN 'CLOSED' THEN 1 ELSE 0 END ) AS Cerradas, "
				+ "SUM( CASE tar.sprayStatus WHEN 'RELUCT' THEN 1 ELSE 0 END ) AS Renuentes, "
				+ "SUM( CASE tar.sprayStatus WHEN 'SPRPAR' THEN 1 ELSE 0 END ) AS Parciales, "
				+ "SUM( CASE tar.sprayStatus WHEN 'SPRTOT' THEN 1 ELSE 0 END ) AS Totales, "
				+ "SUM( tar.household.sprRooms ) AS TotalCuartos, "
				+ "SUM( tar.household.habitants ) AS TotalHabitantes ";
		String sqlQueryFilter = " from Target tar where tar.pasive='0' and tar.irsSeason.ident=:temporada and tar.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and tar.household.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and tar.household.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and tar.household.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and tar.household.local.ident=:localidad";
		}
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery(sqlQuery +sqlQueryStart + sqlQueryFilter + sqlQueryEnd);
		query.setParameter("username",username);
		query.setParameter("temporada",temporada);
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
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa datos de visitas por fecha
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosVisitasxFecha(String area, String district, String foci, String localidad, String temporada,String rociador, String supervisor, String brigada,Long desde, Long hasta,String tiempo, String username) {
		// Retrieve session from Hibernate
		String sqlQueryTiempo = "";
		String sqlQueryEnd = "";
		if(tiempo.equals("Dia")) {
			sqlQueryTiempo = "SELECT (vis.visitDate) as fecha, year(vis.visitDate) as anio, ";
			sqlQueryEnd = " GROUP BY (vis.visitDate), year(vis.visitDate) order by (vis.visitDate)";
		}else if(tiempo.equals("Semana")) {
			sqlQueryTiempo = "SELECT year(vis.visitDate) as anio , week(vis.visitDate) as semana, ";
			sqlQueryEnd = " GROUP BY year(vis.visitDate), week(vis.visitDate) order by year(vis.visitDate), week(vis.visitDate)";
		}
		else if(tiempo.equals("Mes")) {
			sqlQueryTiempo = "SELECT year(vis.visitDate) as anio , month(vis.visitDate) as mes, ";
			sqlQueryEnd = " GROUP BY year(vis.visitDate) , month(vis.visitDate) order by month(vis.visitDate)";
		}
		
		
		String sqlQueryStart = " COUNT(vis.ident) AS Total, "
				+ "SUM( CASE vis.visit WHEN '1' THEN 1 ELSE 0 END ) AS Iniciales, "
				+ "SUM( CASE vis.visit WHEN '2' THEN 1 ELSE 0 END ) AS Seguimiento, "
				+ "SUM( CASE vis.compVisit WHEN '1' THEN 1 ELSE 0 END ) AS Exitos, " 
				+ "SUM( CASE vis.compVisit WHEN '0' THEN 1 ELSE 0 END ) AS Fracasos, "
				+ "SUM( vis.sprayedRooms ) AS rociados, "
				+ "SUM( vis.numCharges ) AS cargas, "
				+ "SUM( vis.personasCharlas ) AS charlas, "
				+ "SUM( CASE vis.activity WHEN 'NOTICE' THEN 1 ELSE 0 END ) AS Preavisos, "
				+ "SUM( CASE vis.activity WHEN 'SPRAY' THEN 1 ELSE 0 END ) AS Rociados ";
		String sqlQueryFilter = " from Visit vis where vis.pasive='0' and vis.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident=:localidad";
		}
		if(!temporada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.irsSeason.ident=:temporada";
		}
		if(!rociador.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitor.ident=:rociador";
		}
		if(!supervisor.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.supervisor.ident=:supervisor";
		}
		if(!brigada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.brigada.ident=:brigada";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitDate between :fechaInicio and :fechaFinal";
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
		if(!temporada.equals("ALL")) {
			query.setParameter("temporada", temporada);
		}
		if(!rociador.equals("ALL")) {
			query.setParameter("rociador", rociador);
		}
		if(!supervisor.equals("ALL")) {
			query.setParameter("supervisor", supervisor);
		}
		if(!brigada.equals("ALL")) {
			query.setParameter("brigada", brigada);
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
	 * Regresa datos de visitas por OU
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosVisitasxOU(String area, String district, String foci, String localidad, String temporada, 
			String rociador, String supervisor, String brigada,Long desde, Long hasta,String tipoOU, String username) {
		// Retrieve session from Hibernate
		String sqlQueryTipoOU = "";
		String sqlQueryEnd = "";
		if(tipoOU.equals("LOCAL")) {
			sqlQueryTipoOU = "SELECT (vis.target.household.local.name) as localidad, ";
			sqlQueryEnd = " GROUP BY (vis.target.household.local.name)";
		}else if(tipoOU.equals("AREA")) {
			sqlQueryTipoOU = "SELECT (vis.target.household.local.district.area.name) as area, ";
			sqlQueryEnd = " GROUP BY (vis.target.household.local.district.area.name)";
		}
		else if(tipoOU.equals("DISTR")) {
			sqlQueryTipoOU = "SELECT (vis.target.household.local.district.name) as district, ";
			sqlQueryEnd = " GROUP BY (vis.target.household.local.district.name)";
		}
		else if(tipoOU.equals("ROC")) {
			sqlQueryTipoOU = "SELECT (vis.visitor.name) as rociador, ";
			sqlQueryEnd = " GROUP BY (vis.visitor.name)";
		}
		else if(tipoOU.equals("SUP")) {
			sqlQueryTipoOU = "SELECT (vis.supervisor.name) as supervisor, ";
			sqlQueryEnd = " GROUP BY (vis.supervisor.name)";
		}
		else if(tipoOU.equals("BRI")) {
			sqlQueryTipoOU = "SELECT (vis.brigada.name) as brigada, ";
			sqlQueryEnd = " GROUP BY (vis.brigada.name)";
		}
		
		
		String sqlQueryStart = " COUNT(vis.ident) AS Total, "
				+ "SUM( CASE vis.visit WHEN '1' THEN 1 ELSE 0 END ) AS Iniciales, "
				+ "SUM( CASE vis.visit WHEN '2' THEN 1 ELSE 0 END ) AS Seguimiento, "
				+ "SUM( CASE vis.compVisit WHEN '1' THEN 1 ELSE 0 END ) AS Exitos, " 
				+ "SUM( CASE vis.compVisit WHEN '0' THEN 1 ELSE 0 END ) AS Fracasos, "
				+ "SUM( vis.sprayedRooms ) AS rociados, "
				+ "SUM( vis.numCharges ) AS cargas, "
				+ "SUM( vis.personasCharlas ) AS charlas, "
				+ "SUM( CASE vis.activity WHEN 'NOTICE' THEN 1 ELSE 0 END ) AS Preavisos, "
				+ "SUM( CASE vis.activity WHEN 'SPRAY' THEN 1 ELSE 0 END ) AS Rociados ";
		String sqlQueryFilter = " from Visit vis where vis.pasive='0' and vis.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident=:localidad";
		}
		if(!temporada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.irsSeason.ident=:temporada";
		}
		if(!rociador.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitor.ident=:rociador";
		}
		if(!supervisor.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.supervisor.ident=:supervisor";
		}
		if(!brigada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.brigada.ident=:brigada";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitDate between :fechaInicio and :fechaFinal";
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
		if(!temporada.equals("ALL")) {
			query.setParameter("temporada", temporada);
		}
		if(!rociador.equals("ALL")) {
			query.setParameter("rociador", rociador);
		}
		if(!supervisor.equals("ALL")) {
			query.setParameter("supervisor", supervisor);
		}
		if(!brigada.equals("ALL")) {
			query.setParameter("brigada", brigada);
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
	 * Regresa datos de metas
	 * 
	 * @return lista de metas
	 */
	@SuppressWarnings("unchecked")
	public List<Target> getDatosTargetxUbi(String area, String district, String foci, String localidad, String temporada, String username) {
		// Retrieve session from Hibernate
		
		String sqlQueryFilter = "from Target viv where viv.household.latitude is not null and viv.household.longitude is not null and viv.household.latitude <> 0 and viv.household.longitude <> 0 and "
				+ "viv.pasive = '0' and viv.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.household.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.household.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.household.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.household.local.ident=:localidad";
		}
		if(!temporada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and viv.irsSeason.ident=:temporada";
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
		if(!temporada.equals("ALL")) {
			query.setParameter("temporada", temporada);
		}
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa datos de consumo por OU
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosConsumoxOU(String area, String district, String foci, String localidad, String temporada, 
			String rociador, String supervisor, String brigada,Long desde, Long hasta,String tipoOU, String username) {
		// Retrieve session from Hibernate
		String sqlQueryTipoOU = "";
		String sqlQueryEnd = "";
		if(tipoOU.equals("LOCAL")) {
			sqlQueryTipoOU = "SELECT vis.visitDate, (vis.target.household.local.name) as localidad, ";
			sqlQueryEnd = " GROUP BY vis.visitDate, vis.target.household.local.name";
		}else if(tipoOU.equals("AREA")) {
			sqlQueryTipoOU = "SELECT vis.visitDate,(vis.target.household.local.district.area.name) as area, ";
			sqlQueryEnd = " GROUP BY vis.visitDate, vis.target.household.local.district.area.name";
		}
		else if(tipoOU.equals("DISTR")) {
			sqlQueryTipoOU = "SELECT vis.visitDate,(vis.target.household.local.district.name) as district, ";
			sqlQueryEnd = " GROUP BY vis.visitDate, vis.target.household.local.district.name";
		}
		else if(tipoOU.equals("ROC")) {
			sqlQueryTipoOU = "SELECT vis.visitDate,(vis.visitor.name) as rociador, ";
			sqlQueryEnd = " GROUP BY vis.visitDate, vis.visitor.name";
		}
		else if(tipoOU.equals("SUP")) {
			sqlQueryTipoOU = "SELECT vis.visitDate,(vis.supervisor.name) as supervisor, ";
			sqlQueryEnd = " GROUP BY vis.visitDate, vis.supervisor.name";
		}
		else if(tipoOU.equals("BRI")) {
			sqlQueryTipoOU = "SELECT vis.visitDate,(vis.brigada.name) as brigada, ";
			sqlQueryEnd = " GROUP BY vis.visitDate, vis.brigada.name";
		}
		
		
		String sqlQueryStart = " COUNT(vis.ident) AS Casas, "
				+ "SUM( vis.sprayedRooms ) AS rociados, "
				+ "SUM( vis.numCharges ) AS cargas ";
		String sqlQueryFilter = " from Visit vis where vis.activity = 'SPRAY' and vis.compVisit = 1 and  vis.pasive='0' and vis.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident=:localidad";
		}
		if(!temporada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.irsSeason.ident=:temporada";
		}
		if(!rociador.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitor.ident=:rociador";
		}
		if(!supervisor.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.supervisor.ident=:supervisor";
		}
		if(!brigada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.brigada.ident=:brigada";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitDate between :fechaInicio and :fechaFinal";
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
		if(!temporada.equals("ALL")) {
			query.setParameter("temporada", temporada);
		}
		if(!rociador.equals("ALL")) {
			query.setParameter("rociador", rociador);
		}
		if(!supervisor.equals("ALL")) {
			query.setParameter("supervisor", supervisor);
		}
		if(!brigada.equals("ALL")) {
			query.setParameter("brigada", brigada);
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
	 * Regresa datos de consumo por OU
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosConsumoxOUxSemana(String area, String district, String foci, String localidad, String temporada, 
			String rociador, String supervisor, String brigada,Long desde, Long hasta,String tipoOU, String username) {
		// Retrieve session from Hibernate
		String sqlQueryTipoOU = "";
		String sqlQueryEnd = "";
		if(tipoOU.equals("LOCAL")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate), (vis.target.household.local.name) as localidad, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.target.household.local.name";
		}else if(tipoOU.equals("AREA")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.target.household.local.district.area.name) as area, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.target.household.local.district.area.name";
		}
		else if(tipoOU.equals("DISTR")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.target.household.local.district.name) as district, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.target.household.local.district.name";
		}
		else if(tipoOU.equals("ROC")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.visitor.name) as rociador, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.visitor.name";
		}
		else if(tipoOU.equals("SUP")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.supervisor.name) as supervisor, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.supervisor.name";
		}
		else if(tipoOU.equals("BRI")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.brigada.name) as brigada, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.brigada.name";
		}
		
		
		String sqlQueryStart = " COUNT(vis.ident) AS Casas, "
				+ "SUM( vis.sprayedRooms ) AS rociados, "
				+ "SUM( vis.numCharges ) AS cargas ";
		String sqlQueryFilter = " from Visit vis where vis.activity = 'SPRAY' and vis.compVisit = 1 and vis.pasive='0' and vis.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident=:localidad";
		}
		if(!temporada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.irsSeason.ident=:temporada";
		}
		if(!rociador.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitor.ident=:rociador";
		}
		if(!supervisor.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.supervisor.ident=:supervisor";
		}
		if(!brigada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.brigada.ident=:brigada";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitDate between :fechaInicio and :fechaFinal";
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
		if(!temporada.equals("ALL")) {
			query.setParameter("temporada", temporada);
		}
		if(!rociador.equals("ALL")) {
			query.setParameter("rociador", rociador);
		}
		if(!supervisor.equals("ALL")) {
			query.setParameter("supervisor", supervisor);
		}
		if(!brigada.equals("ALL")) {
			query.setParameter("brigada", brigada);
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
	 * Regresa datos de consumo por OU
	 * 
	 * @return lista de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getDatosConsumoxOUxSemanaTemp(String area, String district, String foci, String localidad, String temporada, 
			String rociador, String supervisor, String brigada,Long desde, Long hasta,String tipoOU, String username) {
		// Retrieve session from Hibernate
		
		
		/*String sqlQueryNueva = "SELECT week(vis.visitDate) "
				+ " from Visit vis where vis.activity = 'SPRAY' and vis.compVisit = 1 and vis.pasive='0' and "
				+ " vis.target.household.local.ident in "
				+ " (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc "
				+ " where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0')";*/
		
		
		
		
		
		String sqlQueryTipoOU = "";
		String sqlQueryEnd = "";
		if(tipoOU.equals("LOCAL")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate), (vis.target.household.local.name) as localidad, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.target.household.local.name";
		}else if(tipoOU.equals("AREA")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.target.household.local.district.area.name) as area, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.target.household.local.district.area.name";
		}
		else if(tipoOU.equals("DISTR")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.target.household.local.district.name) as district, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.target.household.local.district.name";
		}
		else if(tipoOU.equals("ROC")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.visitor.name) as rociador, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.visitor.name";
		}
		else if(tipoOU.equals("SUP")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.supervisor.name) as supervisor, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.supervisor.name";
		}
		else if(tipoOU.equals("BRI")) {
			sqlQueryTipoOU = "SELECT week(vis.visitDate),(vis.brigada.name) as brigada, ";
			sqlQueryEnd = " GROUP BY week(vis.visitDate), vis.brigada.name";
		}
		
		
		String sqlQueryStart = " COUNT(vis.ident) AS Casas, "
				+ "SUM( vis.sprayedRooms ) AS rociados, "
				+ "SUM( vis.numCharges ) AS cargas ";
		String sqlQueryFilter = " from Visit vis where vis.activity = 'SPRAY' and vis.compVisit = 1 and vis.pasive='0' and vis.target.household.local.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0') ";
		
		if(!area.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.area.ident=:area";
		}
		if(!district.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.district.ident=:district";
		}
		if(!foci.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident in (select fl.focoLocalidadId.localidad from FocoLocalidad fl where fl.focoLocalidadId.foco=:foci and fl.pasive = '0')";
		}
		if(!localidad.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.household.local.ident=:localidad";
		}
		if(!temporada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.target.irsSeason.ident=:temporada";
		}
		if(!rociador.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitor.ident=:rociador";
		}
		if(!supervisor.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.supervisor.ident=:supervisor";
		}
		if(!brigada.equals("ALL")) {
			sqlQueryFilter = sqlQueryFilter + " and vis.brigada.ident=:brigada";
		}
		if(!(desde==null)) {
			sqlQueryFilter = sqlQueryFilter + " and vis.visitDate between :fechaInicio and :fechaFinal";
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
		if(!temporada.equals("ALL")) {
			query.setParameter("temporada", temporada);
		}
		if(!rociador.equals("ALL")) {
			query.setParameter("rociador", rociador);
		}
		if(!supervisor.equals("ALL")) {
			query.setParameter("supervisor", supervisor);
		}
		if(!brigada.equals("ALL")) {
			query.setParameter("brigada", brigada);
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

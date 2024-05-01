package org.clintonhealthaccess.vca.users.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.users.model.UserAccess;
import org.clintonhealthaccess.vca.users.model.UserAttempts;
import org.clintonhealthaccess.vca.users.model.UserSistema;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.transaction.annotation.Transactional;
/**
 * Servicio que provee guardar las transacciones de usuarios en la base de datos
 * 
 * @author William Aviles
 **/
@Transactional
public class UserDetailsDaoImpl implements UserDetailsDao {
	
	private static final int MAX_ATTEMPTS = 5;
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void updateFailAttempts(String username) {
		UserAttempts user = getUserAttempts(username);
		if (user == null) {
			if (isUserExists(username)) {
				// if no record, insert a new
				Session session = sessionFactory.getCurrentSession();
				session.save(new UserAttempts(username, 1, new Date()));
			}
		} else {
			if (isUserExists(username)) {
				Session session = sessionFactory.getCurrentSession();
				user.setAttempts(user.getAttempts()+1);
				user.setLastModified(new Date());
				session.update(user);
			}
			if(user.getAttempts()>= MAX_ATTEMPTS){
				Session session = sessionFactory.getCurrentSession();
				Query query = session.createQuery("update UserSistema set accountNonLocked = :nobloqueado" +
						" where username = :username");
				query.setParameter("nobloqueado", false);
				query.setParameter("username", username);
				query.executeUpdate();
			}
		}
	}

	@Override
	public void resetFailAttempts(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update UserAttempts set attempts = :intentos" +
				" where username = :username");
		query.setParameter("intentos", 0);
		query.setParameter("username", username);
		query.executeUpdate();
	}

	@Override
	public UserAttempts getUserAttempts(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserAttempts u where " +
				"u.username = '" + username + "'");
		UserAttempts userAttemps = (UserAttempts) query.uniqueResult();
		return userAttemps;
	}
	
	private boolean isUserExists(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserSistema u where " +
				"u.username = '" + username + "'");
		UserSistema user = (UserSistema) query.uniqueResult();
		if (user!=null){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public void insertNewAccess(Authentication auth) {
		Session session = sessionFactory.getCurrentSession();
		WebAuthenticationDetails wad  = (WebAuthenticationDetails) auth.getDetails();
		Date fechaAcceso = new Date();
		Query query = session.createQuery("From UserSistema where username = :username");
		query.setParameter("username", auth.getName());
		UserSistema usuario = (UserSistema) query.uniqueResult();
		query = session.createQuery("update UserSistema set lastAccess = :fechaIngreso" +
				" where username = :username");
		query.setParameter("fechaIngreso", fechaAcceso);
		query.setParameter("username", auth.getName());
		query.executeUpdate();
		session.save(new UserAccess(usuario,fechaAcceso, wad.getSessionId(), wad.getRemoteAddress()));
	}
	
	@Override
	public void updateAccessDateLogout(String username, String idSesion, String direccionIp) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update UserAccess set logoutDate = :fechaSalida" +
				" where usuario.username = :username and sessionId = :idSesion and remoteIpAddress = :direccionIp");
		query.setParameter("fechaSalida", new Date());
		query.setParameter("username", username);
		query.setParameter("idSesion", idSesion);
		query.setParameter("direccionIp", direccionIp);
		query.executeUpdate();
	}
	
	@Override
	public void updateAccessUrl(String username, String idSesion, String direccionIp, String url) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update UserAccess set logoutRefererUrl = :urlSalida" +
				" where usuario.username = :username and sessionId = :idSesion and remoteIpAddress = :direccionIp");
		query.setParameter("urlSalida", url);
		query.setParameter("username", username);
		query.setParameter("idSesion", idSesion);
		query.setParameter("direccionIp", direccionIp);
		query.executeUpdate();
		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void checkCredentialsDate(String username) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserSistema u where " +
				"u.username = '" + username + "'");
		UserSistema user = (UserSistema) query.uniqueResult();
		DateTime fecUltCambio = new DateTime(user.getLastCredentialChange());
    	DateTime fecHoy = new DateTime();
    	Days diff = Days.daysBetween(fecUltCambio, fecHoy);
		if (diff.getDays() >= 180){
			query = session.createQuery("update UserSistema set credentialsNonExpired = :nobloqueado" +
					" where username = :username");
			query.setParameter("nobloqueado", false);
			query.setParameter("username", username);
			query.executeUpdate();
		}
	}
}
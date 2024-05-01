package org.clintonhealthaccess.vca.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.relationships.UsuarioLocalidad;
import org.clintonhealthaccess.vca.users.model.Authority;
import org.clintonhealthaccess.vca.users.model.PasswordResetToken;
import org.clintonhealthaccess.vca.users.model.Rol;
import org.clintonhealthaccess.vca.users.model.UserAccess;
import org.clintonhealthaccess.vca.users.model.UserSistema;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Servicio para el objeto UserSistema
 * 
 * @author William Aviles
 * 
 **/

@Service("usuarioService")
@Transactional
public class UsuarioService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los usuarios
	 * 
	 * @return una lista de <code>UserSistema</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<UserSistema> getUsers() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM UserSistema");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los usuarios activos
	 * 
	 * @return una lista de <code>UserSistema</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<UserSistema> getActiveUsers() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM UserSistema us where us.enabled is true");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa un Usuario
	 * @param username Nombre del usuario. 
	 * @return un <code>UserSistema</code>
	 */

	public UserSistema getUser(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserSistema u where " +
				"u.username =:username");
		query.setParameter("username",username);
		UserSistema user = (UserSistema) query.uniqueResult();
		return user;
	}
	
	/**
	 * Regresa un Usuario
	 * @param username Nombre del usuario. 
	 * @param email Email del usuario. 
	 * @return un <code>UserSistema</code>
	 */

	public UserSistema getUser(String username,String email) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserSistema u where " +
				"u.username =:username and u.email =:email and u.enabled is true");
		query.setParameter("username",username);
		query.setParameter("email",email);
		UserSistema user = (UserSistema) query.uniqueResult();
		return user;
	}
	
	
	public String validatePasswordResetToken(String id, String token) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM PasswordResetToken token where " +
				"token.token =:token");
		query.setParameter("token",token);
	    PasswordResetToken passToken = (PasswordResetToken) query.uniqueResult();
	    if ((passToken == null) || !(passToken.getUser()
	        .getUsername().equals(id))) {
	        return "invalidToken";
	    }
	 
	    Calendar cal = Calendar.getInstance();
	    if ((passToken.getExpiryDate()
	        .getTime() - cal.getTime()
	        .getTime()) <= 0) {
	        return "expired";
	    }
	 
	    UserSistema userSistema = passToken.getUser();
	    
	    Authentication auth = new UsernamePasswordAuthenticationToken(
	    		userSistema, null, Arrays.asList(
	      new SimpleGrantedAuthority("ROLE_PASSWORD")));
	    SecurityContextHolder.getContext().setAuthentication(auth);
	    return null;
	}
	
	/**
	 * Verifica Credenciales
	 * @param username Nombre del usuario. 
	 * @return boolean
	 */

	public Boolean checkCredential(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserSistema u where " +
				"u.username =:username");
		query.setParameter("username",username);
		UserSistema user = (UserSistema) query.uniqueResult();
		return user.getCredentialsNonExpired();
	}
	
	/**
	 * Verifica si tiene que cambiar contraseña
	 * @param username Nombre del usuario. 
	 * @return boolean
	 */

	public Boolean checkChangeCredential(String username) {
		// Retrieve session from Hibernate
		if(!username.equals("")) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserSistema u where " +
				"u.username =:username");
		query.setParameter("username",username);
		UserSistema user = (UserSistema) query.uniqueResult();
		return user.getChangePasswordNextLogin();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Guarda un usuario
	 * @param user El usuario. 
	 * 
	 */
	public void saveUser(UserSistema user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
	}
	
	
	public void createPasswordResetTokenForUser(UserSistema user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(token, user);
	    Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(myToken);
	}

	/**
	 * Regresa los UserAccess
	 * @param username Nombre del usuario. 
	 * @return una lista de <code>UserAccess</code>
	 */

	@SuppressWarnings("unchecked")
	public List<UserAccess> getUserAccess(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserAccess u where " +
				"u.usuario.username = '" + username + "' order by u.loginDate DESC");
		return query.list();
	}
	
	/**
	 * Regresa todos los roles de usuarios
	 * 
	 * @return una lista de <code>Rol</code>(es)
	 */

	@SuppressWarnings("unchecked")
	public List<Rol> getRoles() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Rol");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Guarda un rol del usuario
	 * @param rol El rol a guardar 
	 * 
	 */
	public void saveRoleUser(Authority rol) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(rol);
	}
	
	/**
	 * Regresa todos los roles de usuarios
	 * @param username Nombre del usuario. 
	 * @return una lista de <code>Rol</code>(es)
	 */

	@SuppressWarnings("unchecked")
	public List<Authority> getRolesUsuario(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Authority auth " +
				"where auth.authId.username =:username and auth.pasive ='0'");
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los roles que no tenga el usuario
	 * @param username Nombre del usuario. 
	 * @return una lista de <code>Rol</code>(es)
	 */

	@SuppressWarnings("unchecked")
	public List<Rol> getRolesNoTieneUsuario(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Rol roles " +
				"where roles.authority not in (select auth.authId.authority from Authority auth where auth.authId.username =:username)");
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa un rol del usuario
	 * @param username Nombre del usuario.
	 * @param rol Nombre del usuario.  
	 * @return un <code>Authority</code>
	 */

	public Authority getRolUsuario(String username, String rol) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Authority auth " +
				"where auth.authId.username =:username and auth.authId.authority =:rol");
		query.setParameter("username",username);
		query.setParameter("rol",rol);
		Authority rolUsuario = (Authority) query.uniqueResult();
		// Retrieve 
		return  rolUsuario;
	}
	
	/**
	 * Regresa todos los roles del usuario
	 * @param username Nombre del usuario. 
	 * @return una lista de <code>Rol</code>(es)
	 */

	@SuppressWarnings("unchecked")
	public List<Authority> getRolesUsuarioTodos(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Authority auth " +
				"where auth.authId.username =:username");
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Guarda un UsuarioLocalidad
	 * @param us El UsuarioLocalidad a guardar
	 * 
	 */
	public void saveUsuarioLocalidad(UsuarioLocalidad ul) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(ul);
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioLocalidad> getLocalidadesUsuarioTodos(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username");
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Localidad> getLocalidadesNoTieneUsuario(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Localidad loc  where loc.pasive ='0' and loc.ident not in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username)");
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}
	
	public UsuarioLocalidad getUsuarioLocalidad(String username, String localidad) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.usuarioLocalidadId.localidad =:localidad ");
		query.setParameter("username",username);
		query.setParameter("localidad",localidad);
		// Retrieve all
		return  (UsuarioLocalidad) query.uniqueResult();
	}
	
	public UsuarioLocalidad getUsuarioLocalidadActivo(String username, String localidad) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM UsuarioLocalidad uloc where uloc.usuarioLocalidadId.usuario =:username and uloc.usuarioLocalidadId.localidad =:localidad and uloc.pasive = '0'");
		query.setParameter("username",username);
		query.setParameter("localidad",localidad);
		// Retrieve all
		return  (UsuarioLocalidad) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Localidad> getLocalidadesUsuario(String username) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Localidad loc " +
				"where (loc.pasive ='0' and loc.ident in (Select uloc.usuarioLocalidadId.localidad from UsuarioLocalidad uloc "
				+ "where uloc.usuarioLocalidadId.usuario =:username and uloc.pasive ='0'))");
		query.setParameter("username",username);
		// Retrieve all
		return  query.list();
	}

}

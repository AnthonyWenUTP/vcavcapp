package org.clintonhealthaccess.vca.users.dao;

import org.clintonhealthaccess.vca.users.model.UserAttempts;
import org.springframework.security.core.Authentication;

/**
 * Auditable es la interface para implementar los datos de usuario de spring.
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public interface UserDetailsDao {

	void updateFailAttempts(String username);

	void resetFailAttempts(String username);
	
	void insertNewAccess(Authentication auth);
	
	void updateAccessDateLogout(String username, String idSesion, String direccionIp);
	
	void updateAccessUrl(String username, String idSesion, String direccionIp, String url);
	
	void checkCredentialsDate(String username);
	
	UserAttempts getUserAttempts(String username);

}
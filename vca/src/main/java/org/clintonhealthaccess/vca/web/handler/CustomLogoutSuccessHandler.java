package org.clintonhealthaccess.vca.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.clintonhealthaccess.vca.users.dao.UserDetailsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSuccessHandler extends
SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler{
	private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);
	UserDetailsDao userDetailsDao;
	@Override
    public void onLogoutSuccess
      (HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
      throws IOException, ServletException {
		String refererUrl = request.getHeader("Referer");
		String idSesion ="";
		String direccionIp="";
		logger.info(refererUrl);
		if (authentication!=null){
			WebAuthenticationDetails wad  = (WebAuthenticationDetails) authentication.getDetails();
			String username = authentication.getName();
			if(wad!=null) {
				idSesion = wad.getSessionId();
				direccionIp = wad.getRemoteAddress();
			}
			userDetailsDao.updateAccessUrl(username, idSesion, direccionIp, refererUrl);
			super.onLogoutSuccess(request, response, authentication);
		}
		else{
			response.sendRedirect(request.getContextPath()); // home page
		}
    }
	public UserDetailsDao getUserDetailsDao() {
		return userDetailsDao;
	}
	public void setUserDetailsDao(UserDetailsDao userDetailsDao) {
		this.userDetailsDao = userDetailsDao;
	}
}

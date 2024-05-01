package org.clintonhealthaccess.vca.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

public class AuthenticationListener implements ApplicationListener <AbstractAuthenticationEvent> {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationListener.class);
	@Override
    public void onApplicationEvent(AbstractAuthenticationEvent appEvent)
    {
        if (appEvent instanceof AuthenticationSuccessEvent)
        {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
            logger.info(event.toString());
        }

        if (appEvent instanceof AuthenticationFailureBadCredentialsEvent)
        {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;
            logger.error(event.toString());
        }
    }

}

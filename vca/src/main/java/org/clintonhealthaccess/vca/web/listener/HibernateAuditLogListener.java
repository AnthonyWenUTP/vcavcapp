package org.clintonhealthaccess.vca.web.listener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.Initializable;
import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/** 
 * Audit Log Listener is used to log insert, update, delete, and load operations. Complete list of listeners/events can be obtained at <tt>org.hibernate.event.EventListeners</tt>. 
 *  
 * @see org.hibernate.event.EventListeners 
 * @author whoover 
 */  
public final class HibernateAuditLogListener implements  
PostDeleteEventListener, PostInsertEventListener, PostUpdateEventListener,  
Initializable { 
	
	
	private static final long serialVersionUID = 1L;  
    private static final Logger LOG = LoggerFactory.getLogger(HibernateAuditLogListener.class);    
    public static final String OPERATION_TYPE_UPDATE = "UPDATE";    
  

    /** 
     * {@inheritDoc} 
     */  
    @Override  
    public final void initialize(final Configuration cfg) {  
        //  
    }  
    
	/** 
     * Log updates made to the current model in the the Audit Trail. 
     *  
     * @param event 
     *            the post-update event 
     */
	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		try {
            final Serializable entityId = event.getId();
            final String entityClass = event.getEntity().getClass().toString();
            final String entityName = event.getEntity().toString();
            final Date transTime = new Date(); // new Date(event.getSource().getTimestamp());  
            final EntityMode entityMode = event.getPersister().guessEntityMode(event.getEntity());  
            Object oldPropValue = null;  
            Object newPropValue = null; 
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            
            if (event.getEntity() instanceof Auditable){
              	Auditable obj = (Auditable) event.getEntity();
              	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                final String actorId = authentication.getName();
                // need to have a separate session for audit save  
	            StatelessSession session = event.getPersister().getFactory().openStatelessSession();  
	            session.beginTransaction();  
	  
	            // get the existing entity from session so that we can extract existing property values  
	            Object existingEntity = session.get(event.getEntity().getClass(), entityId);  
	  
	            // cycle through property names, extract corresponding property values and insert new entry in audit trail  
	            for (String propertyName : event.getPersister().getPropertyNames()) {  
	            	if (obj.isFieldAuditable(propertyName)){
		                newPropValue = event.getPersister().getPropertyValue(event.getEntity(), propertyName, entityMode); 
		                // collections will fire their own events  
	                    if (!(newPropValue instanceof Collection)) {
                            if (existingEntity!=null) {
                                oldPropValue = event.getPersister().getPropertyValue(existingEntity, propertyName, entityMode);
                                if (oldPropValue instanceof Date) {
                                    oldPropValue = formatter.format((Date) oldPropValue);
                                }
                                if (newPropValue instanceof Date) {
                                    newPropValue = formatter.format((Date) newPropValue);
                                }
                                if (newPropValue != null || oldPropValue != null) {
	                                if (newPropValue != null && !newPropValue.equals("")) {
	                                    if (!newPropValue.equals(oldPropValue)) {
	                                        if (LOG.isDebugEnabled()) {
	                                            LOG.debug("{} for: {}, ID: {}, property: {}, old value: {}, new value: {}, actor: {}, date: {}", new Object[]{OPERATION_TYPE_UPDATE, entityName, entityId, propertyName, oldPropValue, newPropValue, actorId, transTime});
	                                        }
	                                        session.insert(new AuditTrail(entityId.toString(), entityClass, entityName, propertyName, oldPropValue != null ? oldPropValue.toString() : null, newPropValue != null ? newPropValue
	                                                .toString() : null, OPERATION_TYPE_UPDATE, actorId, transTime));
	                                    }
	                                }
	                                else if (oldPropValue != null && !oldPropValue.equals("")) {
	                                    if (!oldPropValue.equals(newPropValue)) {
	                                        if (LOG.isDebugEnabled()) {
	                                            LOG.debug("{} for: {}, ID: {}, property: {}, old value: {}, new value: {}, actor: {}, date: {}", new Object[]{OPERATION_TYPE_UPDATE, entityName, entityId, propertyName, oldPropValue, newPropValue, actorId, transTime});
	                                        }
	                                        session.insert(new AuditTrail(entityId.toString(), entityClass, entityName, propertyName, oldPropValue != null ? oldPropValue.toString() : null, newPropValue != null ? newPropValue
	                                                .toString() : null, OPERATION_TYPE_UPDATE, actorId, transTime));
	                                    }
	                                }
                                }
                            }
	                	}
	            	}
	            }  
	  
	            session.getTransaction().commit();  
	            session.close(); 
            }
        } catch (HibernateException e) {  
            LOG.error("Unable to process audit log for UPDATE operation", e);  
        }
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		LOG.debug("PostInsertEvent");
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		LOG.debug("PostDeleteEvent");
	}     
}

package org.clintonhealthaccess.vca.service;

import org.clintonhealthaccess.vca.domain.audit.AuditTrail;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Servicio para el objeto AuditTrail (Bitácora)
 * 
 * @author William Aviles
 * 
 **/

@Service("auditTrailService")
@Transactional
public class AuditTrailService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<AuditTrail> getBitacora(String id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM AuditTrail audit where audit.entityId =:id order by audit.operationDate desc");
		query.setParameter("id", id);
		return query.list();
	}
	

    /**
     * Guardar un formulario AuditTrail
     * @param auditTrail a guardar
     */
    public void saveAuditTrail(AuditTrail auditTrail){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(auditTrail);
    }
}

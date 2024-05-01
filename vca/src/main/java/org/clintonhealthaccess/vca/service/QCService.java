package org.clintonhealthaccess.vca.service;

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

@Service("qcService")
@Transactional
public class QCService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	public List<Household> getViviendasDuplicadas() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createSQLQuery("SELECT * " + 
				"FROM households WHERE (((households.CODE) In (SELECT CODE FROM households As Tmp GROUP BY CODE HAVING Count(*)>1 ))) " + 
				"ORDER BY households.CODE;");
		// Retrieve all
		return  query.list();
	}
	
	
}

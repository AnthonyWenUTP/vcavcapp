package org.clintonhealthaccess.vca.domain.audit;

/**
 * Auditable es la interface que todas las clases deben implementar para ser parte de la auditoría en el sistema.
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public interface Auditable {
	
	public boolean isFieldAuditable(String fieldname);

}

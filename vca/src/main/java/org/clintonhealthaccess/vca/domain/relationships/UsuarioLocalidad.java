package org.clintonhealthaccess.vca.domain.relationships;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.clintonhealthaccess.vca.users.model.UserSistema;
import org.hibernate.annotations.ForeignKey;

/**
 * Simple objeto de dominio que representa la relación de las localidades para un usuario
 * 
 * @author William Aviles
 **/

@Entity
@Table(name = "userslocalities", catalog = "vca")
public class UsuarioLocalidad extends BaseMetaData implements Auditable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsuarioLocalidadId usuarioLocalidadId;
	private UserSistema user;
	private Localidad locality;
	
	public UsuarioLocalidad() {
	}
	
	public UsuarioLocalidad(UsuarioLocalidadId usuarioLocalidadId, Date recordDate, String recordUser) {
		super(recordDate, recordUser);
		this.usuarioLocalidadId = usuarioLocalidadId;
	}
	
	public UsuarioLocalidad(UsuarioLocalidadId userCenterId, UserSistema user, Localidad segment
			, String recordUser, Date recordDate) {
		super(recordDate, recordUser);
		this.usuarioLocalidadId = userCenterId;
		this.user = user;
		this.locality = segment;
	}
	
	@Id
	public UsuarioLocalidadId getUsuarioLocalidadId() {
		return usuarioLocalidadId;
	}
	public void setUsuarioLocalidadId(UsuarioLocalidadId usuarioLocalidadId) {
		this.usuarioLocalidadId = usuarioLocalidadId;
	}
	
	@ManyToOne
	@JoinColumn(name="user", insertable = false, updatable = false)
	@ForeignKey(name = "UL_USUARIOS_FK")
	public UserSistema getUser() {
		return user;
	}
	
	public void setUser(UserSistema user) {
		this.user = user;
	}
	@ManyToOne
	@JoinColumn(name="locality", insertable = false, updatable = false)
	@ForeignKey(name = "UL_LOC_FK")
	public Localidad getLocalidad() {
		return locality;
	}
	public void setLocalidad(Localidad localidad) {
		this.locality = localidad;
	}
	
	@Override
	public String toString(){
		return locality.getCode();
	}
	@Override
	public boolean isFieldAuditable(String fieldname) {
		//Campos no auditables en la tabla
		if(fieldname.matches("recordDate")||fieldname.matches("recordUser")){
			return false;
		}		
		return true;
	}

}

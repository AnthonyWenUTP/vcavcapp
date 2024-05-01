package org.clintonhealthaccess.vca.users.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;

/**
 * Simple objeto de dominio que representa un rol para un usuario
 * 
 * @author William Aviles
 **/

@Entity
@Table(name = "userroles", catalog = "vca")
public class Authority extends BaseMetaData implements Auditable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AuthorityId authId;
	private UserSistema user;
	private Rol rol;
	
	
	@Id
	public AuthorityId getAuthId() {
		return authId;
	}
	public void setAuthId(AuthorityId authId) {
		this.authId = authId;
	}
	
	public Authority() {
	}
	
	
	public Authority(AuthorityId authId, UserSistema user, Rol rol) {
		super();
		this.authId = authId;
		this.user = user;
		this.rol = rol;
	}
	
	public Authority(AuthorityId authId,
			Date recordDate, String recordUser) {
		super(recordDate, recordUser);
		this.authId = authId;
	}
	
	public Authority(AuthorityId authId,
			UserSistema user, Rol rol,Date recordDate, String recordUser) {
		super(recordDate, recordUser);
		this.authId = authId;
		this.user = user;
		this.rol = rol;
	}
	@ManyToOne
	@JoinColumn(name="username", insertable = false, updatable = false)
	@ForeignKey(name = "userRoles_FK")
	public UserSistema getUser() {
		return user;
	}
	
	public void setUser(UserSistema user) {
		this.user = user;
	}
	@ManyToOne
	@JoinColumn(name="role", insertable = false, updatable = false)
	@ForeignKey(name = "roles_FK")
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	@Override
	public String toString(){
		return authId.getAuthority();
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

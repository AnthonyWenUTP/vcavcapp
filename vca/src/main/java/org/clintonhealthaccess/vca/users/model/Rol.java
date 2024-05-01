package org.clintonhealthaccess.vca.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Simple objeto de dominio que representa un rol
 * 
 * 
 * @author William Aviles
 **/

@Entity
@Table(name = "roles", catalog = "vca")
public class Rol {
	private String authority;	
	
	@Id
	@Column(name = "role", nullable = false, length =50)
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String toString(){
		return authority;
	}
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Rol))
			return false;
		
		Rol castOther = (Rol) other;

		return (this.getAuthority().equals(castOther.getAuthority()));
	}
}

package org.clintonhealthaccess.vca.domain.irs;

import org.clintonhealthaccess.vca.domain.BaseMetaData;

/**
 * 
 * Supervisor
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Personal extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String code;
	private String name;
	private boolean sprayer;
	private boolean sentinel;
	private boolean supervisor;
	
	
	
	public Personal() {
		super();
	}



	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}
	
	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public boolean isSprayer() {
		return sprayer;
	}


	public void setSprayer(boolean sprayer) {
		this.sprayer = sprayer;
	}


	public boolean isSentinel() {
		return sentinel;
	}

	public void setSentinel(boolean sentinel) {
		this.sentinel = sentinel;
	}

	public boolean isSupervisor() {
		return supervisor;
	}

	public void setSupervisor(boolean supervisor) {
		this.supervisor = supervisor;
	}

	@Override
	public String toString(){
		return this.getCode();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Personal))
			return false;
		
		Personal castOther = (Personal) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

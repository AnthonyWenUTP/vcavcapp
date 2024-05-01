package org.clintonhealthaccess.vca.domain.irs;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.Household;




/**
 * 
 * Target es la clase que representa las viviendas objetivos en una temporada.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Target extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private IrsSeason irsSeason;
	private Household household;
	private String sprayStatus;
	private Date lastModified;
	private Personal assignedTo;
	

	
	
	
	
	public Target() {
		super();
	}

	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	public IrsSeason getIrsSeason() {
		return irsSeason;
	}



	public void setIrsSeason(IrsSeason irsSeason) {
		this.irsSeason = irsSeason;
	}


	public Household getHousehold() {
		return household;
	}



	public void setHousehold(Household household) {
		this.household = household;
	}
	

	public String getSprayStatus() {
		return sprayStatus;
	}



	public void setSprayStatus(String sprayStatus) {
		this.sprayStatus = sprayStatus;
	}


	public Date getLastModified() {
		return lastModified;
	}



	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	
	
	
	public Personal getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Personal assignedTo) {
		this.assignedTo = assignedTo;
	}

	@Override
	public String toString(){
		return this.getIdent();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Target))
			return false;
		
		Target castOther = (Target) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

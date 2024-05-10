package org.clintonhealthaccess.vca.domain.irs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Localidad;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * 
 * Target es la clase que representa las viviendas objetivos en una temporada.
 * 
 *  
 * @author      William Avil�s
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "targets", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"season","house"})})
public class Target extends BaseMetaData implements Auditable{
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
	private Localidad local;
	
	public Target() {
		super();
	}
	
	public Target(String ident, IrsSeason irsSeason, Household household, String sprayStatus,
			Localidad local, Date lastModified, String username) {
		super();
		this.ident = ident;
		this.irsSeason = irsSeason;
		this.household = household;
		this.sprayStatus = sprayStatus;
		this.lastModified = lastModified;
		this.local = local;
		this.setRecordDate(lastModified);
		this.setRecordUser(username);
	}






	@Id
    @Column(name = "id", nullable = false, length = 50)
	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="season")
    @ForeignKey(name = "FK_TAR_TEMP")
	public IrsSeason getIrsSeason() {
		return irsSeason;
	}



	public void setIrsSeason(IrsSeason irsSeason) {
		this.irsSeason = irsSeason;
	}


	@ManyToOne(optional=false)
	@JoinColumn(name="house")
    @ForeignKey(name = "FK_TAR_CASA")
	public Household getHousehold() {
		return household;
	}



	public void setHousehold(Household household) {
		this.household = household;
	}
	
	
	@Column(name = "sprayStatus", nullable = false, length = 50)
	public String getSprayStatus() {
		return sprayStatus;
	}



	public void setSprayStatus(String sprayStatus) {
		this.sprayStatus = sprayStatus;
	}


	@Column(name = "lastModified", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getLastModified() {
		return lastModified;
	}



	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	


	@ManyToOne(optional=true)
	@JoinColumn(name="assignedto")
    @ForeignKey(name = "FK_TAR_PER")
	public Personal getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Personal assignedTo) {
		this.assignedTo = assignedTo;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="local")
    @ForeignKey(name = "FK_TAR_LOCALIDAD")
	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}


	@Override
	public boolean isFieldAuditable(String fieldname) {
		return true;
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

package org.clintonhealthaccess.vca.domain.irs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * 
 * IrsSeason es la clase que representa la temporada de rociado.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "irsseasons", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"code","pasive"})})
public class IrsSeason extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String code;
	private String name;
	private Date startDate;
	private Date endDate;
	private Integer numberDays;
	private String obs;
	
	
	
	
	public IrsSeason() {
		super();
	}
	
	



	public IrsSeason(String ident, String code, String name) {
		super();
		this.ident = ident;
		this.code = code;
		this.name = name;
	}





	@Id
    @Column(name = "id", nullable = false, length = 50)
	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}
	
	@Column(name = "code", nullable = false, length = 100)
	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}


	@Column(name = "name", nullable = false, length = 500)
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	

	@Column(name = "startDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	@Column(name = "endDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	@Column(name = "numberDays", nullable = false)
	public Integer getNumberDays() {
		return numberDays;
	}



	public void setNumberDays(Integer numberDays) {
		this.numberDays = numberDays;
	}


	@Column(name = "obs", nullable = true, length = 500)
	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}



	@Override
	public boolean isFieldAuditable(String fieldname) {
		return true;
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
		if (!(other instanceof IrsSeason))
			return false;
		
		IrsSeason castOther = (IrsSeason) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

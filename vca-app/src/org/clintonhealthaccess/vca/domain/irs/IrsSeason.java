package org.clintonhealthaccess.vca.domain.irs;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.BaseMetaData;




/**
 * 
 * IrsSeason es la clase que representa la temporada de rociado.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class IrsSeason extends BaseMetaData{
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
	
	
	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public Integer getNumberDays() {
		return numberDays;
	}



	public void setNumberDays(Integer numberDays) {
		this.numberDays = numberDays;
	}



	
	public String getObs() {
		return obs;
	}


	public void setObs(String obs) {
		this.obs = obs;
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

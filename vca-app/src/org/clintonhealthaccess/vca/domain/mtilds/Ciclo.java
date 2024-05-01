package org.clintonhealthaccess.vca.domain.mtilds;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.BaseMetaData;




/**
 * 
 * Ciclo es la clase que representa la el ciclo de entrega de mosquiteros
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Ciclo extends BaseMetaData{
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
	
	
	
	
	public Ciclo() {
		super();
	}
	
	


	public Ciclo(String ident, String code, String name, Date startDate, Date endDate, Integer numberDays, String obs) {
		super();
		this.ident = ident;
		this.code = code;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numberDays = numberDays;
		this.obs = obs;
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
		if (!(other instanceof Ciclo))
			return false;
		
		Ciclo castOther = (Ciclo) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

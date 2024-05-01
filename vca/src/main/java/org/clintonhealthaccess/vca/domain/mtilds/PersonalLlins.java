package org.clintonhealthaccess.vca.domain.mtilds;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.audit.Auditable;



/**
 * 
 * Personal
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "llinshrs", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"code","pasive"})})
public class PersonalLlins extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String code;
	private String name;
	private boolean deliver;
	private boolean evaluation;
	private boolean supervisor;
	
	
	
	public PersonalLlins() {
		super();
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


	@Column(name = "deliver", nullable = true)
	public boolean isDeliver() {
		return deliver;
	}



	public void setDeliver(boolean deliver) {
		this.deliver = deliver;
	}


	@Column(name = "evaluation", nullable = true)
	public boolean isEvaluation() {
		return evaluation;
	}



	public void setEvaluation(boolean evaluation) {
		this.evaluation = evaluation;
	}



	@Column(name = "supervisor", nullable = true)
	public boolean isSupervisor() {
		return supervisor;
	}



	public void setSupervisor(boolean supervisor) {
		this.supervisor = supervisor;
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
		if (!(other instanceof PersonalLlins))
			return false;
		
		PersonalLlins castOther = (PersonalLlins) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

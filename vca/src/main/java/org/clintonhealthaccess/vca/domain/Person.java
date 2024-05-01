package org.clintonhealthaccess.vca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;



/**
 * Person es la clase que representa la persona en la vivienda.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "persons", catalog = "vca")
public class Person extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Household casa;
	private String code;
	private String name;
	private String sex;
	private Integer age;
	private String preg;
	private String obs;
	
	public Person() {
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

	
	@ManyToOne(optional=false)
	@JoinColumn(name="casa")
    @ForeignKey(name = "FK_PERSONA_VIVIENDA")
	public Household getCasa() {
		return casa;
	}

	public void setCasa(Household casa) {
		this.casa = casa;
	}

	
	@Column(name = "name", nullable = false, length = 250)
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}


	@Column(name = "sex", nullable = true, length = 1)
	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}


	@Column(name = "age", nullable = true)
	public Integer getAge() {
		return age;
	}



	public void setAge(Integer age) {
		this.age = age;
	}



	@Column(name = "obs", nullable = true, length = 750)
	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}


	
	@Column(name = "pregnant", nullable = true, length = 1)
	public String getPreg() {
		return preg;
	}



	public void setPreg(String preg) {
		this.preg = preg;
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
		if (!(other instanceof Person))
			return false;
		
		Person castOther = (Person) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

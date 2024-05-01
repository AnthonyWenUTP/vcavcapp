package org.clintonhealthaccess.vca.domain;






/**
 * Person es la clase que representa la persona en la vivienda.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Person extends BaseMetaData {
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



	public Person(String ident, Household casa, String code, String name, String sex, Integer age, String preg,
			String obs) {
		super();
		this.ident = ident;
		this.casa = casa;
		this.code = code;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.preg = preg;
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

	public Household getCasa() {
		return casa;
	}

	public void setCasa(Household casa) {
		this.casa = casa;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}


	
	public String getPreg() {
		return preg;
	}



	public void setPreg(String preg) {
		this.preg = preg;
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

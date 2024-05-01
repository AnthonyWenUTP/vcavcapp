package org.clintonhealthaccess.vca.domain;




/**
 * 
 * Censador
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public class Censador extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String code;
	private String name;
	
	
	public Censador() {
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
		if (!(other instanceof Censador))
			return false;
		
		Censador castOther = (Censador) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

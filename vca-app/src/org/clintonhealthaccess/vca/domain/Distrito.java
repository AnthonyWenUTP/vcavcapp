package org.clintonhealthaccess.vca.domain;



/**
 * Area es la clase que representa el area de salud a la que pertenece el distrito.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Distrito extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String code;
	private String name;
	private Area area;
	
	
	public Distrito() {
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

	public Area getArea() {
		return area;
	}



	public void setArea(Area area) {
		this.area = area;
	}

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
		if (!(other instanceof Distrito))
			return false;
		
		Distrito castOther = (Distrito) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

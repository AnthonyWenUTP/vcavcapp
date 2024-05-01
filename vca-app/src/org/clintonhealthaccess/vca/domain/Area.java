package org.clintonhealthaccess.vca.domain;



public class Area extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String code;
	private String name;
	
	
	public Area() {
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
		if (!(other instanceof Area))
			return false;
		
		Area castOther = (Area) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

package org.clintonhealthaccess.vca.domain;




/**
 * Criadero 
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Criadero extends BaseMetaData  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String ident;
	private Localidad local;
	private String tipo;
	private String info;
	private Double size;
	private String especie;
	
	
	public Criadero() {
		super();
	}


	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	
	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}

	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getInfo() {
		return info;
	}



	public void setInfo(String info) {
		this.info = info;
	}
	
	
	public Double getSize() {
		return size;
	}



	public void setSize(Double size) {
		this.size = size;
	}


	public String getEspecie() {
		return especie;
	}



	public void setEspecie(String especie) {
		this.especie = especie;
	}



	public String toString(){
		return this.getIdent();
	}	

}

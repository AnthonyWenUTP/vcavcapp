package org.clintonhealthaccess.vca.domain;




/**
 * PuntoDiagnostico es la clase que representa un punto de diagnóstico
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public class PuntoDiagnostico extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Localidad local;
	private String clave;
	private String tipo;
	private String status;
	private String info;
	private Double latitude;
	private Double longitude;
	private Integer zoom;
	
	
	public PuntoDiagnostico() {
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

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	public String getInfo() {
		return info;
	}



	public void setInfo(String info) {
		this.info = info;
	}

	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	public Integer getZoom() {
		return zoom;
	}



	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	
	@Override
	public String toString(){
		return this.getClave();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PuntoDiagnostico))
			return false;
		
		PuntoDiagnostico castOther = (PuntoDiagnostico) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

package org.clintonhealthaccess.vca.domain;

import java.util.Date;




/**
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public class Muestra extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Localidad local;
	private String casa;
	private Integer mxProactiva;
	private Integer mxReactiva;
	private Date mxDate;
	private Double latitude;
	private Double longitude;
	private Float exactitud;
	private Double altitud;
	private Integer zoom=10;	
	
	public Muestra() {
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




	public Date getMxDate() {
		return mxDate;
	}



	public void setMxDate(Date mxDate) {
		this.mxDate = mxDate;
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
	
	public Float getExactitud() {
		return exactitud;
	}

	public void setExactitud(Float exactitud) {
		this.exactitud = exactitud;
	}

	public Double getAltitud() {
		return altitud;
	}

	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}

	public Integer getZoom() {
		return zoom;
	}

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}
	
	

	public String getCasa() {
		return casa;
	}

	public void setCasa(String casa) {
		this.casa = casa;
	}

	public Integer getMxProactiva() {
		return mxProactiva;
	}

	public void setMxProactiva(Integer mxProactiva) {
		this.mxProactiva = mxProactiva;
	}

	public Integer getMxReactiva() {
		return mxReactiva;
	}

	public void setMxReactiva(Integer mxReactiva) {
		this.mxReactiva = mxReactiva;
	}

	@Override
	public String toString(){
		return this.getCasa();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Muestra))
			return false;
		
		Muestra castOther = (Muestra) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

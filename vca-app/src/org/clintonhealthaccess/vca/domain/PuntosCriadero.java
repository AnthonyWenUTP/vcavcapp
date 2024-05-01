package org.clintonhealthaccess.vca.domain;


/**
 * 
 * Punto es la clase que representa un punto en el sistema.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public class PuntosCriadero extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Criadero criadero;
	private Double latitude;
	private Double longitude;
	private int order;
	
	
	public PuntosCriadero() {
		super();
	}


	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}


	public Criadero getCriadero() {
		return criadero;
	}


	public void setCriadero(Criadero criadero) {
		this.criadero = criadero;
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


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}


}

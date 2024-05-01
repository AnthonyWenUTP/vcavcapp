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

public class Punto {
	/**
	 * 
	 */
	
	private Double lat;
	private Double lng;

	
	
	public Punto() {
		super();
	}



	public Punto(Double lat, Double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}



	public Double getLat() {
		return lat;
	}



	public void setLat(Double lat) {
		this.lat = lat;
	}



	public Double getLng() {
		return lng;
	}



	public void setLng(Double lng) {
		this.lng = lng;
	}

	


	

}

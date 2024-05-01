package org.clintonhealthaccess.vca.domain;

import java.util.List;

/**
 * 
 * Punto es la clase que representa un punto en el sistema.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public class PoligonCriadero {
	/**
	 * 
	 */
	
	private String name;
	private List<Punto> coordinates;
	private String color;

	
	
	public PoligonCriadero() {
		super();
	}
	
	






	public PoligonCriadero(String name, List<Punto> coordinates, String color) {
		super();
		this.name = name;
		this.coordinates = coordinates;
		this.color = color;
	}








	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}








	public List<Punto> getCoordinates() {
		return coordinates;
	}








	public void setCoordinates(List<Punto> coordinates) {
		this.coordinates = coordinates;
	}








	public String getColor() {
		return color;
	}








	public void setColor(String color) {
		this.color = color;
	}




	

}

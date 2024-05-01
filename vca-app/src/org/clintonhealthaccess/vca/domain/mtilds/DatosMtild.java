package org.clintonhealthaccess.vca.domain.mtilds;

import java.io.Serializable;
import java.util.List;

import org.clintonhealthaccess.vca.domain.Household;



public class DatosMtild implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Household> viviendas;
	List<Ciclo> temporadas;
	

	
	public DatosMtild() {
		super();
	}





	public DatosMtild(List<Household> viviendas, List<Ciclo> temporadas) {
		super();
		this.viviendas = viviendas;
		this.temporadas = temporadas;
	}

	
	public List<Household> getViviendas() {
		return viviendas;
	}





	public void setViviendas(List<Household> viviendas) {
		this.viviendas = viviendas;
	}





	public List<Ciclo> getTemporadas() {
		return temporadas;
	}





	public void setTemporadas(List<Ciclo> temporadas) {
		this.temporadas = temporadas;
	}

}

package org.clintonhealthaccess.vca.movil.controller;

import java.io.Serializable;
import java.util.List;

import org.clintonhealthaccess.vca.domain.OldHousehold;

public class Datos2 implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<OldHousehold> viviendas;
	

	
	public Datos2() {
		super();
	}



	public List<OldHousehold> getViviendas() {
		return viviendas;
	}



	public void setViviendas(List<OldHousehold> viviendas) {
		this.viviendas = viviendas;
	}

	

}

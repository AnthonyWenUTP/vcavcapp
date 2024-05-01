package org.clintonhealthaccess.vca.server;

import java.io.Serializable;
import java.util.List;

import org.clintonhealthaccess.vca.domain.irs.Brigada;
import org.clintonhealthaccess.vca.domain.irs.Personal;
import org.clintonhealthaccess.vca.domain.Censador;
import org.clintonhealthaccess.vca.domain.MessageResource;

public class Catalogos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<MessageResource> catalogos;
	List<Censador> censadores;
	List<Personal> personalIrs;
	List<Brigada> brigadas;
	
	public Catalogos() {
		super();
	}





	public Catalogos(List<MessageResource> catalogos, List<Censador> censadores, List<Personal> personalIrs,
			List<Brigada> brigadas) {
		super();
		this.catalogos = catalogos;
		this.censadores = censadores;
		this.personalIrs = personalIrs;
		this.brigadas = brigadas;
	}





	public List<MessageResource> getCatalogos() {
		return catalogos;
	}


	public void setCatalogos(List<MessageResource> catalogos) {
		this.catalogos = catalogos;
	}


	public List<Censador> getCensadores() {
		return censadores;
	}


	public void setCensadores(List<Censador> censadores) {
		this.censadores = censadores;
	}







	public List<Personal> getPersonalIrs() {
		return personalIrs;
	}





	public void setPersonalIrs(List<Personal> personalIrs) {
		this.personalIrs = personalIrs;
	}





	public List<Brigada> getBrigadas() {
		return brigadas;
	}





	public void setBrigadas(List<Brigada> brigadas) {
		this.brigadas = brigadas;
	}
	
	
	
	

}

package org.clintonhealthaccess.vca.movil.controller;

import java.io.Serializable;
import java.util.List;

import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.PoligonCriadero;
import org.clintonhealthaccess.vca.domain.PoligonFoco;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;



public class DatosMapa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Caso> casos;
	List<PuntoDiagnostico> puntoDiagnosticos;
	List<PoligonCriadero> criaderos;
	List<PoligonFoco> focos;

	

	
	public DatosMapa() {
		super();
	}



	public List<Caso> getCasos() {
		return casos;
	}





	public void setCasos(List<Caso> casos) {
		this.casos = casos;
	}



	public List<PuntoDiagnostico> getPuntoDiagnosticos() {
		return puntoDiagnosticos;
	}



	public void setPuntoDiagnosticos(List<PuntoDiagnostico> puntoDiagnosticos) {
		this.puntoDiagnosticos = puntoDiagnosticos;
	}



	public List<PoligonCriadero> getCriaderos() {
		return criaderos;
	}



	public void setCriaderos(List<PoligonCriadero> criaderos) {
		this.criaderos = criaderos;
	}



	public List<PoligonFoco> getFocos() {
		return focos;
	}



	public void setFocos(List<PoligonFoco> focos) {
		this.focos = focos;
	}




}

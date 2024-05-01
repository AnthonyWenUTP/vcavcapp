package org.clintonhealthaccess.vca.movil.controller;

import java.io.Serializable;
import java.util.List;

import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.Person;
import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.domain.irs.Supervision;
import org.clintonhealthaccess.vca.domain.irs.Target;
import org.clintonhealthaccess.vca.domain.irs.Visit;

public class Datos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Household> viviendas;
	List<Person> personas;
	List<IrsSeason> temporadas;
	List<Target> metas;
	List<Visit> visitas;
	List<Supervision> supervisiones;

	
	public Datos() {
		super();
	}





	public Datos(List<Household> viviendas, List<IrsSeason> temporadas,List<Target> metas,List<Visit> visitas,List<Supervision> supervisiones,List<Person> personas) {
		super();
		this.viviendas = viviendas;
		this.temporadas = temporadas;
		this.metas = metas;
		this.visitas = visitas;
		this.supervisiones = supervisiones;
		this.personas=personas;
	}





	public List<Household> getViviendas() {
		return viviendas;
	}





	public void setViviendas(List<Household> viviendas) {
		this.viviendas = viviendas;
	}





	public List<IrsSeason> getTemporadas() {
		return temporadas;
	}





	public void setTemporadas(List<IrsSeason> temporadas) {
		this.temporadas = temporadas;
	}





	public List<Target> getMetas() {
		return metas;
	}





	public void setMetas(List<Target> metas) {
		this.metas = metas;
	}





	public List<Visit> getVisitas() {
		return visitas;
	}





	public void setVisitas(List<Visit> visitas) {
		this.visitas = visitas;
	}





	public List<Supervision> getSupervisiones() {
		return supervisiones;
	}





	public void setSupervisiones(List<Supervision> supervisiones) {
		this.supervisiones = supervisiones;
	}





	public List<Person> getPersonas() {
		return personas;
	}





	public void setPersonas(List<Person> personas) {
		this.personas = personas;
	}




}

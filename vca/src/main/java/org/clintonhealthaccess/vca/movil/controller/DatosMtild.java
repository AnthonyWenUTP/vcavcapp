package org.clintonhealthaccess.vca.movil.controller;

import java.io.Serializable;
import java.util.List;


import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.mtilds.Ciclo;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaTarget;
import org.clintonhealthaccess.vca.domain.mtilds.EntregaVisita;
import org.clintonhealthaccess.vca.domain.mtilds.Evaluacion;
import org.clintonhealthaccess.vca.domain.mtilds.EvaluacionMosquitero;



public class DatosMtild implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Household> viviendas;
	List<Ciclo> temporadas;
	List<EntregaTarget> targets;
	List<EntregaVisita> visits;
	List<Evaluacion> evals;
	List<EvaluacionMosquitero> evalMosquiteros;
	

	
	public DatosMtild() {
		super();
	}





	public DatosMtild(List<Household> viviendas, List<Ciclo> temporadas, List<EntregaTarget> targets,
			List<EntregaVisita> visits, List<Evaluacion> evals, List<EvaluacionMosquitero> evalMosquiteros) {
		super();
		this.viviendas = viviendas;
		this.temporadas = temporadas;
		this.targets = targets;
		this.visits = visits;
		this.evals = evals;
		this.evalMosquiteros = evalMosquiteros;
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



	public List<EntregaTarget> getTargets() {
		return targets;
	}



	public void setTargets(List<EntregaTarget> targets) {
		this.targets = targets;
	}



	public List<EntregaVisita> getVisits() {
		return visits;
	}



	public void setVisits(List<EntregaVisita> visits) {
		this.visits = visits;
	}



	public List<Evaluacion> getEvals() {
		return evals;
	}



	public void setEvals(List<Evaluacion> evals) {
		this.evals = evals;
	}





	public List<EvaluacionMosquitero> getEvalMosquiteros() {
		return evalMosquiteros;
	}





	public void setEvalMosquiteros(List<EvaluacionMosquitero> evalMosquiteros) {
		this.evalMosquiteros = evalMosquiteros;
	}



}

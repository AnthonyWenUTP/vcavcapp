package org.clintonhealthaccess.vca.domain.mtilds;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.Person;




/**
 * 
 * EvaluacionPersona es la clase que representa la evaluacion de la persona respecto a mosquiteros
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class EvaluacionPersona extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private EntregaTarget target;
	private Person persona;
	private String usoNocheAnterior;
	private String razoneNoUso;
	private String frecuenciaSemana;
	private String obs;
	
	
	
	
	public EvaluacionPersona() {
		super();
	}
	
	


	public EvaluacionPersona(String ident, EntregaTarget target, Person persona) {
		super();
		this.ident = ident;
		this.persona = persona;
	}




	public EvaluacionPersona(String ident, EntregaTarget target, Person persona, String usoNocheAnterior, String razoneNoUso,
			String frecuenciaSemana, String obs) {
		super();
		this.ident = ident;
		this.persona = persona;
		this.usoNocheAnterior = usoNocheAnterior;
		this.razoneNoUso = razoneNoUso;
		this.frecuenciaSemana = frecuenciaSemana;
		this.obs = obs;
	}




	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}
	
	
	
	public EntregaTarget getTarget() {
		return target;
	}




	public void setTarget(EntregaTarget target) {
		this.target = target;
	}




	public Person getPersona() {
		return persona;
	}


	public void setPersona(Person persona) {
		this.persona = persona;
	}


	public String getUsoNocheAnterior() {
		return usoNocheAnterior;
	}


	public void setUsoNocheAnterior(String usoNocheAnterior) {
		this.usoNocheAnterior = usoNocheAnterior;
	}


	public String getRazoneNoUso() {
		return razoneNoUso;
	}


	public void setRazoneNoUso(String razoneNoUso) {
		this.razoneNoUso = razoneNoUso;
	}


	public String getFrecuenciaSemana() {
		return frecuenciaSemana;
	}


	public void setFrecuenciaSemana(String frecuenciaSemana) {
		this.frecuenciaSemana = frecuenciaSemana;
	}


	public String getObs() {
		return obs;
	}


	public void setObs(String obs) {
		this.obs = obs;
	}


	@Override
	public String toString(){
		return this.getIdent();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EvaluacionPersona))
			return false;
		
		EvaluacionPersona castOther = (EvaluacionPersona) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

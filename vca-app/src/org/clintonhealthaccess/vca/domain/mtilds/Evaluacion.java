package org.clintonhealthaccess.vca.domain.mtilds;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.irs.Personal;




/**
 * 
 * Evaluacion es la clase que representa la visita de evaluacion de mosquiteros
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Evaluacion extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private EntregaTarget target;
	private Date visitDate;
	private Personal visitor;
	
	private Integer sitiosDormirCama;
	private Integer sitiosDormirHamaca;
	private Integer sitiosDormirSuelo;
	private Integer sitiosDormirOtro;
	
	
	private Integer mosqSinInsecticidaEnUso;
	
	
	private Integer mtildEnUso;
	
	
	private Integer mtildGuardados;
	private Integer numMtildGuardados;
	private String razonesMtildGuardados;
	
	private Integer mtildFaltantes;
	private Integer numMtildFaltantes;
	private String razonesMtildFaltantes;
	
	private String tempUsoMtild;

	private String obs;
	
	

	public Evaluacion() {
		super();
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


	public Date getVisitDate() {
		return visitDate;
	}



	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	
	
	

	
	public Personal getVisitor() {
		return visitor;
	}

	public void setVisitor(Personal visitor) {
		this.visitor = visitor;
	}

	public Integer getSitiosDormirCama() {
		return sitiosDormirCama;
	}

	public void setSitiosDormirCama(Integer sitiosDormirCama) {
		this.sitiosDormirCama = sitiosDormirCama;
	}

	public Integer getSitiosDormirHamaca() {
		return sitiosDormirHamaca;
	}

	public void setSitiosDormirHamaca(Integer sitiosDormirHamaca) {
		this.sitiosDormirHamaca = sitiosDormirHamaca;
	}

	public Integer getSitiosDormirSuelo() {
		return sitiosDormirSuelo;
	}

	public void setSitiosDormirSuelo(Integer sitiosDormirSuelo) {
		this.sitiosDormirSuelo = sitiosDormirSuelo;
	}

	public Integer getSitiosDormirOtro() {
		return sitiosDormirOtro;
	}

	public void setSitiosDormirOtro(Integer sitiosDormirOtro) {
		this.sitiosDormirOtro = sitiosDormirOtro;
	}




	public Integer getMosqSinInsecticidaEnUso() {
		return mosqSinInsecticidaEnUso;
	}

	public void setMosqSinInsecticidaEnUso(Integer mosqSinInsecticidaEnUso) {
		this.mosqSinInsecticidaEnUso = mosqSinInsecticidaEnUso;
	}

	public Integer getMtildEnUso() {
		return mtildEnUso;
	}

	public void setMtildEnUso(Integer mtildEnUso) {
		this.mtildEnUso = mtildEnUso;
	}
	
	public Integer getMtildGuardados() {
		return mtildGuardados;
	}

	public void setMtildGuardados(Integer mtildGuardados) {
		this.mtildGuardados = mtildGuardados;
	}
	
	

	public Integer getNumMtildGuardados() {
		return numMtildGuardados;
	}

	public void setNumMtildGuardados(Integer numMtildGuardados) {
		this.numMtildGuardados = numMtildGuardados;
	}

	public String getRazonesMtildGuardados() {
		return razonesMtildGuardados;
	}

	public void setRazonesMtildGuardados(String razonesMtildGuardados) {
		this.razonesMtildGuardados = razonesMtildGuardados;
	}

	public Integer getMtildFaltantes() {
		return mtildFaltantes;
	}

	public void setMtildFaltantes(Integer mtildFaltantes) {
		this.mtildFaltantes = mtildFaltantes;
	}

	public Integer getNumMtildFaltantes() {
		return numMtildFaltantes;
	}

	public void setNumMtildFaltantes(Integer numMtildFaltantes) {
		this.numMtildFaltantes = numMtildFaltantes;
	}

	public String getRazonesMtildFaltantes() {
		return razonesMtildFaltantes;
	}

	public void setRazonesMtildFaltantes(String razonesMtildFaltantes) {
		this.razonesMtildFaltantes = razonesMtildFaltantes;
	}

	public String getTempUsoMtild() {
		return tempUsoMtild;
	}

	public void setTempUsoMtild(String tempUsoMtild) {
		this.tempUsoMtild = tempUsoMtild;
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
		if (!(other instanceof Evaluacion))
			return false;
		
		Evaluacion castOther = (Evaluacion) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

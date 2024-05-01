package org.clintonhealthaccess.vca.domain.mtilds;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.irs.Personal;




/**
 * 
 * EntregaTarget es la clase que representa la casa de entrega de mosquiteros
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class EntregaTarget extends BaseMetaData {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Ciclo ciclo;
	private Household household;
	private Date enrollmentDate;
	
	
	private Integer habitantes;
	private Integer sitiosDormirCama;
	private Integer sitiosDormirHamaca;
	private Integer sitiosDormirSuelo;
	private Integer sitiosDormirOtro;
	private Integer mtildExistentes;
	private Integer mosqSinInsecticida;
	
	private String status;
	private Date lastModified;
	private Personal assignedTo;
	
	private String obs;
	
	

	public EntregaTarget() {
		super();
	}

	public EntregaTarget(String ident, Ciclo ciclo, Household household, Date enrollmentDate, Integer sitiosDormirCama,
			Integer sitiosDormirHamaca, Integer sitiosDormirSuelo, Integer sitiosDormirOtro, Integer mtildExistentes,
			Integer mosqSinInsecticida, String status,String obs, Integer habitantes) {
		super();
		this.ident = ident;
		this.ciclo = ciclo;
		this.household = household;
		this.enrollmentDate = enrollmentDate;
		this.sitiosDormirCama = sitiosDormirCama;
		this.sitiosDormirHamaca = sitiosDormirHamaca;
		this.sitiosDormirSuelo = sitiosDormirSuelo;
		this.sitiosDormirOtro = sitiosDormirOtro;
		this.mtildExistentes = mtildExistentes;
		this.mosqSinInsecticida = mosqSinInsecticida;
		this.status = status;
		this.obs=obs;
		this.habitantes=habitantes;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	public Household getHousehold() {
		return household;
	}

	public void setHousehold(Household household) {
		this.household = household;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public Integer getHabitantes() {
		return habitantes;
	}

	public void setHabitantes(Integer habitantes) {
		this.habitantes = habitantes;
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

	public Integer getMtildExistentes() {
		return mtildExistentes;
	}

	public void setMtildExistentes(Integer mtildExistentes) {
		this.mtildExistentes = mtildExistentes;
	}

	public Integer getMosqSinInsecticida() {
		return mosqSinInsecticida;
	}

	public void setMosqSinInsecticida(Integer mosqSinInsecticida) {
		this.mosqSinInsecticida = mosqSinInsecticida;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Personal getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Personal assignedTo) {
		this.assignedTo = assignedTo;
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
		if (!(other instanceof EntregaTarget))
			return false;
		
		EntregaTarget castOther = (EntregaTarget) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

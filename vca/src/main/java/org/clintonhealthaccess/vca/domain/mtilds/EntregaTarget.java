package org.clintonhealthaccess.vca.domain.mtilds;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;




/**
 * 
 * EntregaTarget es la clase que representa la casa de entrega de mosquiteros
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "llintargets", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"ciclo","house"})})
public class EntregaTarget extends BaseMetaData implements Auditable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Ciclo ciclo;
	private Household household;
	private Date enrollmentDate;
	
	
	private Integer habitantes;
	private Integer masculinos;
	private Integer femeninos;
	private Integer menores5;
	private Integer embarazadas;
	private Integer sitiosDormirCama;
	private Integer sitiosDormirHamaca;
	private Integer sitiosDormirSuelo;
	private Integer sitiosDormirOtro;
	private Integer mtildExistentes;
	private Integer mosqSinInsecticida;
	
	private String status;
	private Date lastModified;
	private String assignedTo;
	
	private String obs;
	
	
	@Id
    @Column(name = "id", nullable = false, length = 50)
	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="ciclo")
    @ForeignKey(name = "FK_TAR_CICLO")
	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="house")
    @ForeignKey(name = "FK_TARM_CASA")
	public Household getHousehold() {
		return household;
	}

	public void setHousehold(Household household) {
		this.household = household;
	}

	@Column(name = "enrollmentDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	
	
	@Column(name = "habitantes", nullable = false)
	public Integer getHabitantes() {
		return habitantes;
	}

	public void setHabitantes(Integer habitantes) {
		this.habitantes = habitantes;
	}
	
	@Column(name = "masculinos", nullable = false)
	public Integer getMasculinos() {
		return masculinos;
	}

	public void setMasculinos(Integer masculinos) {
		this.masculinos = masculinos;
	}

	@Column(name = "femeninos", nullable = false)
	public Integer getFemeninos() {
		return femeninos;
	}

	public void setFemeninos(Integer femeninos) {
		this.femeninos = femeninos;
	}

	@Column(name = "menores5", nullable = false)
	public Integer getMenores5() {
		return menores5;
	}

	public void setMenores5(Integer menores5) {
		this.menores5 = menores5;
	}

	@Column(name = "embarazadas", nullable = false)
	public Integer getEmbarazadas() {
		return embarazadas;
	}

	public void setEmbarazadas(Integer embarazadas) {
		this.embarazadas = embarazadas;
	}

	@Column(name = "sitiosDormirCama", nullable = true)
	public Integer getSitiosDormirCama() {
		return sitiosDormirCama;
	}

	public void setSitiosDormirCama(Integer sitiosDormirCama) {
		this.sitiosDormirCama = sitiosDormirCama;
	}

	@Column(name = "sitiosDormirHamaca", nullable = true)
	public Integer getSitiosDormirHamaca() {
		return sitiosDormirHamaca;
	}

	public void setSitiosDormirHamaca(Integer sitiosDormirHamaca) {
		this.sitiosDormirHamaca = sitiosDormirHamaca;
	}

	@Column(name = "sitiosDormirSuelo", nullable = true)
	public Integer getSitiosDormirSuelo() {
		return sitiosDormirSuelo;
	}

	public void setSitiosDormirSuelo(Integer sitiosDormirSuelo) {
		this.sitiosDormirSuelo = sitiosDormirSuelo;
	}

	@Column(name = "sitiosDormirOtro", nullable = true)
	public Integer getSitiosDormirOtro() {
		return sitiosDormirOtro;
	}

	public void setSitiosDormirOtro(Integer sitiosDormirOtro) {
		this.sitiosDormirOtro = sitiosDormirOtro;
	}

	@Column(name = "mtildExistentes", nullable = true)
	public Integer getMtildExistentes() {
		return mtildExistentes;
	}

	public void setMtildExistentes(Integer mtildExistentes) {
		this.mtildExistentes = mtildExistentes;
	}

	@Column(name = "mosqSinInsecticida", nullable = true)
	public Integer getMosqSinInsecticida() {
		return mosqSinInsecticida;
	}

	public void setMosqSinInsecticida(Integer mosqSinInsecticida) {
		this.mosqSinInsecticida = mosqSinInsecticida;
	}

	@Column(name = "estadoCasa", nullable = false, length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "lastModified", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
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
	public boolean isFieldAuditable(String fieldname) {
		return true;
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

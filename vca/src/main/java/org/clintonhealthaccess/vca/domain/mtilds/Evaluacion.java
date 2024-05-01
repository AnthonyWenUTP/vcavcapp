package org.clintonhealthaccess.vca.domain.mtilds;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;




/**
 * 
 * Evaluacion es la clase que representa la visita de evaluacion de mosquiteros
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "llinevals", catalog = "vca")
public class Evaluacion extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private EntregaTarget target;
	private Date evalDate;
	private String visitor;
	
	private Integer sitiosDormirCama;
	private Integer sitiosDormirHamaca;
	private Integer sitiosDormirSuelo;
	private Integer sitiosDormirOtro;
	
	private Integer mtildEnUso;
	private Integer mosqSinInsecticidaEnUso;
	
	private String expUsoMtild;
	private String sirvenMtild;
	private String otroSirvenMtild;
	
	private String expCuidoMtild;
	private String cuidoMtild;
	private String otroCuidoMtild;
	
	private Integer habitantesUsan;
	private Integer habitantesNoUsan;
	
	private String noUsaMtild;
	private String otroNoUsaMtild;
	
	private String usariaMtild;
	private String comoUsariaMtild;
	private String otroComoUsariaMtild;
	
	private String siUsaMtild;
	private String otroSiUsaMtild;
	
	private String histMalaria;
	
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

	
	@Id
    @Column(name = "id", nullable = false, length = 50)
	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="target")
    @ForeignKey(name = "FK_DEL_TAR")
	public EntregaTarget getTarget() {
		return target;
	}



	public void setTarget(EntregaTarget target) {
		this.target = target;
	}

	@Column(name = "evalDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getEvalDate() {
		return evalDate;
	}

	public void setEvalDate(Date evalDate) {
		this.evalDate = evalDate;
	}

	@Column(name = "visitor", nullable = false, length = 200)
	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
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



	@Column(name = "mosqSinInsecticidaEnUso", nullable = true)
	public Integer getMosqSinInsecticidaEnUso() {
		return mosqSinInsecticidaEnUso;
	}

	public void setMosqSinInsecticidaEnUso(Integer mosqSinInsecticidaEnUso) {
		this.mosqSinInsecticidaEnUso = mosqSinInsecticidaEnUso;
	}

	@Column(name = "mtildEnUso", nullable = true)
	public Integer getMtildEnUso() {
		return mtildEnUso;
	}

	public void setMtildEnUso(Integer mtildEnUso) {
		this.mtildEnUso = mtildEnUso;
	}
	
	@Column(name = "mtildGuardados", nullable = false)
	public Integer getMtildGuardados() {
		return mtildGuardados;
	}

	public void setMtildGuardados(Integer mtildGuardados) {
		this.mtildGuardados = mtildGuardados;
	}
	
	
	@Column(name = "numMtildGuardados", nullable = true)
	public Integer getNumMtildGuardados() {
		return numMtildGuardados;
	}

	public void setNumMtildGuardados(Integer numMtildGuardados) {
		this.numMtildGuardados = numMtildGuardados;
	}

	@Column(name = "razonesMtildGuardados", nullable = true, length = 750)
	public String getRazonesMtildGuardados() {
		return razonesMtildGuardados;
	}

	public void setRazonesMtildGuardados(String razonesMtildGuardados) {
		this.razonesMtildGuardados = razonesMtildGuardados;
	}

	@Column(name = "mtildFaltantes", nullable = false)
	public Integer getMtildFaltantes() {
		return mtildFaltantes;
	}

	public void setMtildFaltantes(Integer mtildFaltantes) {
		this.mtildFaltantes = mtildFaltantes;
	}

	@Column(name = "numMtildFaltantes", nullable = true)
	public Integer getNumMtildFaltantes() {
		return numMtildFaltantes;
	}

	public void setNumMtildFaltantes(Integer numMtildFaltantes) {
		this.numMtildFaltantes = numMtildFaltantes;
	}

	@Column(name = "razonesMtildFaltantes", nullable = true, length = 750)
	public String getRazonesMtildFaltantes() {
		return razonesMtildFaltantes;
	}

	public void setRazonesMtildFaltantes(String razonesMtildFaltantes) {
		this.razonesMtildFaltantes = razonesMtildFaltantes;
	}

	@Column(name = "tempUsoMtild", nullable = true, length = 50)
	public String getTempUsoMtild() {
		return tempUsoMtild;
	}

	public void setTempUsoMtild(String tempUsoMtild) {
		this.tempUsoMtild = tempUsoMtild;
	}

	@Column(name = "obs", nullable = true, length = 750)
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

	@Column(name = "expUsoMtild", nullable = false, length = 50)
	public String getExpUsoMtild() {
		return expUsoMtild;
	}

	public void setExpUsoMtild(String expUsoMtild) {
		this.expUsoMtild = expUsoMtild;
	}

	@Column(name = "sirvenMtild", nullable = true, length = 750)
	public String getSirvenMtild() {
		return sirvenMtild;
	}

	public void setSirvenMtild(String sirvenMtild) {
		this.sirvenMtild = sirvenMtild;
	}

	@Column(name = "otroSirvenMtild", nullable = true, length = 250)
	public String getOtroSirvenMtild() {
		return otroSirvenMtild;
	}

	public void setOtroSirvenMtild(String otroSirvenMtild) {
		this.otroSirvenMtild = otroSirvenMtild;
	}

	@Column(name = "expCuidoMtild", nullable = false, length = 50)
	public String getExpCuidoMtild() {
		return expCuidoMtild;
	}

	public void setExpCuidoMtild(String expCuidoMtild) {
		this.expCuidoMtild = expCuidoMtild;
	}

	@Column(name = "cuidoMtild", nullable = true, length = 750)
	public String getCuidoMtild() {
		return cuidoMtild;
	}

	public void setCuidoMtild(String cuidoMtild) {
		this.cuidoMtild = cuidoMtild;
	}

	@Column(name = "otroCuidoMtild", nullable = true, length = 250)
	public String getOtroCuidoMtild() {
		return otroCuidoMtild;
	}

	public void setOtroCuidoMtild(String otroCuidoMtild) {
		this.otroCuidoMtild = otroCuidoMtild;
	}

	@Column(name = "habitantesUsan", nullable = false)
	public Integer getHabitantesUsan() {
		return habitantesUsan;
	}

	public void setHabitantesUsan(Integer habitantesUsan) {
		this.habitantesUsan = habitantesUsan;
	}

	@Column(name = "habitantesNoUsan", nullable = false)
	public Integer getHabitantesNoUsan() {
		return habitantesNoUsan;
	}

	public void setHabitantesNoUsan(Integer habitantesNoUsan) {
		this.habitantesNoUsan = habitantesNoUsan;
	}

	@Column(name = "noUsaMtild", nullable = true, length = 750)
	public String getNoUsaMtild() {
		return noUsaMtild;
	}

	public void setNoUsaMtild(String noUsaMtild) {
		this.noUsaMtild = noUsaMtild;
	}

	@Column(name = "otroNoUsaMtild", nullable = true, length = 250)
	public String getOtroNoUsaMtild() {
		return otroNoUsaMtild;
	}

	public void setOtroNoUsaMtild(String otroNoUsaMtild) {
		this.otroNoUsaMtild = otroNoUsaMtild;
	}

	@Column(name = "usariaMtild", nullable = true, length = 50)
	public String getUsariaMtild() {
		return usariaMtild;
	}

	public void setUsariaMtild(String usariaMtild) {
		this.usariaMtild = usariaMtild;
	}

	@Column(name = "comoUsariaMtild", nullable = true, length = 750)
	public String getComoUsariaMtild() {
		return comoUsariaMtild;
	}

	public void setComoUsariaMtild(String comoUsariaMtild) {
		this.comoUsariaMtild = comoUsariaMtild;
	}

	@Column(name = "otroComoUsariaMtild", nullable = true, length = 250)
	public String getOtroComoUsariaMtild() {
		return otroComoUsariaMtild;
	}

	public void setOtroComoUsariaMtild(String otroComoUsariaMtild) {
		this.otroComoUsariaMtild = otroComoUsariaMtild;
	}

	@Column(name = "siUsaMtild", nullable = true, length = 750)
	public String getSiUsaMtild() {
		return siUsaMtild;
	}

	public void setSiUsaMtild(String siUsaMtild) {
		this.siUsaMtild = siUsaMtild;
	}

	@Column(name = "otroSiUsaMtild", nullable = true, length = 250)
	public String getOtroSiUsaMtild() {
		return otroSiUsaMtild;
	}

	public void setOtroSiUsaMtild(String otroSiUsaMtild) {
		this.otroSiUsaMtild = otroSiUsaMtild;
	}

	@Column(name = "histMalaria", nullable = true, length = 50)
	public String getHistMalaria() {
		return histMalaria;
	}

	public void setHistMalaria(String histMalaria) {
		this.histMalaria = histMalaria;
	}
	

}

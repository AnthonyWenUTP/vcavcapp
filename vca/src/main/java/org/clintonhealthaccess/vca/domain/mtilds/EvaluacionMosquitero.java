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
 * EvaluacionMosquitero es la clase que representa la evaluacion de cada mosquitero
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "llinevalsmtild", catalog = "vca")
public class EvaluacionMosquitero extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private EntregaTarget target;
	private Date evalDate;
	private String codeMosq;
	private String tipo;
	private String eval;
	private String evaluador;
	private String estadoEvaluacion;
	private String razonMtildGuardado;
	private String razonMtildFaltante;
	private String usadoAnoche;
	private String lavado6Meses;
	private String formaLavado;
	private String formaSecado;
	private String manejoNoUso;
	private String reaccionSecundaria;
	private String cualReaccionSecundaria;
	private String otraReaccionSecundaria;
	private String estaRoto;
	private String razonRoto;
	private String otraRazonRoto;
	private String tipoAgujeros;
	private Integer numAgujerosTipo1;
	private Integer numAgujerosTipo2;
	private Integer numAgujerosTipo3;
	private Integer numAgujerosTipo4;
	private String agujerosReparados;
	private Integer numAgujerosReparados;
	private String agujerosReparadosComo;
	private String colectado;
	
	private String obs;
	
	
	
	
	public EvaluacionMosquitero() {
		super();
	}


	




	public EvaluacionMosquitero(String ident, EntregaTarget target, String tipo, String eval) {
		super();
		this.ident = ident;
		this.target = target;
		this.tipo = tipo;
		this.eval = eval;
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
    @ForeignKey(name = "FK_MOS_TAR")
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


	@Column(name = "codeMosq", nullable = false, length = 50)
	public String getCodeMosq() {
		return codeMosq;
	}




	public void setCodeMosq(String codeMosq) {
		this.codeMosq = codeMosq;
	}



	@Column(name = "tipoMosq", nullable = false, length = 50)
	public String getTipo() {
		return tipo;
	}




	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	@Column(name = "evaluado", nullable = false, length = 50)
	public String getEval() {
		return eval;
	}




	public void setEval(String eval) {
		this.eval = eval;
	}


	@Column(name = "evaluador", nullable = false, length = 250)
	public String getEvaluador() {
		return evaluador;
	}


	public void setEvaluador(String evaluador) {
		this.evaluador = evaluador;
	}


	@Column(name = "estadoEvaluacion", nullable = false, length = 50)
	public String getEstadoEvaluacion() {
		return estadoEvaluacion;
	}




	public void setEstadoEvaluacion(String estadoEvaluacion) {
		this.estadoEvaluacion = estadoEvaluacion;
	}



	@Column(name = "razonMtildGuardado", nullable = true, length = 50)
	public String getRazonMtildGuardado() {
		return razonMtildGuardado;
	}




	public void setRazonMtildGuardado(String razonMtildGuardado) {
		this.razonMtildGuardado = razonMtildGuardado;
	}



	@Column(name = "razonMtildFaltante", nullable = true, length = 50)
	public String getRazonMtildFaltante() {
		return razonMtildFaltante;
	}




	public void setRazonMtildFaltante(String razonMtildFaltante) {
		this.razonMtildFaltante = razonMtildFaltante;
	}



	@Column(name = "usadoAnoche", nullable = true, length = 50)
	public String getUsadoAnoche() {
		return usadoAnoche;
	}




	public void setUsadoAnoche(String usadoAnoche) {
		this.usadoAnoche = usadoAnoche;
	}



	@Column(name = "lavado6Meses", nullable = true, length = 50)
	public String getLavado6Meses() {
		return lavado6Meses;
	}




	public void setLavado6Meses(String lavado6Meses) {
		this.lavado6Meses = lavado6Meses;
	}



	@Column(name = "formaLavado", nullable = true, length = 750)
	public String getFormaLavado() {
		return formaLavado;
	}




	public void setFormaLavado(String formaLavado) {
		this.formaLavado = formaLavado;
	}



	@Column(name = "formaSecado", nullable = true, length = 750)
	public String getFormaSecado() {
		return formaSecado;
	}




	public void setFormaSecado(String formaSecado) {
		this.formaSecado = formaSecado;
	}



	@Column(name = "manejoNoUso", nullable = true, length = 50)
	public String getManejoNoUso() {
		return manejoNoUso;
	}




	public void setManejoNoUso(String manejoNoUso) {
		this.manejoNoUso = manejoNoUso;
	}



	@Column(name = "reaccionSecundaria", nullable = true, length = 50)
	public String getReaccionSecundaria() {
		return reaccionSecundaria;
	}




	public void setReaccionSecundaria(String reaccionSecundaria) {
		this.reaccionSecundaria = reaccionSecundaria;
	}



	@Column(name = "cualReaccionSecundaria", nullable = true, length = 750)
	public String getCualReaccionSecundaria() {
		return cualReaccionSecundaria;
	}




	public void setCualReaccionSecundaria(String cualReaccionSecundaria) {
		this.cualReaccionSecundaria = cualReaccionSecundaria;
	}



	@Column(name = "otraReaccionSecundaria", nullable = true, length = 250)
	public String getOtraReaccionSecundaria() {
		return otraReaccionSecundaria;
	}




	public void setOtraReaccionSecundaria(String otraReaccionSecundaria) {
		this.otraReaccionSecundaria = otraReaccionSecundaria;
	}



	@Column(name = "estaRoto", nullable = true, length = 50)
	public String getEstaRoto() {
		return estaRoto;
	}




	public void setEstaRoto(String estaRoto) {
		this.estaRoto = estaRoto;
	}



	@Column(name = "razonRoto", nullable = true, length = 750)
	public String getRazonRoto() {
		return razonRoto;
	}




	public void setRazonRoto(String razonRoto) {
		this.razonRoto = razonRoto;
	}



	@Column(name = "otraRazonRoto", nullable = true, length = 250)
	public String getOtraRazonRoto() {
		return otraRazonRoto;
	}




	public void setOtraRazonRoto(String otraRazonRoto) {
		this.otraRazonRoto = otraRazonRoto;
	}



	@Column(name = "tipoAgujeros", nullable = true, length = 750)
	public String getTipoAgujeros() {
		return tipoAgujeros;
	}



	
	public void setTipoAgujeros(String tipoAgujeros) {
		this.tipoAgujeros = tipoAgujeros;
	}



	@Column(name = "numAgujerosTipo1", nullable = true)
	public Integer getNumAgujerosTipo1() {
		return numAgujerosTipo1;
	}




	public void setNumAgujerosTipo1(Integer numAgujerosTipo1) {
		this.numAgujerosTipo1 = numAgujerosTipo1;
	}



	@Column(name = "numAgujerosTipo2", nullable = true)
	public Integer getNumAgujerosTipo2() {
		return numAgujerosTipo2;
	}




	public void setNumAgujerosTipo2(Integer numAgujerosTipo2) {
		this.numAgujerosTipo2 = numAgujerosTipo2;
	}



	@Column(name = "numAgujerosTipo3", nullable = true)
	public Integer getNumAgujerosTipo3() {
		return numAgujerosTipo3;
	}




	public void setNumAgujerosTipo3(Integer numAgujerosTipo3) {
		this.numAgujerosTipo3 = numAgujerosTipo3;
	}



	@Column(name = "numAgujerosTipo4", nullable = true)
	public Integer getNumAgujerosTipo4() {
		return numAgujerosTipo4;
	}




	public void setNumAgujerosTipo4(Integer numAgujerosTipo4) {
		this.numAgujerosTipo4 = numAgujerosTipo4;
	}



	@Column(name = "agujerosReparados", nullable = true, length = 50)
	public String getAgujerosReparados() {
		return agujerosReparados;
	}




	public void setAgujerosReparados(String agujerosReparados) {
		this.agujerosReparados = agujerosReparados;
	}



	@Column(name = "numAgujerosReparados", nullable = true)
	public Integer getNumAgujerosReparados() {
		return numAgujerosReparados;
	}




	public void setNumAgujerosReparados(Integer numAgujerosReparados) {
		this.numAgujerosReparados = numAgujerosReparados;
	}



	@Column(name = "agujerosReparadosComo", nullable = true, length = 750)
	public String getAgujerosReparadosComo() {
		return agujerosReparadosComo;
	}




	public void setAgujerosReparadosComo(String agujerosReparadosComo) {
		this.agujerosReparadosComo = agujerosReparadosComo;
	}



	@Column(name = "colectado", nullable = true, length = 50)
	public String getColectado() {
		return colectado;
	}




	public void setColectado(String colectado) {
		this.colectado = colectado;
	}



	@Column(name = "obs", nullable = true, length = 500)
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
		if (!(other instanceof EvaluacionMosquitero))
			return false;
		
		EvaluacionMosquitero castOther = (EvaluacionMosquitero) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

package org.clintonhealthaccess.vca.domain.mtilds;

import org.clintonhealthaccess.vca.domain.BaseMetaData;




/**
 * 
 * EvaluacionMosquitero es la clase que representa la evaluacion de cada mosquitero
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class EvaluacionMosquitero extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private EntregaTarget target;
	private String codeMosq;
	private String tipo;
	private String eval;
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




	public String getCodeMosq() {
		return codeMosq;
	}




	public void setCodeMosq(String codeMosq) {
		this.codeMosq = codeMosq;
	}




	public String getTipo() {
		return tipo;
	}




	public void setTipo(String tipo) {
		this.tipo = tipo;
	}




	public String getEval() {
		return eval;
	}




	public void setEval(String eval) {
		this.eval = eval;
	}




	public String getEstadoEvaluacion() {
		return estadoEvaluacion;
	}




	public void setEstadoEvaluacion(String estadoEvaluacion) {
		this.estadoEvaluacion = estadoEvaluacion;
	}




	public String getRazonMtildGuardado() {
		return razonMtildGuardado;
	}




	public void setRazonMtildGuardado(String razonMtildGuardado) {
		this.razonMtildGuardado = razonMtildGuardado;
	}




	public String getRazonMtildFaltante() {
		return razonMtildFaltante;
	}




	public void setRazonMtildFaltante(String razonMtildFaltante) {
		this.razonMtildFaltante = razonMtildFaltante;
	}




	public String getUsadoAnoche() {
		return usadoAnoche;
	}




	public void setUsadoAnoche(String usadoAnoche) {
		this.usadoAnoche = usadoAnoche;
	}




	public String getLavado6Meses() {
		return lavado6Meses;
	}




	public void setLavado6Meses(String lavado6Meses) {
		this.lavado6Meses = lavado6Meses;
	}




	public String getFormaLavado() {
		return formaLavado;
	}




	public void setFormaLavado(String formaLavado) {
		this.formaLavado = formaLavado;
	}




	public String getFormaSecado() {
		return formaSecado;
	}




	public void setFormaSecado(String formaSecado) {
		this.formaSecado = formaSecado;
	}




	public String getManejoNoUso() {
		return manejoNoUso;
	}




	public void setManejoNoUso(String manejoNoUso) {
		this.manejoNoUso = manejoNoUso;
	}




	public String getReaccionSecundaria() {
		return reaccionSecundaria;
	}




	public void setReaccionSecundaria(String reaccionSecundaria) {
		this.reaccionSecundaria = reaccionSecundaria;
	}




	public String getCualReaccionSecundaria() {
		return cualReaccionSecundaria;
	}




	public void setCualReaccionSecundaria(String cualReaccionSecundaria) {
		this.cualReaccionSecundaria = cualReaccionSecundaria;
	}




	public String getOtraReaccionSecundaria() {
		return otraReaccionSecundaria;
	}




	public void setOtraReaccionSecundaria(String otraReaccionSecundaria) {
		this.otraReaccionSecundaria = otraReaccionSecundaria;
	}




	public String getEstaRoto() {
		return estaRoto;
	}




	public void setEstaRoto(String estaRoto) {
		this.estaRoto = estaRoto;
	}




	public String getRazonRoto() {
		return razonRoto;
	}




	public void setRazonRoto(String razonRoto) {
		this.razonRoto = razonRoto;
	}




	public String getOtraRazonRoto() {
		return otraRazonRoto;
	}




	public void setOtraRazonRoto(String otraRazonRoto) {
		this.otraRazonRoto = otraRazonRoto;
	}




	public String getTipoAgujeros() {
		return tipoAgujeros;
	}




	public void setTipoAgujeros(String tipoAgujeros) {
		this.tipoAgujeros = tipoAgujeros;
	}




	public Integer getNumAgujerosTipo1() {
		return numAgujerosTipo1;
	}




	public void setNumAgujerosTipo1(Integer numAgujerosTipo1) {
		this.numAgujerosTipo1 = numAgujerosTipo1;
	}




	public Integer getNumAgujerosTipo2() {
		return numAgujerosTipo2;
	}




	public void setNumAgujerosTipo2(Integer numAgujerosTipo2) {
		this.numAgujerosTipo2 = numAgujerosTipo2;
	}




	public Integer getNumAgujerosTipo3() {
		return numAgujerosTipo3;
	}




	public void setNumAgujerosTipo3(Integer numAgujerosTipo3) {
		this.numAgujerosTipo3 = numAgujerosTipo3;
	}




	public Integer getNumAgujerosTipo4() {
		return numAgujerosTipo4;
	}




	public void setNumAgujerosTipo4(Integer numAgujerosTipo4) {
		this.numAgujerosTipo4 = numAgujerosTipo4;
	}




	public String getAgujerosReparados() {
		return agujerosReparados;
	}




	public void setAgujerosReparados(String agujerosReparados) {
		this.agujerosReparados = agujerosReparados;
	}




	public Integer getNumAgujerosReparados() {
		return numAgujerosReparados;
	}




	public void setNumAgujerosReparados(Integer numAgujerosReparados) {
		this.numAgujerosReparados = numAgujerosReparados;
	}




	public String getAgujerosReparadosComo() {
		return agujerosReparadosComo;
	}




	public void setAgujerosReparadosComo(String agujerosReparadosComo) {
		this.agujerosReparadosComo = agujerosReparadosComo;
	}




	public String getColectado() {
		return colectado;
	}




	public void setColectado(String colectado) {
		this.colectado = colectado;
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
		if (!(other instanceof EvaluacionMosquitero))
			return false;
		
		EvaluacionMosquitero castOther = (EvaluacionMosquitero) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

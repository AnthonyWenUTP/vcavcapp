package org.clintonhealthaccess.vca.domain.irs;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.BaseMetaData;





/**
 * 
 * Supervision es la clase que representa las supervisiones realizadas en campo.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Supervision extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Target target;
	private Date supervisionDate;
	private Personal supervisor;
	private Personal rociador;
	private String usoEqProt;
	
	private String eqProtBien;
	private String numIden;
	private String aguaOp;
	private String prepViv;
	private String coopPrepViv;
	
	private String mezcla;
	private String aguaAdec;
	private String mezclaPrep;
	private String agitaBomba;
	private String bombaCerrada;
	
	private String bombaPresion;
	private String compruebaBomba;
	private String colocApropiada;
	private String distApropiada;
	private String distBoquilla;
	
	private String pasoFrente;
	private String mantRitmo;
	private String metConteo;
	private String velocSuperficies;
	private String supFajas;
	
	private String pasosLaterales;
	private String salvarObstaculos;
	private String bienRociado;
	private String supInvertidas;
	private String objPiso;
	
	private String reportaConsumoAprop;
	private String transEqAprop;
	private String eqCompleto;
	private String cuidaMatEq;
	private String buenAspPersonal;
	
	private String cumpleInstrucciones;
	private String aceptaSuperv;
	private String respetuoso;
	
	private String camp;
	
	private String obs;
	
	

	public Supervision() {
		super();
	}


	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	public Target getTarget() {
		return target;
	}



	public void setTarget(Target target) {
		this.target = target;
	}


	public Date getSupervisionDate() {
		return supervisionDate;
	}



	public void setSupervisionDate(Date supervisionDate) {
		this.supervisionDate = supervisionDate;
	}

	public Personal getSupervisor() {
		return supervisor;
	}



	public void setSupervisor(Personal supervisor) {
		this.supervisor = supervisor;
	}

	public Personal getRociador() {
		return rociador;
	}



	public void setRociador(Personal rociador) {
		this.rociador = rociador;
	}


	public String getUsoEqProt() {
		return usoEqProt;
	}



	public void setUsoEqProt(String usoEqProt) {
		this.usoEqProt = usoEqProt;
	}

	public String getEqProtBien() {
		return eqProtBien;
	}



	public void setEqProtBien(String eqProtBien) {
		this.eqProtBien = eqProtBien;
	}

	public String getNumIden() {
		return numIden;
	}



	public void setNumIden(String numIden) {
		this.numIden = numIden;
	}


	public String getAguaOp() {
		return aguaOp;
	}



	public void setAguaOp(String aguaOp) {
		this.aguaOp = aguaOp;
	}


	public String getPrepViv() {
		return prepViv;
	}



	public void setPrepViv(String prepViv) {
		this.prepViv = prepViv;
	}


	public String getCoopPrepViv() {
		return coopPrepViv;
	}



	public void setCoopPrepViv(String coopPrepViv) {
		this.coopPrepViv = coopPrepViv;
	}



	public String getMezcla() {
		return mezcla;
	}



	public void setMezcla(String mezcla) {
		this.mezcla = mezcla;
	}


	public String getAguaAdec() {
		return aguaAdec;
	}



	public void setAguaAdec(String aguaAdec) {
		this.aguaAdec = aguaAdec;
	}


	public String getMezclaPrep() {
		return mezclaPrep;
	}



	public void setMezclaPrep(String mezclaPrep) {
		this.mezclaPrep = mezclaPrep;
	}


	public String getAgitaBomba() {
		return agitaBomba;
	}



	public void setAgitaBomba(String agitaBomba) {
		this.agitaBomba = agitaBomba;
	}


	
	public String getBombaCerrada() {
		return bombaCerrada;
	}



	public void setBombaCerrada(String bombaCerrada) {
		this.bombaCerrada = bombaCerrada;
	}


	
	public String getBombaPresion() {
		return bombaPresion;
	}



	public void setBombaPresion(String bombaPresion) {
		this.bombaPresion = bombaPresion;
	}



	public String getCompruebaBomba() {
		return compruebaBomba;
	}



	public void setCompruebaBomba(String compruebaBomba) {
		this.compruebaBomba = compruebaBomba;
	}


	
	public String getColocApropiada() {
		return colocApropiada;
	}



	public void setColocApropiada(String colocApropiada) {
		this.colocApropiada = colocApropiada;
	}


	
	public String getDistApropiada() {
		return distApropiada;
	}



	public void setDistApropiada(String distApropiada) {
		this.distApropiada = distApropiada;
	}


	
	public String getDistBoquilla() {
		return distBoquilla;
	}



	public void setDistBoquilla(String distBoquilla) {
		this.distBoquilla = distBoquilla;
	}


	
	public String getPasoFrente() {
		return pasoFrente;
	}



	public void setPasoFrente(String pasoFrente) {
		this.pasoFrente = pasoFrente;
	}


	
	public String getMantRitmo() {
		return mantRitmo;
	}



	public void setMantRitmo(String mantRitmo) {
		this.mantRitmo = mantRitmo;
	}


	
	public String getMetConteo() {
		return metConteo;
	}



	public void setMetConteo(String metConteo) {
		this.metConteo = metConteo;
	}



	public String getVelocSuperficies() {
		return velocSuperficies;
	}



	public void setVelocSuperficies(String velocSuperficies) {
		this.velocSuperficies = velocSuperficies;
	}



	public String getSupFajas() {
		return supFajas;
	}



	public void setSupFajas(String supFajas) {
		this.supFajas = supFajas;
	}



	public String getPasosLaterales() {
		return pasosLaterales;
	}



	public void setPasosLaterales(String pasosLaterales) {
		this.pasosLaterales = pasosLaterales;
	}



	public String getSalvarObstaculos() {
		return salvarObstaculos;
	}



	public void setSalvarObstaculos(String salvarObstaculos) {
		this.salvarObstaculos = salvarObstaculos;
	}



	public String getBienRociado() {
		return bienRociado;
	}



	public void setBienRociado(String bienRociado) {
		this.bienRociado = bienRociado;
	}



	public String getSupInvertidas() {
		return supInvertidas;
	}



	public void setSupInvertidas(String supInvertidas) {
		this.supInvertidas = supInvertidas;
	}



	public String getObjPiso() {
		return objPiso;
	}



	public void setObjPiso(String objPiso) {
		this.objPiso = objPiso;
	}


	public String getReportaConsumoAprop() {
		return reportaConsumoAprop;
	}



	public void setReportaConsumoAprop(String reportaConsumoAprop) {
		this.reportaConsumoAprop = reportaConsumoAprop;
	}



	public String getTransEqAprop() {
		return transEqAprop;
	}



	public void setTransEqAprop(String transEqAprop) {
		this.transEqAprop = transEqAprop;
	}



	public String getEqCompleto() {
		return eqCompleto;
	}



	public void setEqCompleto(String eqCompleto) {
		this.eqCompleto = eqCompleto;
	}


	public String getCuidaMatEq() {
		return cuidaMatEq;
	}



	public void setCuidaMatEq(String cuidaMatEq) {
		this.cuidaMatEq = cuidaMatEq;
	}



	public String getBuenAspPersonal() {
		return buenAspPersonal;
	}



	public void setBuenAspPersonal(String buenAspPersonal) {
		this.buenAspPersonal = buenAspPersonal;
	}



	public String getCumpleInstrucciones() {
		return cumpleInstrucciones;
	}



	public void setCumpleInstrucciones(String cumpleInstrucciones) {
		this.cumpleInstrucciones = cumpleInstrucciones;
	}



	public String getAceptaSuperv() {
		return aceptaSuperv;
	}



	public void setAceptaSuperv(String aceptaSuperv) {
		this.aceptaSuperv = aceptaSuperv;
	}


	public String getRespetuoso() {
		return respetuoso;
	}



	public void setRespetuoso(String respetuoso) {
		this.respetuoso = respetuoso;
	}

	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}

	
	
	public String getCamp() {
		return camp;
	}


	public void setCamp(String camp) {
		this.camp = camp;
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
		if (!(other instanceof Supervision))
			return false;
		
		Supervision castOther = (Supervision) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

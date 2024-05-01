package org.clintonhealthaccess.vca.movil.controller;

import java.io.Serializable;
import java.util.List;

import org.clintonhealthaccess.vca.domain.Caso;
import org.clintonhealthaccess.vca.domain.Criadero;
import org.clintonhealthaccess.vca.domain.CriaderoTx;
import org.clintonhealthaccess.vca.domain.Muestra;
import org.clintonhealthaccess.vca.domain.PtoDxVisit;
import org.clintonhealthaccess.vca.domain.PuntoDiagnostico;
import org.clintonhealthaccess.vca.domain.PuntosCriadero;



public class DatosMapeo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Caso> casos;
	List<Muestra> muestras;
	List<PuntoDiagnostico> puntosdx;
	List<Criadero> criaderos;
	List<PtoDxVisit> visitaspdx;
	List<CriaderoTx> txscriadero;
	List<PuntosCriadero> puntoscriadero;
	

	

	
	public DatosMapeo() {
		super();
	}




	public List<Caso> getCasos() {
		return casos;
	}




	public void setCasos(List<Caso> casos) {
		this.casos = casos;
	}




	public List<Muestra> getMuestras() {
		return muestras;
	}




	public void setMuestras(List<Muestra> muestras) {
		this.muestras = muestras;
	}




	public List<PuntoDiagnostico> getPuntosdx() {
		return puntosdx;
	}




	public void setPuntosdx(List<PuntoDiagnostico> puntosdx) {
		this.puntosdx = puntosdx;
	}




	public List<Criadero> getCriaderos() {
		return criaderos;
	}




	public void setCriaderos(List<Criadero> criaderos) {
		this.criaderos = criaderos;
	}




	public List<PtoDxVisit> getVisitaspdx() {
		return visitaspdx;
	}




	public void setVisitaspdx(List<PtoDxVisit> visitaspdx) {
		this.visitaspdx = visitaspdx;
	}




	public List<CriaderoTx> getTxscriadero() {
		return txscriadero;
	}




	public void setTxscriadero(List<CriaderoTx> txscriadero) {
		this.txscriadero = txscriadero;
	}




	public List<PuntosCriadero> getPuntoscriadero() {
		return puntoscriadero;
	}




	public void setPuntoscriadero(List<PuntosCriadero> puntoscriadero) {
		this.puntoscriadero = puntoscriadero;
	}

	


}

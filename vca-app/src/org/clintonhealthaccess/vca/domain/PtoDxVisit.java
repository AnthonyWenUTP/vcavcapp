package org.clintonhealthaccess.vca.domain;

import java.util.Date;





/**
 * 
 * Visit es la clase que representa las visitas realizadas en el punto de diagnostico.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public class PtoDxVisit extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private PuntoDiagnostico punto;
	private Date visitDate;
	private String visitType;
	private String obs;
	
	

	public PtoDxVisit() {
		super();
	}


	
	public String getIdent() {
		return ident;
	}



	public void setIdent(String ident) {
		this.ident = ident;
	}



	public PuntoDiagnostico getPunto() {
		return punto;
	}



	public void setPunto(PuntoDiagnostico punto) {
		this.punto = punto;
	}



	public Date getVisitDate() {
		return visitDate;
	}



	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}



	public String getVisitType() {
		return visitType;
	}



	public void setVisitType(String visitType) {
		this.visitType = visitType;
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
	

}

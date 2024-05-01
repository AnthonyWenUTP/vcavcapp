package org.clintonhealthaccess.vca.domain.irs;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.BaseMetaData;




/**
 * 
 * Visit es la clase que representa las visitas realizadas en campo.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Visit extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Target target;
	private Date visitDate;
	private Personal visitor;
	private Personal supervisor;
	private Brigada brigada;
	private String visit;
	private String activity;
	private String compVisit;
	private String reasonNoVisit;
	private String reasonNoVisitOther;
	private String reasonReluctant;
	private String reasonReluctantOther;
	private String modCasa;
	private Integer sprayedRooms;
	private Integer numCharges;
	private String reasonIncomplete;
	private String supervised;
	private Integer personasCharlas;
	private String obs;
	
	

	public Visit() {
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


	public Date getVisitDate() {
		return visitDate;
	}



	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	

	public Brigada getBrigada() {
		return brigada;
	}


	public void setBrigada(Brigada brigada) {
		this.brigada = brigada;
	}
	

	public String getVisit() {
		return visit;
	}



	public void setVisit(String visit) {
		this.visit = visit;
	}



	public String getActivity() {
		return activity;
	}



	public void setActivity(String activity) {
		this.activity = activity;
	}


	public String getCompVisit() {
		return compVisit;
	}



	public void setCompVisit(String compVisit) {
		this.compVisit = compVisit;
	}


	public String getReasonNoVisit() {
		return reasonNoVisit;
	}



	public void setReasonNoVisit(String reasonNoVisit) {
		this.reasonNoVisit = reasonNoVisit;
	}
	
	
	public String getReasonNoVisitOther() {
		return reasonNoVisitOther;
	}



	public void setReasonNoVisitOther(String reasonNoVisitOther) {
		this.reasonNoVisitOther = reasonNoVisitOther;
	}


	public String getReasonReluctant() {
		return reasonReluctant;
	}



	public void setReasonReluctant(String reasonReluctant) {
		this.reasonReluctant = reasonReluctant;
	}
	
	

	public String getReasonReluctantOther() {
		return reasonReluctantOther;
	}



	public void setReasonReluctantOther(String reasonReluctantOther) {
		this.reasonReluctantOther = reasonReluctantOther;
	}


	public Integer getSprayedRooms() {
		return sprayedRooms;
	}



	public void setSprayedRooms(Integer sprayedRooms) {
		this.sprayedRooms = sprayedRooms;
	}


	public Integer getNumCharges() {
		return numCharges;
	}



	public void setNumCharges(Integer numCharges) {
		this.numCharges = numCharges;
	}


	public String getReasonIncomplete() {
		return reasonIncomplete;
	}



	public void setReasonIncomplete(String reasonIncomplete) {
		this.reasonIncomplete = reasonIncomplete;
	}



	public String getSupervised() {
		return supervised;
	}

	public Integer getPersonasCharlas() {
		return personasCharlas;
	}



	public void setPersonasCharlas(Integer personasCharlas) {
		this.personasCharlas = personasCharlas;
	}

	public void setSupervised(String supervised) {
		this.supervised = supervised;
	}

	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}

	

	
	public String getModCasa() {
		return modCasa;
	}

	public void setModCasa(String modCasa) {
		this.modCasa = modCasa;
	}

	public Personal getVisitor() {
		return visitor;
	}

	public void setVisitor(Personal visitor) {
		this.visitor = visitor;
	}

	public Personal getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Personal supervisor) {
		this.supervisor = supervisor;
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
		if (!(other instanceof Visit))
			return false;
		
		Visit castOther = (Visit) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

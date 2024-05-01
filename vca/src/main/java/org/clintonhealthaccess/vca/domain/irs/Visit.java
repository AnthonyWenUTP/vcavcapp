package org.clintonhealthaccess.vca.domain.irs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.clintonhealthaccess.vca.domain.BaseMetaData;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * 
 * Visit es la clase que representa las visitas realizadas en campo.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "visits", catalog = "vca")
public class Visit extends BaseMetaData implements Auditable{
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
	private String modCasa;
	private String reasonNoVisit;
	private String reasonNoVisitOther;
	private String reasonReluctant;
	private String reasonReluctantOther;
	private Integer sprayedRooms;
	private Integer numCharges;
	private String reasonIncomplete;
	private String supervised;
	private Integer personasCharlas;
	private String obs;
	
	

	public Visit() {
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
    @ForeignKey(name = "FK_SPY_TAR")
	public Target getTarget() {
		return target;
	}



	public void setTarget(Target target) {
		this.target = target;
	}



	@Column(name = "visitDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getVisitDate() {
		return visitDate;
	}



	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="visitor")
    @ForeignKey(name = "FK_SPY_ROC")
	public Personal getVisitor() {
		return visitor;
	}



	public void setVisitor(Personal visitor) {
		this.visitor = visitor;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="brigade")
    @ForeignKey(name = "FK_SPR_BRI")
	public Brigada getBrigada() {
		return brigada;
	}


	public void setBrigada(Brigada brigada) {
		this.brigada = brigada;
	}
	
	
	@Column(name = "visit", nullable = false , length = 50)
	public String getVisit() {
		return visit;
	}



	public void setVisit(String visit) {
		this.visit = visit;
	}



	@Column(name = "activity", nullable = false , length = 50)
	public String getActivity() {
		return activity;
	}



	public void setActivity(String activity) {
		this.activity = activity;
	}


	@Column(name = "compVisit", nullable = false , length = 50)
	public String getCompVisit() {
		return compVisit;
	}



	public void setCompVisit(String compVisit) {
		this.compVisit = compVisit;
	}
	
	

	@Column(name = "modCasa", nullable = true , length = 50)
	public String getModCasa() {
		return modCasa;
	}



	public void setModCasa(String modCasa) {
		this.modCasa = modCasa;
	}



	@Column(name = "reasonNoVisit", nullable = true , length = 50)
	public String getReasonNoVisit() {
		return reasonNoVisit;
	}



	public void setReasonNoVisit(String reasonNoVisit) {
		this.reasonNoVisit = reasonNoVisit;
	}
	
	

	@Column(name = "reasonNoVisitOther", nullable = true , length = 500)
	public String getReasonNoVisitOther() {
		return reasonNoVisitOther;
	}



	public void setReasonNoVisitOther(String reasonNoVisitOther) {
		this.reasonNoVisitOther = reasonNoVisitOther;
	}



	@Column(name = "reasonReluctant", nullable = true , length = 50)
	public String getReasonReluctant() {
		return reasonReluctant;
	}



	public void setReasonReluctant(String reasonReluctant) {
		this.reasonReluctant = reasonReluctant;
	}
	
	

	@Column(name = "reasonReluctantOther", nullable = true , length = 500)
	public String getReasonReluctantOther() {
		return reasonReluctantOther;
	}



	public void setReasonReluctantOther(String reasonReluctantOther) {
		this.reasonReluctantOther = reasonReluctantOther;
	}



	@Column(name = "sprayedRooms", nullable = true)
	public Integer getSprayedRooms() {
		return sprayedRooms;
	}



	public void setSprayedRooms(Integer sprayedRooms) {
		this.sprayedRooms = sprayedRooms;
	}


	@Column(name = "numCharges", nullable = true)
	public Integer getNumCharges() {
		return numCharges;
	}



	public void setNumCharges(Integer numCharges) {
		this.numCharges = numCharges;
	}


	@Column(name = "reasonIncomplete", nullable = true , length = 250)
	public String getReasonIncomplete() {
		return reasonIncomplete;
	}



	public void setReasonIncomplete(String reasonIncomplete) {
		this.reasonIncomplete = reasonIncomplete;
	}


	@Column(name = "supervised", nullable = true , length = 50)
	public String getSupervised() {
		return supervised;
	}

	@Column(name = "personasCharlas", nullable = true)
	public Integer getPersonasCharlas() {
		return personasCharlas;
	}



	public void setPersonasCharlas(Integer personasCharlas) {
		this.personasCharlas = personasCharlas;
	}

	public void setSupervised(String supervised) {
		this.supervised = supervised;
	}

	@Column(name = "obs", nullable = true , length = 500)
	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}



	@ManyToOne(optional=false)
	@JoinColumn(name="supervisor")
    @ForeignKey(name = "FK_SUP_VIS")
	public Personal getSupervisor() {
		return supervisor;
	}



	public void setSupervisor(Personal supervisor) {
		this.supervisor = supervisor;
	}



	@Override
	public boolean isFieldAuditable(String fieldname) {
		return true;
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

package org.clintonhealthaccess.vca.domain;

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
 * Visit es la clase que representa las visitas realizadas en el punto de diagnostico.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "visitspdxs", catalog = "vca")
public class PtoDxVisit extends BaseMetaData implements Auditable{
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



	@Id
    @Column(name = "id", nullable = false, length = 50)
	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}

	

	@ManyToOne(optional=false)
	@JoinColumn(name="punto")
    @ForeignKey(name = "FK_VIS_PTO")
	public PuntoDiagnostico getPunto() {
		return punto;
	}

	public void setPunto(PuntoDiagnostico punto) {
		this.punto = punto;
	}



	@Column(name = "visitDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

		
	@Column(name = "visitType", nullable = false , length = 50)
	public String getVisitType() {
		return visitType;
	}



	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	

	@Column(name = "obs", nullable = true , length = 500)
	public String getObs() {
		return obs;
	}


	public void setObs(String obs) {
		this.obs = obs;
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
		if (!(other instanceof PtoDxVisit))
			return false;
		
		PtoDxVisit castOther = (PtoDxVisit) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

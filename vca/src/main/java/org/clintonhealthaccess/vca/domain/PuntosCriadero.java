package org.clintonhealthaccess.vca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;

/**
 * 
 * Punto es la clase que representa un punto en el sistema.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "puntoscriaderos", catalog = "vca")
public class PuntosCriadero extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Criadero criadero;
	private Double latitude;
	private Double longitude;
	private int order;
	
	
	public PuntosCriadero() {
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
	@JoinColumn(name="criadero")
    @ForeignKey(name = "FK_CRIAD_PTO")
	public Criadero getCriadero() {
		return criadero;
	}




	public void setCriadero(Criadero criadero) {
		this.criadero = criadero;
	}



	@Column(name = "latitude", nullable = false)
	public Double getLatitude() {
		return latitude;
	}




	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}



	@Column(name = "longitude", nullable = false)
	public Double getLongitude() {
		return longitude;
	}




	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}


	@Column(name = "ord", nullable = false)
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
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
		if (!(other instanceof Foco))
			return false;
		
		Foco castOther = (Foco) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}

}

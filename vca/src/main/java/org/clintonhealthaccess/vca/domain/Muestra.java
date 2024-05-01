package org.clintonhealthaccess.vca.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "muestras", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"casa","mxDate","local","pasive"})})
public class Muestra extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Localidad local;
	private String casa;
	private Integer mxProactiva=0;
	private Integer mxReactiva=0;
	private Date mxDate;
	private Double latitude;
	private Double longitude;
	private Float exactitud;
	private Double altitud;
	private Integer zoom=10;	
	
	public Muestra() {
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
	@JoinColumn(name="local")
    @ForeignKey(name = "FK_MUESTRA_LOCALIDAD")
	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}


	@Column(name = "casa", nullable = false, length = 100)
	public String getCasa() {
		return casa;
	}



	public void setCasa(String casa) {
		this.casa = casa;
	}


	@Column(name = "mxDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getMxDate() {
		return mxDate;
	}



	public void setMxDate(Date mxDate) {
		this.mxDate = mxDate;
	}
	
	

	@Column(name = "mxProactiva", nullable = false)
	public Integer getMxProactiva() {
		return mxProactiva;
	}

	public void setMxProactiva(Integer mxProactiva) {
		this.mxProactiva = mxProactiva;
	}

	@Column(name = "mxReactiva", nullable = false)
	public Integer getMxReactiva() {
		return mxReactiva;
	}

	public void setMxReactiva(Integer mxReactiva) {
		this.mxReactiva = mxReactiva;
	}

	@Column(name = "latitude", nullable = true)
	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	@Column(name = "longitude", nullable = true)
	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	@Column(name = "exactitud", nullable = true)
	public Float getExactitud() {
		return exactitud;
	}



	public void setExactitud(Float exactitud) {
		this.exactitud = exactitud;
	}


	@Column(name = "altitud", nullable = true)
	public Double getAltitud() {
		return altitud;
	}



	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}
	
	@Column(name = "zoom", nullable = true)
	public Integer getZoom() {
		return zoom;
	}
	

	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	@Override
	public boolean isFieldAuditable(String fieldname) {
		return true;
	}
	
	
	@Override
	public String toString(){
		return this.getCasa();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Muestra))
			return false;
		
		Muestra castOther = (Muestra) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

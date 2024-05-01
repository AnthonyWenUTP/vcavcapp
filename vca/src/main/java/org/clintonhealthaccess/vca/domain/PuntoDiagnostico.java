package org.clintonhealthaccess.vca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;



/**
 * PuntoDiagnostico es la clase que representa un punto de diagnóstico
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "puntosdx", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"clave","pasive"})})
public class PuntoDiagnostico extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private Localidad local;
	private String clave;
	private String tipo;
	private String status;
	private String info;
	private Double latitude;
	private Double longitude;
	private Integer zoom;
	
	
	public PuntoDiagnostico() {
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
    @ForeignKey(name = "FK_PUNTO_LOCALIDAD")
	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}

	@Column(name = "clave", nullable = false, length = 100)
	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column(name = "tipo", nullable = false, length = 50)
	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	@Column(name = "estado", nullable = false, length = 50)
	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	@Column(name = "info", nullable = true, length = 500)
	public String getInfo() {
		return info;
	}



	public void setInfo(String info) {
		this.info = info;
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
		return this.getClave();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PuntoDiagnostico))
			return false;
		
		PuntoDiagnostico castOther = (PuntoDiagnostico) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

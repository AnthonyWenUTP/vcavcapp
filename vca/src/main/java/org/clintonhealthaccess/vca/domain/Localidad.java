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
 * Localidad es la clase que representa la localidad donde se registra la información.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "localities", catalog = "vca", uniqueConstraints={@UniqueConstraint(columnNames = {"code","pasive"})})
public class Localidad extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String code;
	private String name;
	private Distrito district;
	private Double latitude;
	private Double longitude;
	private Integer zoom;
	private Integer population;
	private String pattern;
	private String obs;
	private boolean tieneAcceso = false;
	
	public Localidad() {
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
	
	@Column(name = "code", nullable = false, length = 100)
	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}


	@Column(name = "name", nullable = false, length = 500)
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	
	@ManyToOne(optional=false)
	@JoinColumn(name="district")
    @ForeignKey(name = "FK_LOCALIDAD_DISTRITO")
	public Distrito getDistrict() {
		return district;
	}



	public void setDistrict(Distrito district) {
		this.district = district;
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



	@Column(name = "population", nullable = true)
	public Integer getPopulation() {
		return population;
	}



	public void setPopulation(Integer population) {
		this.population = population;
	}

	@Column(name = "pattern", nullable = false, length = 750)
	public String getPattern() {
		return pattern;
	}



	public void setPattern(String pattern) {
		this.pattern = pattern;
	}



	@Column(name = "obs", nullable = true, length = 750)
	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}


	@Column(name = "acceso", nullable = false)
	public boolean isTieneAcceso() {
		return tieneAcceso;
	}



	public void setTieneAcceso(boolean tieneAcceso) {
		this.tieneAcceso = tieneAcceso;
	}



	@Override
	public boolean isFieldAuditable(String fieldname) {
		return true;
	}
	
	
	@Override
	public String toString(){
		return this.getCode();
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Localidad))
			return false;
		
		Localidad castOther = (Localidad) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

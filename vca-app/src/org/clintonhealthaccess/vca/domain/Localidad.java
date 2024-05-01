package org.clintonhealthaccess.vca.domain;



/**
 * Localidad es la clase que representa la localidad donde se registra la información.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Localidad extends BaseMetaData{
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
	private boolean tieneAcceso;
	
	public Localidad() {
		super();
	}


	public String getIdent() {
		return ident;
	}


	public void setIdent(String ident) {
		this.ident = ident;
	}
	
	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}


	public Distrito getDistrict() {
		return district;
	}



	public void setDistrict(Distrito district) {
		this.district = district;
	}


	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	
	public Integer getZoom() {
		return zoom;
	}


	public void setZoom(Integer zoom) {
		this.zoom = zoom;
	}

	
	
	public String getPattern() {
		return pattern;
	}


	public void setPattern(String pattern) {
		this.pattern = pattern;
	}


	public Integer getPopulation() {
		return population;
	}



	public void setPopulation(Integer population) {
		this.population = population;
	}


	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}
	
	
	

	
	public boolean isTieneAcceso() {
		return tieneAcceso;
	}


	public void setTieneAcceso(boolean tieneAcceso) {
		this.tieneAcceso = tieneAcceso;
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

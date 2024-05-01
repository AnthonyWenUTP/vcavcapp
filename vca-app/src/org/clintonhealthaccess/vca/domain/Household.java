package org.clintonhealthaccess.vca.domain;

import java.util.Date;

/**
 * Household es la clase que representa la vivienda donde se registra la información.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
public class Household extends BaseMetaData{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String code;
	private Localidad local;
	private Censador censusTaker;
	private Date censusDate;
	private String inhabited;
	private String ownerName;
	private Integer habitants;
	private String material;
	private Integer rooms;
	private Integer sprRooms;
	private Integer noSprooms;
	private String noSproomsReasons;
	private Integer personasCharlas;
	private Integer sleep;
	private Integer numNets;
	private Double latitude;
	private Double longitude;
	private Float exactitud;
	private Double altitud;
	private String obs;
	private String verified;
	
	private Integer masculinos;
	private Integer femeninos;
	private Integer menores5;
	private Integer menores5masc;
	private Integer menores5fem;
	private Integer embarazadas;
	private Integer sitiosDormirCama;
	private Integer sitiosDormirHamaca;
	private Integer sitiosDormirSuelo;
	private Integer sitiosDormirOtro;
	private Integer mtildExistentes;
	private Integer mosqSinInsecticida;
	
	public Household() {
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

	
	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}

	
	
	public Censador getCensusTaker() {
		return censusTaker;
	}



	public void setCensusTaker(Censador censusTaker) {
		this.censusTaker = censusTaker;
	}



	public Date getCensusDate() {
		return censusDate;
	}



	public void setCensusDate(Date censusDate) {
		this.censusDate = censusDate;
	}


	public String getInhabited() {
		return inhabited;
	}



	public void setInhabited(String inhabited) {
		this.inhabited = inhabited;
	}


	public String getOwnerName() {
		return ownerName;
	}



	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	public Integer getHabitants() {
		return habitants;
	}



	public void setHabitants(Integer habitants) {
		this.habitants = habitants;
	}


	public String getMaterial() {
		return material;
	}



	public void setMaterial(String material) {
		this.material = material;
	}


	public Integer getRooms() {
		return rooms;
	}



	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}


	public Integer getSprRooms() {
		return sprRooms;
	}



	public void setSprRooms(Integer sprRooms) {
		this.sprRooms = sprRooms;
	}


	public Integer getNoSprooms() {
		return noSprooms;
	}



	public void setNoSprooms(Integer noSprooms) {
		this.noSprooms = noSprooms;
	}


	public String getNoSproomsReasons() {
		return noSproomsReasons;
	}



	public void setNoSproomsReasons(String noSproomsReasons) {
		this.noSproomsReasons = noSproomsReasons;
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
	
	


	public Float getExactitud() {
		return exactitud;
	}



	public void setExactitud(Float exactitud) {
		this.exactitud = exactitud;
	}



	public Double getAltitud() {
		return altitud;
	}



	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}



	public Integer getPersonasCharlas() {
		return personasCharlas;
	}



	public void setPersonasCharlas(Integer personasCharlas) {
		this.personasCharlas = personasCharlas;
	}

	


	public Integer getSleep() {
		return sleep;
	}



	public void setSleep(Integer sleep) {
		this.sleep = sleep;
	}





	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}
	
	
	
	public String getVerified() {
		return verified;
	}



	public void setVerified(String verified) {
		this.verified = verified;
	}



	public Integer getMasculinos() {
		return masculinos;
	}



	public void setMasculinos(Integer masculinos) {
		this.masculinos = masculinos;
	}



	public Integer getFemeninos() {
		return femeninos;
	}



	public void setFemeninos(Integer femeninos) {
		this.femeninos = femeninos;
	}



	public Integer getMenores5() {
		return menores5;
	}



	public void setMenores5(Integer menores5) {
		this.menores5 = menores5;
	}



	public Integer getMenores5masc() {
		return menores5masc;
	}



	public void setMenores5masc(Integer menores5masc) {
		this.menores5masc = menores5masc;
	}



	public Integer getMenores5fem() {
		return menores5fem;
	}



	public void setMenores5fem(Integer menores5fem) {
		this.menores5fem = menores5fem;
	}



	public Integer getEmbarazadas() {
		return embarazadas;
	}



	public void setEmbarazadas(Integer embarazadas) {
		this.embarazadas = embarazadas;
	}



	public Integer getSitiosDormirCama() {
		return sitiosDormirCama;
	}



	public void setSitiosDormirCama(Integer sitiosDormirCama) {
		this.sitiosDormirCama = sitiosDormirCama;
	}



	public Integer getSitiosDormirHamaca() {
		return sitiosDormirHamaca;
	}



	public void setSitiosDormirHamaca(Integer sitiosDormirHamaca) {
		this.sitiosDormirHamaca = sitiosDormirHamaca;
	}



	public Integer getSitiosDormirSuelo() {
		return sitiosDormirSuelo;
	}



	public void setSitiosDormirSuelo(Integer sitiosDormirSuelo) {
		this.sitiosDormirSuelo = sitiosDormirSuelo;
	}



	public Integer getSitiosDormirOtro() {
		return sitiosDormirOtro;
	}



	public void setSitiosDormirOtro(Integer sitiosDormirOtro) {
		this.sitiosDormirOtro = sitiosDormirOtro;
	}



	public Integer getMtildExistentes() {
		return mtildExistentes;
	}



	public void setMtildExistentes(Integer mtildExistentes) {
		this.mtildExistentes = mtildExistentes;
	}



	public Integer getMosqSinInsecticida() {
		return mosqSinInsecticida;
	}



	public void setMosqSinInsecticida(Integer mosqSinInsecticida) {
		this.mosqSinInsecticida = mosqSinInsecticida;
	}



	public Integer getNumNets() {
		return numNets;
	}



	public void setNumNets(Integer numNets) {
		this.numNets = numNets;
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
		if (!(other instanceof Household))
			return false;
		
		Household castOther = (Household) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

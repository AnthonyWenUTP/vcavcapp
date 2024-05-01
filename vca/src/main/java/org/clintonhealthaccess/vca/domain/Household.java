package org.clintonhealthaccess.vca.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.clintonhealthaccess.vca.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;
import org.springframework.format.annotation.DateTimeFormat;



/**
 * Household es la clase que representa la vivienda donde se registra la información.
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "households", catalog = "vca")
public class Household extends BaseMetaData implements Auditable{
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

	
	@ManyToOne(optional=false)
	@JoinColumn(name="local")
    @ForeignKey(name = "FK_VIVIENDA_LOCALIDAD")
	public Localidad getLocal() {
		return local;
	}

	public void setLocal(Localidad local) {
		this.local = local;
	}

	
	@ManyToOne(optional=false)
	@JoinColumn(name="censustaker")
    @ForeignKey(name = "FK_VIVIENDA_CESADOR")
	public Censador getCensusTaker() {
		return censusTaker;
	}



	public void setCensusTaker(Censador censusTaker) {
		this.censusTaker = censusTaker;
	}



	@Column(name = "censusDate", nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	public Date getCensusDate() {
		return censusDate;
	}



	public void setCensusDate(Date censusDate) {
		this.censusDate = censusDate;
	}


	@Column(name = "inhabited", nullable = false, length = 50)
	public String getInhabited() {
		return inhabited;
	}



	public void setInhabited(String inhabited) {
		this.inhabited = inhabited;
	}


	@Column(name = "ownerName", nullable = true, length = 250)
	public String getOwnerName() {
		return ownerName;
	}



	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	@Column(name = "habitants", nullable = true)
	public Integer getHabitants() {
		return habitants;
	}



	public void setHabitants(Integer habitants) {
		this.habitants = habitants;
	}


	@Column(name = "material", nullable = true, length = 50)
	public String getMaterial() {
		return material;
	}



	public void setMaterial(String material) {
		this.material = material;
	}


	@Column(name = "rooms", nullable = true)
	public Integer getRooms() {
		return rooms;
	}



	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}


	@Column(name = "sprRooms", nullable = true)
	public Integer getSprRooms() {
		return sprRooms;
	}



	public void setSprRooms(Integer sprRooms) {
		this.sprRooms = sprRooms;
	}


	@Column(name = "noSprooms", nullable = true)
	public Integer getNoSprooms() {
		return noSprooms;
	}



	public void setNoSprooms(Integer noSprooms) {
		this.noSprooms = noSprooms;
	}


	@Column(name = "noSproomsReasons", nullable = true, length = 250)
	public String getNoSproomsReasons() {
		return noSproomsReasons;
	}



	public void setNoSproomsReasons(String noSproomsReasons) {
		this.noSproomsReasons = noSproomsReasons;
	}
	
	

	@Column(name = "personasCharlas", nullable = true)
	public Integer getPersonasCharlas() {
		return personasCharlas;
	}



	public void setPersonasCharlas(Integer personasCharlas) {
		this.personasCharlas = personasCharlas;
	}


	@Column(name = "sleep", nullable = true)
	public Integer getSleep() {
		return sleep;
	}



	public void setSleep(Integer sleep) {
		this.sleep = sleep;
	}


	/*@Column(name = "numNets", nullable = true)
	public Integer getNumNets() {
		return numNets;
	}



	public void setNumNets(Integer numNets) {
		this.numNets = numNets;
	}*/



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



	@Column(name = "obs", nullable = true, length = 750)
	public String getObs() {
		return obs;
	}



	public void setObs(String obs) {
		this.obs = obs;
	}
	
	


	@Column(name = "verif", nullable = true, length = 50)
	public String getVerified() {
		return verified;
	}



	public void setVerified(String verified) {
		this.verified = verified;
	}

	

	@Column(name = "masculinos", nullable = true)
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
		if (!(other instanceof Household))
			return false;
		
		Household castOther = (Household) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}
	

}

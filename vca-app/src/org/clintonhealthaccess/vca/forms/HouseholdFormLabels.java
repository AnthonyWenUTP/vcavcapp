package org.clintonhealthaccess.vca.forms;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class HouseholdFormLabels {
	
	protected String introMessage;
	protected String introMessageHint;
	protected String code;
	protected String codeHint;
	protected String censusTaker;
	protected String censusTakerHint;
	protected String censusDate;
	protected String censusDateHint;
	protected String inhabited;
	protected String inhabitedHint;
	protected String ownerName;
	protected String ownerNameHint;
	protected String habitants;
	protected String habitantsHint;
	protected String material;
	protected String materialHint;
	protected String rooms;
	protected String roomsHint;
	protected String sprRooms;
	protected String sprRoomsHint;
	protected String noSprooms;
	protected String noSproomsHint;
	protected String noSproomsReasons;
	protected String noSproomsReasonsHint;
	protected String personasCharlas;
	protected String personasCharlasHint;
	
	protected String sleep;
	protected String sleepHint;
	
	protected String numNets;
	protected String numNetsHint;
	
	protected String obs;
	protected String obsHint;
	
	protected String masculinos;
	protected String femeninos;
	protected String menores5;
	protected String menores5masc;
	protected String menores5fem;
	protected String embarazadas;
	protected String sitiosDormirCama;
	protected String sitiosDormirHamaca;
	protected String sitiosDormirSuelo;
	protected String sitiosDormirOtro;
	protected String mtildExistentes;
	protected String mosqSinInsecticida;
	
	protected String masculinosHint;
	protected String femeninosHint;
	protected String menores5Hint;
	protected String menores5mascHint;
	protected String menores5femHint;
	protected String embarazadasHint;
	protected String sitiosDormirCamaHint;
	protected String sitiosDormirHamacaHint;
	protected String sitiosDormirSueloHint;
	protected String sitiosDormirOtroHint;
	protected String mtildExistentesHint;
	protected String mosqSinInsecticidaHint;
	
	public HouseholdFormLabels(){
		Resources res = VcaApplication.getContext().getResources();
		introMessage = res.getString(R.string.introMessage);
		introMessageHint = res.getString(R.string.introMessageHint);
		code = res.getString(R.string.codeHouse);
		codeHint = res.getString(R.string.codeHouseHint);
		censusTaker = res.getString(R.string.censusTaker);
		censusTakerHint = res.getString(R.string.censusTakerHint);
		censusDate = res.getString(R.string.censusDate);
		censusDateHint = res.getString(R.string.censusDateHint);
		inhabited = res.getString(R.string.inhabited);
		inhabitedHint = res.getString(R.string.inhabitedHint);
		ownerName = res.getString(R.string.ownerName);
		ownerNameHint = res.getString(R.string.ownerNameHint);
		habitants = res.getString(R.string.habitants);
		habitantsHint = res.getString(R.string.habitantsHint);
		material = res.getString(R.string.material);
		materialHint = res.getString(R.string.materialHint);
		rooms = res.getString(R.string.rooms);
		roomsHint = res.getString(R.string.roomsHint);
		sprRooms = res.getString(R.string.sprRooms);
		sprRoomsHint = res.getString(R.string.sprRoomsHint);
		noSprooms = res.getString(R.string.noSprooms);
		noSproomsHint = res.getString(R.string.noSproomsHint);
		noSproomsReasons = res.getString(R.string.noSproomsReasons);
		noSproomsReasonsHint = res.getString(R.string.noSproomsReasonsHint);
		personasCharlas = res.getString(R.string.personasCharlas);
		personasCharlasHint = res.getString(R.string.personasCharlasHint);
		sleep = res.getString(R.string.sleep);
		sleepHint = res.getString(R.string.sleepHint);
		numNets = res.getString(R.string.numNets);
		numNetsHint = res.getString(R.string.numNetsHint);
		obs = res.getString(R.string.obsHouse);
		obsHint = res.getString(R.string.obsHouseHint);
		
		masculinos = res.getString(R.string.masculinos);
		masculinosHint = res.getString(R.string.masculinosHint);
		femeninos = res.getString(R.string.femeninos);
		femeninosHint = res.getString(R.string.femeninosHint);

		menores5 = res.getString(R.string.menores5);
		menores5Hint = res.getString(R.string.menores5Hint);

		menores5masc = res.getString(R.string.menores5masc);
		menores5mascHint = res.getString(R.string.menores5mascHint);
		menores5fem = res.getString(R.string.menores5fem);
		menores5femHint = res.getString(R.string.menores5femHint);
		embarazadas = res.getString(R.string.embarazadas);
		embarazadasHint = res.getString(R.string.embarazadasHint);
		sitiosDormirCama = res.getString(R.string.sitiosDormirCama);
		sitiosDormirCamaHint = res.getString(R.string.sitiosDormirCamaHint);
		sitiosDormirHamaca = res.getString(R.string.sitiosDormirHamaca);
		sitiosDormirHamacaHint = res.getString(R.string.sitiosDormirHamacaHint);
		sitiosDormirSuelo = res.getString(R.string.sitiosDormirSuelo);
		sitiosDormirSueloHint = res.getString(R.string.sitiosDormirSueloHint);
		sitiosDormirOtro = res.getString(R.string.sitiosDormirOtro);
		sitiosDormirOtroHint = res.getString(R.string.sitiosDormirOtroHint);
		mtildExistentes = res.getString(R.string.mtildExistentes);
		mtildExistentesHint = res.getString(R.string.mtildExistentesHint);
		mosqSinInsecticida = res.getString(R.string.mosqSinInsecticida);
		mosqSinInsecticidaHint = res.getString(R.string.mosqSinInsecticidaHint);
	}
	
	
	public String getIntroMessage() {
		return introMessage;
	}

	public void setIntroMessage(String introMessage) {
		this.introMessage = introMessage;
	}


	public String getIntroMessageHint() {
		return introMessageHint;
	}


	public void setIntroMessageHint(String introMessageHint) {
		this.introMessageHint = introMessageHint;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getCodeHint() {
		return codeHint;
	}


	public void setCodeHint(String codeHint) {
		this.codeHint = codeHint;
	}


	public String getCensusDate() {
		return censusDate;
	}


	public void setCensusDate(String censusDate) {
		this.censusDate = censusDate;
	}


	public String getCensusDateHint() {
		return censusDateHint;
	}


	public void setCensusDateHint(String censusDateHint) {
		this.censusDateHint = censusDateHint;
	}


	public String getInhabited() {
		return inhabited;
	}


	public void setInhabited(String inhabited) {
		this.inhabited = inhabited;
	}


	public String getInhabitedHint() {
		return inhabitedHint;
	}


	public void setInhabitedHint(String inhabitedHint) {
		this.inhabitedHint = inhabitedHint;
	}


	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	public String getOwnerNameHint() {
		return ownerNameHint;
	}


	public void setOwnerNameHint(String ownerNameHint) {
		this.ownerNameHint = ownerNameHint;
	}


	public String getHabitants() {
		return habitants;
	}


	public void setHabitants(String habitants) {
		this.habitants = habitants;
	}


	public String getHabitantsHint() {
		return habitantsHint;
	}


	public void setHabitantsHint(String habitantsHint) {
		this.habitantsHint = habitantsHint;
	}


	public String getMaterial() {
		return material;
	}


	public void setMaterial(String material) {
		this.material = material;
	}


	public String getMaterialHint() {
		return materialHint;
	}


	public void setMaterialHint(String materialHint) {
		this.materialHint = materialHint;
	}


	public String getRooms() {
		return rooms;
	}


	public void setRooms(String rooms) {
		this.rooms = rooms;
	}


	public String getRoomsHint() {
		return roomsHint;
	}


	public void setRoomsHint(String roomsHint) {
		this.roomsHint = roomsHint;
	}


	public String getSprRooms() {
		return sprRooms;
	}


	public void setSprRooms(String sprRooms) {
		this.sprRooms = sprRooms;
	}


	public String getSprRoomsHint() {
		return sprRoomsHint;
	}


	public void setSprRoomsHint(String sprRoomsHint) {
		this.sprRoomsHint = sprRoomsHint;
	}


	public String getNoSprooms() {
		return noSprooms;
	}


	public void setNoSprooms(String noSprooms) {
		this.noSprooms = noSprooms;
	}


	public String getNoSproomsHint() {
		return noSproomsHint;
	}


	public void setNoSproomsHint(String noSproomsHint) {
		this.noSproomsHint = noSproomsHint;
	}


	public String getNoSproomsReasons() {
		return noSproomsReasons;
	}


	public void setNoSproomsReasons(String noSproomsReasons) {
		this.noSproomsReasons = noSproomsReasons;
	}


	public String getNoSproomsReasonsHint() {
		return noSproomsReasonsHint;
	}


	public void setNoSproomsReasonsHint(String noSproomsReasonsHint) {
		this.noSproomsReasonsHint = noSproomsReasonsHint;
	}


	public String getPersonasCharlas() {
		return personasCharlas;
	}


	public void setPersonasCharlas(String personasCharlas) {
		this.personasCharlas = personasCharlas;
	}


	public String getPersonasCharlasHint() {
		return personasCharlasHint;
	}


	public void setPersonasCharlasHint(String personasCharlasHint) {
		this.personasCharlasHint = personasCharlasHint;
	}


	public String getObs() {
		return obs;
	}


	public void setObs(String obs) {
		this.obs = obs;
	}


	public String getObsHint() {
		return obsHint;
	}


	public void setObsHint(String obsHint) {
		this.obsHint = obsHint;
	}


	public String getCensusTaker() {
		return censusTaker;
	}


	public void setCensusTaker(String censusTaker) {
		this.censusTaker = censusTaker;
	}


	public String getCensusTakerHint() {
		return censusTakerHint;
	}


	public void setCensusTakerHint(String censusTakerHint) {
		this.censusTakerHint = censusTakerHint;
	}


	public String getSleep() {
		return sleep;
	}


	public void setSleep(String sleep) {
		this.sleep = sleep;
	}


	public String getSleepHint() {
		return sleepHint;
	}


	public void setSleepHint(String sleepHint) {
		this.sleepHint = sleepHint;
	}


	public String getNumNets() {
		return numNets;
	}


	public void setNumNets(String numNets) {
		this.numNets = numNets;
	}


	public String getNumNetsHint() {
		return numNetsHint;
	}


	public void setNumNetsHint(String numNetsHint) {
		this.numNetsHint = numNetsHint;
	}


	public String getMasculinos() {
		return masculinos;
	}


	public void setMasculinos(String masculinos) {
		this.masculinos = masculinos;
	}


	public String getFemeninos() {
		return femeninos;
	}


	public void setFemeninos(String femeninos) {
		this.femeninos = femeninos;
	}


	public String getMenores5() {
		return menores5;
	}


	public void setMenores5(String menores5) {
		this.menores5 = menores5;
	}


	public String getMenores5masc() {
		return menores5masc;
	}


	public void setMenores5masc(String menores5masc) {
		this.menores5masc = menores5masc;
	}


	public String getMenores5fem() {
		return menores5fem;
	}


	public void setMenores5fem(String menores5fem) {
		this.menores5fem = menores5fem;
	}


	public String getEmbarazadas() {
		return embarazadas;
	}


	public void setEmbarazadas(String embarazadas) {
		this.embarazadas = embarazadas;
	}


	public String getSitiosDormirCama() {
		return sitiosDormirCama;
	}


	public void setSitiosDormirCama(String sitiosDormirCama) {
		this.sitiosDormirCama = sitiosDormirCama;
	}


	public String getSitiosDormirHamaca() {
		return sitiosDormirHamaca;
	}


	public void setSitiosDormirHamaca(String sitiosDormirHamaca) {
		this.sitiosDormirHamaca = sitiosDormirHamaca;
	}


	public String getSitiosDormirSuelo() {
		return sitiosDormirSuelo;
	}


	public void setSitiosDormirSuelo(String sitiosDormirSuelo) {
		this.sitiosDormirSuelo = sitiosDormirSuelo;
	}


	public String getSitiosDormirOtro() {
		return sitiosDormirOtro;
	}


	public void setSitiosDormirOtro(String sitiosDormirOtro) {
		this.sitiosDormirOtro = sitiosDormirOtro;
	}


	public String getMtildExistentes() {
		return mtildExistentes;
	}


	public void setMtildExistentes(String mtildExistentes) {
		this.mtildExistentes = mtildExistentes;
	}


	public String getMosqSinInsecticida() {
		return mosqSinInsecticida;
	}


	public void setMosqSinInsecticida(String mosqSinInsecticida) {
		this.mosqSinInsecticida = mosqSinInsecticida;
	}


	public String getMasculinosHint() {
		return masculinosHint;
	}


	public void setMasculinosHint(String masculinosHint) {
		this.masculinosHint = masculinosHint;
	}


	public String getFemeninosHint() {
		return femeninosHint;
	}


	public void setFemeninosHint(String femeninosHint) {
		this.femeninosHint = femeninosHint;
	}


	public String getMenores5Hint() {
		return menores5Hint;
	}


	public void setMenores5Hint(String menores5Hint) {
		this.menores5Hint = menores5Hint;
	}


	public String getMenores5mascHint() {
		return menores5mascHint;
	}


	public void setMenores5mascHint(String menores5mascHint) {
		this.menores5mascHint = menores5mascHint;
	}


	public String getMenores5femHint() {
		return menores5femHint;
	}


	public void setMenores5femHint(String menores5femHint) {
		this.menores5femHint = menores5femHint;
	}


	public String getEmbarazadasHint() {
		return embarazadasHint;
	}


	public void setEmbarazadasHint(String embarazadasHint) {
		this.embarazadasHint = embarazadasHint;
	}


	public String getSitiosDormirCamaHint() {
		return sitiosDormirCamaHint;
	}


	public void setSitiosDormirCamaHint(String sitiosDormirCamaHint) {
		this.sitiosDormirCamaHint = sitiosDormirCamaHint;
	}


	public String getSitiosDormirHamacaHint() {
		return sitiosDormirHamacaHint;
	}


	public void setSitiosDormirHamacaHint(String sitiosDormirHamacaHint) {
		this.sitiosDormirHamacaHint = sitiosDormirHamacaHint;
	}


	public String getSitiosDormirSueloHint() {
		return sitiosDormirSueloHint;
	}


	public void setSitiosDormirSueloHint(String sitiosDormirSueloHint) {
		this.sitiosDormirSueloHint = sitiosDormirSueloHint;
	}


	public String getSitiosDormirOtroHint() {
		return sitiosDormirOtroHint;
	}


	public void setSitiosDormirOtroHint(String sitiosDormirOtroHint) {
		this.sitiosDormirOtroHint = sitiosDormirOtroHint;
	}


	public String getMtildExistentesHint() {
		return mtildExistentesHint;
	}


	public void setMtildExistentesHint(String mtildExistentesHint) {
		this.mtildExistentesHint = mtildExistentesHint;
	}


	public String getMosqSinInsecticidaHint() {
		return mosqSinInsecticidaHint;
	}


	public void setMosqSinInsecticidaHint(String mosqSinInsecticidaHint) {
		this.mosqSinInsecticidaHint = mosqSinInsecticidaHint;
	}

	
	
}

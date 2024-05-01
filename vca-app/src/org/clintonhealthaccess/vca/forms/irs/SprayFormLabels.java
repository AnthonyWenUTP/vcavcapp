package org.clintonhealthaccess.vca.forms.irs;

import org.clintonhealthaccess.vca.R;
import org.clintonhealthaccess.vca.VcaApplication;
import android.content.res.Resources;

/**
 * 
 */
public class SprayFormLabels {
	
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
	protected String obs;
	protected String obsHint;
	
	public SprayFormLabels(){
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
		obs = res.getString(R.string.obsHouse);
		obsHint = res.getString(R.string.obsHouseHint);
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

	
	
}

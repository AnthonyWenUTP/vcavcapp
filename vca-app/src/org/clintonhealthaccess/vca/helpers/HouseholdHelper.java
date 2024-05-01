package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.Household;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class HouseholdHelper {
	
	public static ContentValues crearHouseholdValues(Household vivienda){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, vivienda.getIdent());
		cv.put(MainDBConstants.code, vivienda.getCode());
		cv.put(MainDBConstants.local, vivienda.getLocal().getIdent());
		cv.put(MainDBConstants.censusTaker, vivienda.getCensusTaker().getIdent());
		if (vivienda.getCensusDate() != null) cv.put(MainDBConstants.censusDate, vivienda.getCensusDate().getTime());
		cv.put(MainDBConstants.inhabited, vivienda.getInhabited());
		cv.put(MainDBConstants.ownerName, vivienda.getOwnerName());
		cv.put(MainDBConstants.habitants, vivienda.getHabitants());
		cv.put(MainDBConstants.material, vivienda.getMaterial());
		cv.put(MainDBConstants.rooms, vivienda.getRooms());
		cv.put(MainDBConstants.sprRooms, vivienda.getSprRooms());
		cv.put(MainDBConstants.noSprooms, vivienda.getNoSprooms());
		cv.put(MainDBConstants.noSproomsReasons, vivienda.getNoSproomsReasons());
		cv.put(MainDBConstants.sleep, vivienda.getSleep());
		cv.put(MainDBConstants.numNets, vivienda.getNumNets());
		cv.put(MainDBConstants.personasCharlas, vivienda.getPersonasCharlas());
		cv.put(MainDBConstants.latitude, vivienda.getLatitude());
		cv.put(MainDBConstants.longitude, vivienda.getLongitude());
		cv.put(MainDBConstants.altitud, vivienda.getAltitud());
		cv.put(MainDBConstants.exactitud, vivienda.getExactitud());
		cv.put(MainDBConstants.obs, vivienda.getObs());
		cv.put(MainDBConstants.verif, vivienda.getVerified());

		cv.put(MainDBConstants.masculinos, vivienda.getMasculinos());
		cv.put(MainDBConstants.femeninos, vivienda.getFemeninos());
		cv.put(MainDBConstants.menores5, vivienda.getMenores5());
		cv.put(MainDBConstants.menores5masc, vivienda.getMenores5masc());
		cv.put(MainDBConstants.menores5fem, vivienda.getMenores5fem());
		cv.put(MainDBConstants.embarazadas, vivienda.getEmbarazadas());
		cv.put(MainDBConstants.sitiosDormirCama, vivienda.getSitiosDormirCama());
		cv.put(MainDBConstants.sitiosDormirHamaca, vivienda.getSitiosDormirHamaca());
		cv.put(MainDBConstants.sitiosDormirSuelo, vivienda.getSitiosDormirSuelo());
		cv.put(MainDBConstants.sitiosDormirOtro, vivienda.getSitiosDormirOtro());
		cv.put(MainDBConstants.mtildExistentes, vivienda.getMtildExistentes());
		cv.put(MainDBConstants.mosqSinInsecticida, vivienda.getMosqSinInsecticida());
		//METADATA
		if (vivienda.getRecordDate() != null) cv.put(MainDBConstants.recordDate, vivienda.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, vivienda.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(vivienda.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(vivienda.getEstado()));
		cv.put(MainDBConstants.deviceId, vivienda.getDeviceid());
		return cv; 
	}	
	
	public static Household crearHousehold(Cursor cursorHousehold){
		
		Household mHousehold = new Household();
		mHousehold.setIdent(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.ident)));
		mHousehold.setCode(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.code)));
		mHousehold.setLocal(null);
		mHousehold.setCensusTaker(null);
		if(cursorHousehold.getLong(cursorHousehold.getColumnIndex(MainDBConstants.censusDate))>0) mHousehold.setCensusDate(new Date(cursorHousehold.getLong(cursorHousehold.getColumnIndex(MainDBConstants.censusDate))));
		mHousehold.setInhabited(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.inhabited)));
		mHousehold.setOwnerName(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.ownerName)));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.habitants))!=null) 
			mHousehold.setHabitants(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.habitants))));
		mHousehold.setMaterial(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.material)));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.rooms))!=null)
			mHousehold.setRooms(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.rooms))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sprRooms))!=null) 
			mHousehold.setSprRooms(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sprRooms))));
		
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.noSprooms))!=null) 
			mHousehold.setNoSprooms(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.noSprooms))));
		
		
		mHousehold.setNoSproomsReasons(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.noSproomsReasons)));
		
		
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sleep))!=null)
			mHousehold.setSleep(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sleep))));
		
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.numNets))!=null)
			mHousehold.setNumNets(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.numNets))));
		
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.personasCharlas))!=null) 
			mHousehold.setPersonasCharlas(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.personasCharlas))));
		
		
		if(cursorHousehold.getDouble(cursorHousehold.getColumnIndex(MainDBConstants.latitude))!=0) 
			mHousehold.setLatitude(cursorHousehold.getDouble(cursorHousehold.getColumnIndex(MainDBConstants.latitude)));
		if(cursorHousehold.getDouble(cursorHousehold.getColumnIndex(MainDBConstants.longitude))!=0)
		mHousehold.setLongitude(cursorHousehold.getDouble(cursorHousehold.getColumnIndex(MainDBConstants.longitude)));
		
		if(cursorHousehold.getDouble(cursorHousehold.getColumnIndex(MainDBConstants.altitud))!=0) 
			mHousehold.setAltitud(cursorHousehold.getDouble(cursorHousehold.getColumnIndex(MainDBConstants.altitud)));
		if(cursorHousehold.getDouble(cursorHousehold.getColumnIndex(MainDBConstants.exactitud))!=0)
			mHousehold.setExactitud(cursorHousehold.getFloat(cursorHousehold.getColumnIndex(MainDBConstants.exactitud)));
		
		
		mHousehold.setObs(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.obs)));
		mHousehold.setVerified(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.verif)));
		

		
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.masculinos))!=null)
			mHousehold.setMasculinos(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.masculinos))));
		
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.femeninos))!=null)
			mHousehold.setFemeninos(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.femeninos))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.menores5))!=null)
			mHousehold.setMenores5(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.menores5))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.menores5masc))!=null)
			mHousehold.setMenores5masc(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.menores5masc))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.menores5fem))!=null)
			mHousehold.setMenores5fem(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.menores5fem))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.embarazadas))!=null)
			mHousehold.setEmbarazadas(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.embarazadas))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sitiosDormirCama))!=null)
			mHousehold.setSitiosDormirCama(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sitiosDormirCama))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sitiosDormirHamaca))!=null)
			mHousehold.setSitiosDormirHamaca(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sitiosDormirHamaca))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sitiosDormirSuelo))!=null)
			mHousehold.setSitiosDormirSuelo(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sitiosDormirSuelo))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sitiosDormirOtro))!=null)
			mHousehold.setSitiosDormirOtro(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.sitiosDormirOtro))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.mtildExistentes))!=null)
			mHousehold.setMtildExistentes(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.mtildExistentes))));
		if(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.mosqSinInsecticida))!=null)
			mHousehold.setMosqSinInsecticida(Integer.valueOf(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.mosqSinInsecticida))));
		
		
		
		//METADATA
		if(cursorHousehold.getLong(cursorHousehold.getColumnIndex(MainDBConstants.recordDate))>0) mHousehold.setRecordDate(new Date(cursorHousehold.getLong(cursorHousehold.getColumnIndex(MainDBConstants.recordDate))));
		mHousehold.setRecordUser(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.recordUser)));
		mHousehold.setPasive(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mHousehold.setEstado(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mHousehold.setDeviceid(cursorHousehold.getString(cursorHousehold.getColumnIndex(MainDBConstants.deviceId)));
		return mHousehold;
	}
	
}

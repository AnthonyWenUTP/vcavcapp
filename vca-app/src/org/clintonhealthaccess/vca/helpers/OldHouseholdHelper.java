package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.OldHousehold;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class OldHouseholdHelper {
	
	public static ContentValues crearOldHouseholdValues(OldHousehold vivienda){
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
		//METADATA
		if (vivienda.getRecordDate() != null) cv.put(MainDBConstants.recordDate, vivienda.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, vivienda.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(vivienda.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(vivienda.getEstado()));
		cv.put(MainDBConstants.deviceId, vivienda.getDeviceid());
		return cv; 
	}	
	
	public static OldHousehold crearOldHousehold(Cursor cursorOldHousehold){
		
		OldHousehold mOldHousehold = new OldHousehold();
		mOldHousehold.setIdent(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.ident)));
		mOldHousehold.setCode(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.code)));
		mOldHousehold.setLocal(null);
		mOldHousehold.setCensusTaker(null);
		if(cursorOldHousehold.getLong(cursorOldHousehold.getColumnIndex(MainDBConstants.censusDate))>0) mOldHousehold.setCensusDate(new Date(cursorOldHousehold.getLong(cursorOldHousehold.getColumnIndex(MainDBConstants.censusDate))));
		mOldHousehold.setInhabited(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.inhabited)));
		mOldHousehold.setOwnerName(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.ownerName)));
		if(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.habitants))!=null) 
			mOldHousehold.setHabitants(Integer.valueOf(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.habitants))));
		mOldHousehold.setMaterial(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.material)));
		if(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.rooms))!=null)
			mOldHousehold.setRooms(Integer.valueOf(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.rooms))));
		if(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.sprRooms))!=null) 
			mOldHousehold.setSprRooms(Integer.valueOf(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.sprRooms))));
		
		if(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.noSprooms))!=null) 
			mOldHousehold.setNoSprooms(Integer.valueOf(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.noSprooms))));
		
		
		mOldHousehold.setNoSproomsReasons(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.noSproomsReasons)));
		
		
		if(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.sleep))!=null)
			mOldHousehold.setSleep(Integer.valueOf(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.sleep))));
		
		if(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.numNets))!=null)
			mOldHousehold.setNumNets(Integer.valueOf(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.numNets))));
		
		if(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.personasCharlas))!=null) 
			mOldHousehold.setPersonasCharlas(Integer.valueOf(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.personasCharlas))));
		
		
		if(cursorOldHousehold.getDouble(cursorOldHousehold.getColumnIndex(MainDBConstants.latitude))!=0) 
			mOldHousehold.setLatitude(cursorOldHousehold.getDouble(cursorOldHousehold.getColumnIndex(MainDBConstants.latitude)));
		if(cursorOldHousehold.getDouble(cursorOldHousehold.getColumnIndex(MainDBConstants.longitude))!=0)
		mOldHousehold.setLongitude(cursorOldHousehold.getDouble(cursorOldHousehold.getColumnIndex(MainDBConstants.longitude)));
		
		if(cursorOldHousehold.getDouble(cursorOldHousehold.getColumnIndex(MainDBConstants.altitud))!=0) 
			mOldHousehold.setAltitud(cursorOldHousehold.getDouble(cursorOldHousehold.getColumnIndex(MainDBConstants.altitud)));
		if(cursorOldHousehold.getDouble(cursorOldHousehold.getColumnIndex(MainDBConstants.exactitud))!=0)
			mOldHousehold.setExactitud(cursorOldHousehold.getFloat(cursorOldHousehold.getColumnIndex(MainDBConstants.exactitud)));
		
		
		mOldHousehold.setObs(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.obs)));
		mOldHousehold.setVerified(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.verif)));
		//METADATA
		if(cursorOldHousehold.getLong(cursorOldHousehold.getColumnIndex(MainDBConstants.recordDate))>0) mOldHousehold.setRecordDate(new Date(cursorOldHousehold.getLong(cursorOldHousehold.getColumnIndex(MainDBConstants.recordDate))));
		mOldHousehold.setRecordUser(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.recordUser)));
		mOldHousehold.setPasive(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mOldHousehold.setEstado(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mOldHousehold.setDeviceid(cursorOldHousehold.getString(cursorOldHousehold.getColumnIndex(MainDBConstants.deviceId)));
		return mOldHousehold;
	}
	
}

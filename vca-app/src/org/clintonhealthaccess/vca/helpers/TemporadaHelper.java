package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.irs.IrsSeason;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class TemporadaHelper {
	
	public static ContentValues crearTemporadaValues(IrsSeason temporada){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, temporada.getIdent());
		cv.put(MainDBConstants.code, temporada.getCode());
		cv.put(MainDBConstants.name, temporada.getName());
		if (temporada.getStartDate() != null) cv.put(MainDBConstants.startDate, temporada.getStartDate().getTime());
		if (temporada.getEndDate() != null) cv.put(MainDBConstants.endDate, temporada.getEndDate().getTime());
		cv.put(MainDBConstants.numberDays, temporada.getNumberDays());
		cv.put(MainDBConstants.obs, temporada.getObs());
		//METADATA
		if (temporada.getRecordDate() != null) cv.put(MainDBConstants.recordDate, temporada.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, temporada.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(temporada.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(temporada.getEstado()));
		cv.put(MainDBConstants.deviceId, temporada.getDeviceid());
		return cv; 
	}	
	
	public static IrsSeason crearTemporada(Cursor cursorTemporada){
		
		IrsSeason mTemporada = new IrsSeason();
		mTemporada.setIdent(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.ident)));
		mTemporada.setCode(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.code)));
		mTemporada.setName(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.name)));
		if(cursorTemporada.getLong(cursorTemporada.getColumnIndex(MainDBConstants.startDate))>0) mTemporada.setStartDate(new Date(cursorTemporada.getLong(cursorTemporada.getColumnIndex(MainDBConstants.startDate))));
		if(cursorTemporada.getLong(cursorTemporada.getColumnIndex(MainDBConstants.endDate))>0) mTemporada.setEndDate(new Date(cursorTemporada.getLong(cursorTemporada.getColumnIndex(MainDBConstants.endDate))));
		
		if(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.numberDays))!=null) 
			mTemporada.setNumberDays(cursorTemporada.getInt(cursorTemporada.getColumnIndex(MainDBConstants.numberDays)));
		mTemporada.setObs(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.obs)));
		
		//METADATA
		if(cursorTemporada.getLong(cursorTemporada.getColumnIndex(MainDBConstants.recordDate))>0) mTemporada.setRecordDate(new Date(cursorTemporada.getLong(cursorTemporada.getColumnIndex(MainDBConstants.recordDate))));
		mTemporada.setRecordUser(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.recordUser)));
		mTemporada.setPasive(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mTemporada.setEstado(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mTemporada.setDeviceid(cursorTemporada.getString(cursorTemporada.getColumnIndex(MainDBConstants.deviceId)));
		
		return mTemporada;
	}
	
}

package org.clintonhealthaccess.vca.helpers;

import java.util.Date;

import org.clintonhealthaccess.vca.domain.PuntosCriadero;
import org.clintonhealthaccess.vca.utils.MainDBConstants;

import android.content.ContentValues;
import android.database.Cursor;

public class PuntosCriaderoHelper {
	
	public static ContentValues crearPuntosCriaderoValues(PuntosCriadero puntoCriadero){
		ContentValues cv = new ContentValues();
		cv.put(MainDBConstants.ident, puntoCriadero.getIdent());
		cv.put(MainDBConstants.criadero, puntoCriadero.getCriadero().getIdent());
		cv.put(MainDBConstants.order, puntoCriadero.getOrder());
		cv.put(MainDBConstants.latitude, puntoCriadero.getLatitude());
		cv.put(MainDBConstants.longitude, puntoCriadero.getLongitude());
		
		//METADATA
		if (puntoCriadero.getRecordDate() != null) cv.put(MainDBConstants.recordDate, puntoCriadero.getRecordDate().getTime());
		cv.put(MainDBConstants.recordUser, puntoCriadero.getRecordUser());
		cv.put(MainDBConstants.pasive, String.valueOf(puntoCriadero.getPasive()));
		cv.put(MainDBConstants.estado, String.valueOf(puntoCriadero.getEstado()));
		cv.put(MainDBConstants.deviceId, puntoCriadero.getDeviceid());
		return cv; 
	}	
	
	public static PuntosCriadero crearPuntosCriadero(Cursor cursorPuntosCriadero){
		
		PuntosCriadero mPuntosCriadero = new PuntosCriadero();
		mPuntosCriadero.setIdent(cursorPuntosCriadero.getString(cursorPuntosCriadero.getColumnIndex(MainDBConstants.ident)));
		mPuntosCriadero.setCriadero(null);
		mPuntosCriadero.setOrder(cursorPuntosCriadero.getInt(cursorPuntosCriadero.getColumnIndex(MainDBConstants.order)));
		if(cursorPuntosCriadero.getDouble(cursorPuntosCriadero.getColumnIndex(MainDBConstants.latitude))!=0) 
			mPuntosCriadero.setLatitude(cursorPuntosCriadero.getDouble(cursorPuntosCriadero.getColumnIndex(MainDBConstants.latitude)));
		if(cursorPuntosCriadero.getDouble(cursorPuntosCriadero.getColumnIndex(MainDBConstants.longitude))!=0)
			mPuntosCriadero.setLongitude(cursorPuntosCriadero.getDouble(cursorPuntosCriadero.getColumnIndex(MainDBConstants.longitude)));
		
		//METADATA
		if(cursorPuntosCriadero.getLong(cursorPuntosCriadero.getColumnIndex(MainDBConstants.recordDate))>0) mPuntosCriadero.setRecordDate(new Date(cursorPuntosCriadero.getLong(cursorPuntosCriadero.getColumnIndex(MainDBConstants.recordDate))));
		mPuntosCriadero.setRecordUser(cursorPuntosCriadero.getString(cursorPuntosCriadero.getColumnIndex(MainDBConstants.recordUser)));
		mPuntosCriadero.setPasive(cursorPuntosCriadero.getString(cursorPuntosCriadero.getColumnIndex(MainDBConstants.pasive)).charAt(0));
		mPuntosCriadero.setEstado(cursorPuntosCriadero.getString(cursorPuntosCriadero.getColumnIndex(MainDBConstants.estado)).charAt(0));
		mPuntosCriadero.setDeviceid(cursorPuntosCriadero.getString(cursorPuntosCriadero.getColumnIndex(MainDBConstants.deviceId)));
		return mPuntosCriadero;
	}
	
}
